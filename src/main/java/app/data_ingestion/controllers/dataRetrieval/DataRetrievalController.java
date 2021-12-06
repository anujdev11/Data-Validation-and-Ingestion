package app.data_ingestion.controllers.dataRetrieval;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import app.data_ingestion.helpers.GenericControllerOperations;
import app.data_ingestion.services.dataRetrieval.IDataRetrievalService;

@CrossOrigin(origins = "http://localhost:5555")
@RestController
public class DataRetrievalController {

    @Autowired
    IDataRetrievalService dataService;

    /**
     * @param tableName
     * @return
     */
    @GetMapping(path = "/records/{tableName}")
    public ResponseEntity<Object> fetchData(@PathVariable String tableName) {

        Map<String, Object> responseBody = GenericControllerOperations.getInstance()
                .createResponseBody(dataService.fetchData(tableName));
        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }
}