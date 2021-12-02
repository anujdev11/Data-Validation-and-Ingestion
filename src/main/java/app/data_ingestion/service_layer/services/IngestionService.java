package app.data_ingestion.service_layer.services;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public interface IngestionService {

    /**
     * @param id
     * @param file
     * @param delimiter
     * @throws Exception
     */
    public void ingestData(int id, MultipartFile file, String delimiter) throws Exception;

    /**
     * @param inputFile
     * @return
     * @throws IOException
     */
    String retrieveFileContentsAsString(MultipartFile inputFile) throws IOException;

    /**
     * @param file_content
     * @param delimiter
     */
    void populateHeadersAndRows(String file_content, String delimiter);

    /**
     * @param headers
     * @throws Exception
     */
    void validateFileHeaders(List<String> headers) throws Exception;

    void createTable();

    void insertRecordsToDatabase() throws SQLException;

    List<List<String>> getInvalidRows();

    List<List<String>> getValidRows();
}
