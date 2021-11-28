package app.data_ingestion;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.mock.web.MockMultipartFile;

import app.data_ingestion.business_logic_layer.services.IngestionService;
import app.data_ingestion.data_layer.dao.FileTypeDao;
import app.data_ingestion.data_layer.database.QueryExecutor;
import app.data_ingestion.data_layer.models.ColumnDetails;
import app.data_ingestion.data_layer.models.FileType;

@SpringBootTest
public class IngestionWithValidationTest {

    @Autowired
    IngestionService ingestionService;
    
    @MockBean
    FileTypeDao fileTypeDao;

    @MockBean
    QueryExecutor queryExecutor;

    @Mock
    JdbcTemplate jdbcTemplate;

    @Test
    void ingestDataWithInvalidData(){
        try{
            FileType fileType= new FileType();
            fileType.setFileTypeId(1);
            fileType.setFileTypeName("Sales");
            String columnDetails = "[{\"columnName\":\"NAME\",\"dataType\":\"STRING\",\"rules\":[]},{\"columnName\":\"SALES\",\"dataType\":\"INTEGER\",\"rules\":[{\"operator\":\">\",\"rhsValue\":\"0\"}]}]";
            ObjectMapper objectMapper = new ObjectMapper();
            ColumnDetails[] colDetailsArray;
            colDetailsArray = objectMapper.readValue(columnDetails, ColumnDetails[].class);
            fileType.setColumnDetails(Arrays.asList(colDetailsArray) );
            when(fileTypeDao.getFileTypeById(1)).thenReturn(fileType);

            String fileContent = "name,sales"+System.lineSeparator()+"Prachi,90"+System.lineSeparator()+"Shan,-100";
            MockMultipartFile multipartFile = new MockMultipartFile(
                        "file", 
                        "sales.csv", 
                        MediaType.APPLICATION_OCTET_STREAM_VALUE, 
                        fileContent.getBytes()
                    );
            
            String query = "create table if not exists `SALES` (`NAME` nvarchar(1000),`SALES` INTEGER)";
            doNothing().when(jdbcTemplate).execute(query);

            List<String> headers = new ArrayList<>(Arrays.asList("NAME", "SALES"));
            String tableName = "SALES";
            List<List<String>> rows = new ArrayList<>();
            rows.add(new ArrayList<>(Arrays.asList("Prachi","90")));
            rows.add(new ArrayList<>(Arrays.asList("Shan","100")));

            HashMap<String,String> mapColumnToDatatype = new HashMap<>();
            mapColumnToDatatype.put("NAME", "STRING");
            mapColumnToDatatype.put("SALES", "INTEGER");
            doNothing().when(queryExecutor).insertRecords(headers, tableName, rows, mapColumnToDatatype);

            // IngestionService ingestionService = new IngestionServiceImpl();
            ingestionService.ingestData(1, multipartFile, ",");
            assertAll(
                () -> assertEquals(1, ingestionService.getInvalidRows().size()),
                () -> assertEquals(1, ingestionService.getValidRows().size())
            );
                
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    
}
