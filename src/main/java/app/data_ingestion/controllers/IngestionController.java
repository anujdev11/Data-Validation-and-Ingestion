package app.data_ingestion.controllers;


import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import app.data_ingestion.business_logic_layer.services.IngestionService;
import app.data_ingestion.config.ConfigReader;

@RestController
public class IngestionController {

    @Autowired
    IngestionService ingestionService;

    @PostMapping(path = "/ingestion", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    @ResponseBody
    public ResponseEntity<Object> ingestDataFromFile(@RequestParam("file_definition_id") String file_definition_id,
                                    @RequestParam("input_file") MultipartFile multipartFile,
                                    @RequestParam("delimiter") String delimiter) throws Exception {
        
        System.out.println(file_definition_id);
        System.out.println(delimiter);
        ingestionService.ingestData(Integer.valueOf(file_definition_id), multipartFile, delimiter);
        String fileName = ConfigReader.getProperty("INVALID_ROWS_CSV_PATH");
        InputStreamResource fileInputStream = null;
        File file = new File(fileName);
        if(file.exists()){
            InputStream in = new FileInputStream(fileName);
            fileInputStream = new InputStreamResource(in);

            HttpHeaders headers = new HttpHeaders();
            headers.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName);
            headers.set(HttpHeaders.CONTENT_TYPE, "text/csv");

            return new ResponseEntity<>(
                fileInputStream,
                headers,
                HttpStatus.OK
            );
            
        }
        
        return new ResponseEntity<>(
                HttpStatus.OK
            );
    }

}
