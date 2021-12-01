package app.data_ingestion.controllers;

import app.data_ingestion.service_layer.services.FileDefinitionService;

import app.data_ingestion.service_layer.servicesImpl.UserServiceStatus;
import app.data_ingestion.data_layer.models.FileType;


import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;

@CrossOrigin(origins = "http://localhost:5555")
@RestController
public class FileDefinitionController {


    @Autowired
    FileDefinitionService fileDefService;
    static final String SUCCESS_MESSAGE = "File Definition created successfully!";
    static final String SYSTEM_ERROR_MESSAGE = "System error";


    @PostMapping(path = "/fileDefinition")
    public ResponseEntity<Object> addFileDefinition(@RequestBody FileType fileType) {
        UserServiceStatus status = fileDefService.fileDefinition(fileType);
        if (status == UserServiceStatus.SUCCESS) {
            return ResponseEntity.status(HttpStatus.CREATED).body(SUCCESS_MESSAGE);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(SYSTEM_ERROR_MESSAGE);
    }

    @PostMapping(path = "/fileDefinition/delete")
    public ResponseEntity<Object> deleteFileDefinition(@RequestParam("file_definition_id") int file_definition_id) throws SQLException, JsonProcessingException {
        boolean status = fileDefService.deleteFileDefinition(file_definition_id);
        return new ResponseEntity<>("Deleted", HttpStatus.OK);
    }

}
