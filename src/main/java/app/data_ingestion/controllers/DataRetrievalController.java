package app.data_ingestion.controllers;




import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import app.data_ingestion.business_logic_layer.services.DataRetrievalService;
import app.data_ingestion.helpers.Helper;

@RestController
public class DataRetrievalController {

    @Autowired
    DataRetrievalService dataService;
    
    /**
     * @param username
     * @param password
     * @return
     */
    @GetMapping(path = "/records/{table_name}")
    public ResponseEntity<Object> fetchData(@PathVariable String table_name){
        
        HashMap<String,Object> responseBody = Helper.createResponseBody(dataService.fetchData(table_name));
        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }
}
