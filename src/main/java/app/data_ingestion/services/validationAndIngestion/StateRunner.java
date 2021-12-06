package app.data_ingestion.services.validationAndIngestion;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class StateRunner {

    private IState currentState;

    public StateRunner(){
        currentState = new LoadDataState();
    }

    
    /** 
     * @param ingestionService
     * @param id
     * @param file
     * @param delimiter
     * @param action
     * @throws Exception
     */
    public void run(IngestionService ingestionService, int id, MultipartFile file, String delimiter, String action) throws Exception{

        do{
            currentState= currentState.execute(ingestionService, id, file, delimiter, action);
        } while(currentState != null);
        
        
    }
    
}
