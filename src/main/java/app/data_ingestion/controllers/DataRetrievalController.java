package app.data_ingestion.controllers;


import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import app.data_ingestion.service_layer.services.DataRetrievalService;
import app.data_ingestion.helpers.Helper;

@CrossOrigin(origins = "http://localhost:5555")
@RestController
public class DataRetrievalController {

    @Autowired
    DataRetrievalService dataService;


    /**
     * @param tableName
     * @return
     */
    @GetMapping(path = "/records/{table_name}")
    public ResponseEntity<Object> fetchData(@PathVariable String tableName) {

        HashMap<String, Object> responseBody = Helper.createResponseBody(dataService.fetchData(tableName));
        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }
}
