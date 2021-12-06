package app.data_ingestion.services.validationAndIngestion;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import app.data_ingestion.helpers.LiteralConstants;

@Service
public class ValidateHeadersState implements IState{

    
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
        
        validateFileHeaders(ingestionService, ingestionService.getHeaders());
        
        return new ValidateRecordsState();
    }

    
    /** 
     * @param ingestionService
     * @param headers
     * @throws Exception
     */
    public void validateFileHeaders(IngestionService ingestionService, List<String> headers) throws Exception {

        List<String> columns = new ArrayList<>(ingestionService.getMapColumnToDatatype().keySet());
        columns.replaceAll(String::toUpperCase);
        headers.replaceAll(String::toUpperCase);

        for (String header : headers) {
            if (!columns.contains(header)) {
                throw new Exception(LiteralConstants.INGESTION_INVALID_HEADERS_MESSAGE + String.join(", ", columns));
            }
        }
    }
    
}
