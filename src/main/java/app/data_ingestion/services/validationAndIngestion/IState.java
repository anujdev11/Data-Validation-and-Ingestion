package app.data_ingestion.services.validationAndIngestion;

import org.springframework.web.multipart.MultipartFile;

public interface IState {
    IState execute(IngestionService ingestionService, int id, MultipartFile file, String delimiter, String action) throws Exception;
}
