package app.data_ingestion.business_logic_layer.services;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public interface IngestionService {

    public void ingestData(int id, MultipartFile file, String delimiter) throws Exception;

    String retrieveFileContentsAsString(MultipartFile inputFile) throws IOException;

    void populateHeadersAndRows(String file_content, String delimiter);

    void validateFileHeaders(List<String> headers) throws Exception;

    void createTable();

    void insertRecodsToDatabase() throws SQLException;
}
