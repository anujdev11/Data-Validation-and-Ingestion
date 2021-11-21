package app.data_ingestion.business_logic_layer.services;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public interface IngestionService {
    
    public String retrieveFileContentsAsString(MultipartFile inputFile) throws IOException;

    public void populateHeadersAndRows(String file_content, String delimiter);

    public void validateFileHeaders(List<String> headers) throws Exception;

    public void insertRecodsToDatabase() throws SQLException;
}
