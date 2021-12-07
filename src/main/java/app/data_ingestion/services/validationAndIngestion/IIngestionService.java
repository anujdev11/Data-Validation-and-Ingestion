package app.data_ingestion.services.validationAndIngestion;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import app.data_ingestion.dataLayer.models.User;
import app.data_ingestion.exceptions.ResourceNotFoundException;
import app.data_ingestion.services.userAuthAndRegister.UserServiceStatus;

public interface IIngestionService {

    /**
     * @param id
     * @param file
     * @param delimiter
     * @throws Exception
     */
    public void ingestData(int id, MultipartFile file, String delimiter, String action) throws Exception;

    List<List<String>> getInvalidRows();

    List<List<String>> getValidRows();
    
    //public User editInlineDataO(String username) throws SQLException, ResourceNotFoundException;

	public UserServiceStatus editInlineData(Map<String, Object> body) throws Exception;
    
    
}
