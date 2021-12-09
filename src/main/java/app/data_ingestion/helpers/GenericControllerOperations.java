package app.data_ingestion.helpers;

import java.util.HashMap;

public class GenericControllerOperations {

    private static GenericControllerOperations genericControllerOperations = new GenericControllerOperations();

    private GenericControllerOperations() {
    }

    
    /** 
     * @return GenericControllerOperations
     */
    public static GenericControllerOperations getInstance(){
        return genericControllerOperations;
    }

    
    /** 
     * format response body
     * @param data
     * @return HashMap<String, Object>
     */
    public HashMap<String, Object> createResponseBody(Object data) {
        HashMap<String, Object> body = new HashMap<>();
        body.put("data", data);
        return body;
    }

}
