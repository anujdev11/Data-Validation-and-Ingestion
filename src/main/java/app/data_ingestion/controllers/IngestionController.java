package app.data_ingestion.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import app.data_ingestion.business_logic_layer.services.IngestionService;
@CrossOrigin(origins = "http://localhost:5555")
@RestController
public class IngestionController {

    @Autowired
    IngestionService ingestionService;

    @PostMapping(path = "/ingestion")
    @ResponseBody
    public String ingestDataFromFile(@RequestParam("file_definition_id") String file_definition_id,
                                    @RequestParam("input_file") MultipartFile multipartFile,
                                    @RequestParam("delimiter") String delimiter) throws Exception {
        
        System.out.println(file_definition_id);
        System.out.println(delimiter);
        ingestionService.ingestData(Integer.valueOf(file_definition_id), multipartFile, delimiter);
        
        return "success";
    }
}
