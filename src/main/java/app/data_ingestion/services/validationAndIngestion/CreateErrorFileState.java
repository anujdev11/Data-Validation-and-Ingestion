package app.data_ingestion.services.validationAndIngestion;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import app.data_ingestion.config.ConfigReader;
import app.data_ingestion.helpers.ConfigPropertiesKeyConstants;

@Service
public class CreateErrorFileState implements IState{

    
    /** 
     * @param ingestionService
     * @param id
     * @param file
     * @param delimiter
     * @param action
     * @return IState
     * @throws Exception
     */
    @Override
    public IState execute(IngestionService ingestionService, int id, MultipartFile file, String delimiter, String action)
            throws Exception {
        
        if (!ingestionService.getInvalidRows().isEmpty()) {
            String path = ConfigReader.getInstance().getProperty(ConfigPropertiesKeyConstants.INVALID_ROWS_CSV_PATH);
            CSVUtil.getInstance().createCSV(path, ingestionService.getHeaders(), ingestionService.getInvalidRows());
        }
        
        return new InsertRecordsState();
    }
    
}
