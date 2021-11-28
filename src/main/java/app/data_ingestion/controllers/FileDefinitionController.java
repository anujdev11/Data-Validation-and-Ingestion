package app.data_ingestion.controllers;

import app.data_ingestion.business_logic_layer.services.FileDefinitionService;

import app.data_ingestion.business_logic_layer.servicesImpl.UserServiceStatus;
import app.data_ingestion.data_layer.models.FileType;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:5555")
@RestController
public class FileDefinitionController {

	
	    @Autowired
	    FileDefinitionService fileDefService;
	    static final String SUCCESS_MESSAGE = "File Definition created successfully!";
	    static final String SYSTEM_ERROR_MESSAGE = "System error";
	    
	  
	    @PostMapping(path = "/fileDefinition")
	    public ResponseEntity<Object> addFileDefinition(@RequestBody FileType fileType){
	    	UserServiceStatus status = fileDefService.fileDefinition(fileType);
	        if(status == UserServiceStatus.SUCCESS){
	            return ResponseEntity.status(HttpStatus.CREATED).body(SUCCESS_MESSAGE);
	        }
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(SYSTEM_ERROR_MESSAGE);         
	    }
	
}
