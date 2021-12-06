package app.data_ingestion.helpers;

import java.util.HashMap;
import java.util.Map;

public class GenericControllerOperations {

    private static GenericControllerOperations genericControllerOperations = new GenericControllerOperations();

    private GenericControllerOperations() {
    }

    public static GenericControllerOperations getInstance() {
        return genericControllerOperations;
    }

    public Map<String, Object> createResponseBody(Object data) {
        Map<String, Object> body = new HashMap<>();
        body.put("data", data);
        return body;
    }

}
