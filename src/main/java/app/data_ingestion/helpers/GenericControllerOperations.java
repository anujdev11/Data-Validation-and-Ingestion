package app.data_ingestion.helpers;

import java.util.HashMap;

public class GenericControllerOperations {

    private static GenericControllerOperations genericControllerOperations = new GenericControllerOperations();

    private GenericControllerOperations(){}

    public static GenericControllerOperations getInstance(){
        return genericControllerOperations;
    }

    public HashMap<String, Object> createResponseBody(Object data) {
        HashMap<String, Object> body = new HashMap<>();
        body.put("data", data);
        return body;
    }
    
}
