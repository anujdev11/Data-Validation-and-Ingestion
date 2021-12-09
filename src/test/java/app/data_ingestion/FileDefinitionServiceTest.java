package app.data_ingestion;
import app.data_ingestion.dataLayer.dao.IFileTypeDao;
import app.data_ingestion.dataLayer.models.ColumnDetails;
import app.data_ingestion.dataLayer.models.FileType;
import app.data_ingestion.services.fileDefinition.FileDefinition;
import app.data_ingestion.services.userAuthAndRegister.UserServiceStatus;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import java.sql.SQLException;
import java.util.Arrays;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
@ContextConfiguration
public class FileDefinitionServiceTest {
    @Mock
    IFileTypeDao fileTypeDao;

    @InjectMocks
    FileDefinition fileDefinition;



    @Test
    void addFileDefinitionTest() throws SQLException, JsonProcessingException {
        FileType fileTypeTest = new FileType();
        fileTypeTest.setFileTypeId(2);
        fileTypeTest.setFileTypeName("Sales");
        String columnDetails = "[{\"columnName\":\"NAME\",\"dataType\":\"STRING\",\"rules\":[]},{\"columnName\":\"SALES\",\"dataType\":\"INTEGER\",\"rules\":[]}]";
        ObjectMapper objectMapper = new ObjectMapper();
        ColumnDetails[] colDetailsArray;

        colDetailsArray = objectMapper.readValue(columnDetails, ColumnDetails[].class);

        fileTypeTest.setColumnDetails(Arrays.asList(colDetailsArray));
        when(fileTypeDao.addFileDefinition(fileTypeTest)).thenReturn(1);
        UserServiceStatus userServiceStatus = fileDefinition.fileDefinition(fileTypeTest);
        assertEquals(UserServiceStatus.SUCCESS,userServiceStatus);
    }

    @Test
    void addFileDefinitionFailTest() throws SQLException, JsonProcessingException {
        FileType fileTypeTest = new FileType();
        fileTypeTest.setFileTypeId(2);
        fileTypeTest.setFileTypeName("Sales");
        String columnDetails = "[{\"columnName\":\"NAME\",\"dataType\":\"STRING\",\"rules\":[]},{\"columnName\":\"SALES\",\"dataType\":\"INTEGER\",\"rules\":[]}]";
        ObjectMapper objectMapper = new ObjectMapper();
        ColumnDetails[] colDetailsArray;

        colDetailsArray = objectMapper.readValue(columnDetails, ColumnDetails[].class);

        fileTypeTest.setColumnDetails(Arrays.asList(colDetailsArray));
        when(fileTypeDao.addFileDefinition(fileTypeTest)).thenReturn(0);
        UserServiceStatus userServiceStatus = fileDefinition.fileDefinition(fileTypeTest);
        assertEquals(UserServiceStatus.FAILURE,userServiceStatus);
    }

    @Test
    void addFileDefinitionExceptionTest() throws SQLException, JsonProcessingException {
        FileType fileTypeTest = new FileType();
        fileTypeTest.setFileTypeId(2);
        fileTypeTest.setFileTypeName("Sales");
        String columnDetails = "[{\"columnName\":\"NAME\",\"dataType\":\"STRING\",\"rules\":[]},{\"columnName\":\"SALES\",\"dataType\":\"INTEGER\",\"rules\":[]}]";
        ObjectMapper objectMapper = new ObjectMapper();
        ColumnDetails[] colDetailsArray;

        colDetailsArray = objectMapper.readValue(columnDetails, ColumnDetails[].class);

        fileTypeTest.setColumnDetails(Arrays.asList(colDetailsArray));
        when(fileTypeDao.addFileDefinition(fileTypeTest)).thenThrow(SQLException.class);
        UserServiceStatus userServiceStatus = fileDefinition.fileDefinition(fileTypeTest);
        assertEquals(UserServiceStatus.FAILURE,userServiceStatus);
    }

    @Test
    void deleteFileDefinitionTest() throws SQLException, JsonProcessingException {
        when(fileTypeDao.deleteFileDefinition(21)).thenReturn(true);
        Boolean status = fileDefinition.deleteFileDefinition(21);
        assertTrue(status);
    }

    @Test
    void deleteFileDefinitionFalseTest() throws SQLException, JsonProcessingException {
        when(fileTypeDao.deleteFileDefinition(21)).thenReturn(false);
        Boolean status = fileDefinition.deleteFileDefinition(21);
        assertFalse(status);
    }

    @Test
    void updateFileDefinitionTest() throws SQLException, JsonProcessingException {
        FileType fileTypeTest = new FileType();
        fileTypeTest.setFileTypeId(2);
        fileTypeTest.setFileTypeName("Sales");
        String columnDetails = "[{\"columnName\":\"NAME\",\"dataType\":\"STRING\",\"rules\":[]},{\"columnName\":\"SALES\",\"dataType\":\"INTEGER\",\"rules\":[]}]";
        ObjectMapper objectMapper = new ObjectMapper();
        ColumnDetails[] colDetailsArray;

        colDetailsArray = objectMapper.readValue(columnDetails, ColumnDetails[].class);

        fileTypeTest.setColumnDetails(Arrays.asList(colDetailsArray));

        when(fileTypeDao.updateFileDefinition(fileTypeTest)).thenReturn(1);
        UserServiceStatus userServiceStatus = fileDefinition.updateFileDefinition(fileTypeTest);
        assertEquals(UserServiceStatus.SUCCESS,userServiceStatus);
    }

    @Test
    void updateFileDefinitionFailTest() throws SQLException, JsonProcessingException {
        FileType fileTypeTest = new FileType();
        fileTypeTest.setFileTypeId(2);
        fileTypeTest.setFileTypeName("Sales");
        String columnDetails = "[{\"columnName\":\"NAME\",\"dataType\":\"STRING\",\"rules\":[]},{\"columnName\":\"SALES\",\"dataType\":\"INTEGER\",\"rules\":[]}]";
        ObjectMapper objectMapper = new ObjectMapper();
        ColumnDetails[] colDetailsArray;

        colDetailsArray = objectMapper.readValue(columnDetails, ColumnDetails[].class);

        fileTypeTest.setColumnDetails(Arrays.asList(colDetailsArray));

        when(fileTypeDao.updateFileDefinition(fileTypeTest)).thenReturn(0);
        UserServiceStatus userServiceStatus = fileDefinition.updateFileDefinition(fileTypeTest);
        assertEquals(UserServiceStatus.FAILURE,userServiceStatus);
    }

    @Test
    void updateFileDefinitionExceptionTest() throws SQLException, JsonProcessingException {
        FileType fileTypeTest = new FileType();
        fileTypeTest.setFileTypeId(2);
        fileTypeTest.setFileTypeName("Sales");
        String columnDetails = "[{\"columnName\":\"NAME\",\"dataType\":\"STRING\",\"rules\":[]},{\"columnName\":\"SALES\",\"dataType\":\"INTEGER\",\"rules\":[]}]";
        ObjectMapper objectMapper = new ObjectMapper();
        ColumnDetails[] colDetailsArray;

        colDetailsArray = objectMapper.readValue(columnDetails, ColumnDetails[].class);

        fileTypeTest.setColumnDetails(Arrays.asList(colDetailsArray));

        when(fileTypeDao.updateFileDefinition(fileTypeTest)).thenThrow(SQLException.class);
        UserServiceStatus userServiceStatus = fileDefinition.updateFileDefinition(fileTypeTest);
        assertEquals(UserServiceStatus.FAILURE,userServiceStatus);
    }



}
