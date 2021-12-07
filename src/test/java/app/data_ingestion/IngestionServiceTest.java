package app.data_ingestion;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.web.multipart.MultipartFile;

import app.data_ingestion.dataLayer.dao.IFileTypeDao;
import app.data_ingestion.dataLayer.database.IQueryExecutor;
import app.data_ingestion.dataLayer.models.ColumnDetails;
import app.data_ingestion.dataLayer.models.FileType;
import app.data_ingestion.services.validationAndIngestion.IIngestionService;
import app.data_ingestion.services.validationAndIngestion.LoadDataState;

@SpringBootTest
@ContextConfiguration
public class IngestionServiceTest {

    @Autowired
    IIngestionService ingestService;

    @MockBean
    IFileTypeDao fileTypeDao;

    @MockBean
    IQueryExecutor queryExecutor;

    @Mock
    JdbcTemplate jdbcTemplate;

    FileType fileType;

    void initializeFileTypeDaoBeans() {

        try {
            fileType = new FileType();
            fileType.setFileTypeId(1);
            fileType.setFileTypeName("Sales");
            String columnDetails = "[{\"columnName\":\"NAME\",\"dataType\":\"STRING\",\"rules\":[]},{\"columnName\":\"SALES\",\"dataType\":\"INTEGER\",\"rules\":[]}]";
            ObjectMapper objectMapper = new ObjectMapper();
            ColumnDetails[] colDetailsArray;

            colDetailsArray = objectMapper.readValue(columnDetails, ColumnDetails[].class);

            fileType.setColumnDetails(Arrays.asList(colDetailsArray));

            when(fileTypeDao.getFileTypeById(1)).thenReturn(fileType);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    
    /** 
     * @return MockMultipartFile
     */
    MockMultipartFile initializeMultipartFileSales() {

        try {
            String fileContent = "name,sales" + System.lineSeparator() + "Prachi,90" + System.lineSeparator()
                    + "Shan,100";
            MockMultipartFile multipartFile = new MockMultipartFile(
                    "file",
                    "sales.csv",
                    MediaType.APPLICATION_OCTET_STREAM_VALUE,
                    fileContent.getBytes());
            return multipartFile;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    
    /** 
     * @return MockMultipartFile
     */
    MockMultipartFile initializeMultipartFileEmployee() {

        try {
            String fileContent = "id,name" + System.lineSeparator() + "1,Prachi Kabtiyal" + System.lineSeparator()
                    + "2,Shan Malhotra";
            MockMultipartFile multipartFile = new MockMultipartFile(
                    "file",
                    "employees.csv",
                    MediaType.APPLICATION_OCTET_STREAM_VALUE,
                    fileContent.getBytes());
            return multipartFile;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    
    /** 
     * @return MockMultipartFile
     */
    MockMultipartFile initializeMultipartFileEmpty() {

        try {
            String fileContent = "";
            MockMultipartFile multipartFile = new MockMultipartFile(
                    "file",
                    "empty.csv",
                    MediaType.APPLICATION_OCTET_STREAM_VALUE,
                    fileContent.getBytes());
            return multipartFile;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    void initializeQueryExecutorBean() {

        try {
            String query = "create table if not exists `SALES` (`NAME` nvarchar(1000),`SALES` INTEGER)";
            doNothing().when(jdbcTemplate).execute(query);

            List<String> headers = new ArrayList<>(Arrays.asList("NAME", "SALES"));
            String tableName = "SALES";
            List<List<String>> rows = new ArrayList<>();
            rows.add(new ArrayList<>(Arrays.asList("Prachi", "90")));
            rows.add(new ArrayList<>(Arrays.asList("Shan", "100")));

            Map<String, String> map_column_to_datatype = new HashMap<>();
            map_column_to_datatype.put("NAME", "STRING");
            map_column_to_datatype.put("SALES", "INTEGER");
            doNothing().when(queryExecutor).insertRecords(headers, tableName, rows, map_column_to_datatype);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void retrieveFileContentsAsStringTest() {
        try {
            LoadDataState loadDataState = new LoadDataState();
            MultipartFile multipartFile = initializeMultipartFileSales();
            String fileContent = "name,sales" + System.lineSeparator() + "Prachi,90" + System.lineSeparator()
                    + "Shan,100";
            String actualContent = loadDataState.retrieveFileContentsAsString(multipartFile);
            assertEquals(fileContent, actualContent);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void retrieveFileContentsAsStringWithNoInputFile() {
        LoadDataState loadDataState = new LoadDataState();
        assertThrows(Exception.class, () -> loadDataState.retrieveFileContentsAsString(null));
    }

    @Test
    void retrieveFileContentsAsStringWithDifferentFiles() {
        LoadDataState loadDataState = new LoadDataState();
        MultipartFile sales = initializeMultipartFileSales();
        MultipartFile employees = initializeMultipartFileEmployee();
        try {
            String salesContent = loadDataState.retrieveFileContentsAsString(sales);
            String employeesContent = loadDataState.retrieveFileContentsAsString(employees);
            assertNotEquals(salesContent, employeesContent);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    
    /** 
     * @throws Exception
     */
    @Test
    void ingestDataWithoutValidations() throws Exception {

        // IIngestionService ingestService = new IngestionService();
        initializeFileTypeDaoBeans();
        MultipartFile multipartFile = initializeMultipartFileSales();
        initializeQueryExecutorBean();
        ingestService.ingestData(1, multipartFile, ",", "append");
        assertAll(
                () -> assertEquals(2, ingestService.getValidRows().size()),
                () -> assertEquals(0, ingestService.getInvalidRows().size()));
    }

    
    /** 
     * @throws Exception
     */
    @Test
    void ingestDataWithIncorrectDelimiter() throws Exception {
        // IIngestionService ingestService = new IngestionService();
        initializeFileTypeDaoBeans();
        MultipartFile multipartFile = initializeMultipartFileSales();
        initializeQueryExecutorBean();
        assertThrows(Exception.class, () -> ingestService.ingestData(1, multipartFile, "-", "append"));

        try {
            ingestService.ingestData(1, multipartFile, "-", "append");
        } catch (Exception e) {
            String expectedMessage = "Invalid headers. Correct headers are: SALES, NAME";
            String actualMessage = e.getMessage();
            assertEquals(expectedMessage, actualMessage);
        }
    }

    
    /** 
     * @throws Exception
     */
    @Test
    void ingestDataWithEmptyFileExceptionMessage() throws Exception {
        MultipartFile empty = initializeMultipartFileEmpty();
        try {
            ingestService.ingestData(1, empty, ",", "append");
        } catch (Exception e) {
            String expectedMessage = "Data Ingestion cannot be performed on an empty file. Please try again.";
            String actualMessage = e.getMessage();
            assertEquals(expectedMessage, actualMessage);
        }
    }

    @Test
    void ingestDataWithEmptyFile() {
        MultipartFile empty = initializeMultipartFileEmpty();
        assertThrows(Exception.class, () -> ingestService.ingestData(1, empty, ",", "append"));
    }

}
