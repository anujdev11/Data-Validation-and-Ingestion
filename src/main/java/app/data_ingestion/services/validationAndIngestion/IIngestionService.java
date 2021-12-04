package app.data_ingestion.services.validationAndIngestion;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

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
}
