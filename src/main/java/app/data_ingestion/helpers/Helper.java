package app.data_ingestion.helpers;

import java.util.HashMap;

public class Helper {
    
    public static HashMap<String,Object> createResponseBody(Object data){
        HashMap<String, Object> body = new HashMap<>();
        body.put("data", data);
        return body;
    }

    public static String addUnderscores(String name){
        return name.replaceAll(name, "_");
    }

    public static String getDataType(String dtype) {
        if(dtype.equalsIgnoreCase("string")){
            return "nvarchar(1000)";
        }
        return dtype;
    }
}
