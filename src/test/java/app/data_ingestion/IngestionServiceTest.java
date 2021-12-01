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
import org.springframework.web.multipart.MultipartFile;

import app.data_ingestion.service_layer.services.IngestionService;
import app.data_ingestion.data_layer.dao.FileTypeDao;
import app.data_ingestion.data_layer.database.QueryExecutor;
import app.data_ingestion.data_layer.models.ColumnDetails;
import app.data_ingestion.data_layer.models.FileType;


@SpringBootTest
public class IngestionServiceTest {

    @Autowired
    IngestionService ingestService;

    @MockBean
    FileTypeDao fileTypeDao;

    @MockBean
    QueryExecutor queryExecutor;

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


    MockMultipartFile initializeMultipartFileSales() {

        try {
            String fileContent = "name,sales" + System.lineSeparator() + "Prachi,90" + System.lineSeparator() + "Shan,100";
            MockMultipartFile multipartFile = new MockMultipartFile(
                    "file",
                    "sales.csv",
                    MediaType.APPLICATION_OCTET_STREAM_VALUE,
                    fileContent.getBytes()
            );
            return multipartFile;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    MockMultipartFile initializeMultipartFileEmployee() {

        try {
            String fileContent = "id,name" + System.lineSeparator() + "1,Prachi Kabtiyal" + System.lineSeparator() + "2,Shan Malhotra";
            MockMultipartFile multipartFile = new MockMultipartFile(
                    "file",
                    "employees.csv",
                    MediaType.APPLICATION_OCTET_STREAM_VALUE,
                    fileContent.getBytes()
            );
            return multipartFile;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    MockMultipartFile initializeMultipartFileEmpty() {

        try {
            String fileContent = "";
            MockMultipartFile multipartFile = new MockMultipartFile(
                    "file",
                    "empty.csv",
                    MediaType.APPLICATION_OCTET_STREAM_VALUE,
                    fileContent.getBytes()
            );
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
            MultipartFile multipartFile = initializeMultipartFileSales();
            String fileContent = "name,sales" + System.lineSeparator() + "Prachi,90" + System.lineSeparator() + "Shan,100";
            String actualContent = ingestService.retrieveFileContentsAsString(multipartFile);
            assertEquals(fileContent, actualContent);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void retrieveFileContentsAsStringWithNoInputFile() {
        assertThrows(NullPointerException.class, () -> ingestService.retrieveFileContentsAsString(null));
    }

    @Test
    void retrieveFileContentsAsStringWithDifferentFiles() {
        MultipartFile sales = initializeMultipartFileSales();
        MultipartFile employees = initializeMultipartFileEmployee();
        try {
            String salesContent = ingestService.retrieveFileContentsAsString(sales);
            String employeesContent = ingestService.retrieveFileContentsAsString(employees);
            assertNotEquals(salesContent, employeesContent);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Test
    void ingestDataWithoutValidations() throws Exception {
        initializeFileTypeDaoBeans();
        MultipartFile multipartFile = initializeMultipartFileSales();
        initializeQueryExecutorBean();
        ingestService.ingestData(1, multipartFile, ",");
        assertAll(
                () -> assertEquals(2, ingestService.getValidRows().size()),
                () -> assertEquals(0, ingestService.getInvalidRows().size())
        );
    }

    @Test
    void ingestDataWithIncorrectDelimiter() throws Exception {

        initializeFileTypeDaoBeans();
        MultipartFile multipartFile = initializeMultipartFileSales();
        initializeQueryExecutorBean();
        //ingestService.ingestData(1, multipartFile, "-");
        assertThrows(Exception.class, () -> ingestService.ingestData(1, multipartFile, "-"));

        try {
            ingestService.ingestData(1, multipartFile, "-");
        } catch (Exception e) {
            String expectedMessage = "Invalid headers. Correct headers are: SALES, NAME";
            String actualMessage = e.getMessage();
            assertEquals(expectedMessage, actualMessage);
        }
    }

    @Test
    void ingestDataWithEmptyFile() {
        MultipartFile empty = initializeMultipartFileEmpty();
        assertThrows(Exception.class, () -> ingestService.ingestData(1, empty, ","));
    }

    @Test
    void ingestDataWithEmptyFileExceptionMessage() throws Exception {
        MultipartFile empty = initializeMultipartFileEmpty();
        try {
            ingestService.ingestData(1, empty, ",");
        } catch (Exception e) {
            String expectedMessage = "Data Ingestion cannot be performed on an empty file. Please try again.";
            String actualMessage = e.getMessage();
            assertEquals(expectedMessage, actualMessage);
        }
    }

}
