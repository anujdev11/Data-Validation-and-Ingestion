package app.data_ingestion.helpers;

import java.util.HashMap;

public class Helper {
    
    public static HashMap<String,Object> createResponseBody(Object data){
        HashMap<String, Object> body = new HashMap<>();
        body.put("data", data);
        return body;
    }
}
