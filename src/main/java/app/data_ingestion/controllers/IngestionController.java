package app.data_ingestion.controllers;

// import java.io.File;
import java.io.IOException;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class IngestionController {

    @PostMapping(path = "/ingestion")
    @ResponseBody
    public String ingestDataFromFile(@RequestParam("input_file") MultipartFile multipartFile,
                                    @RequestParam("delimiter") String delimiter) throws IllegalStateException, IOException {
        
        String content = new String(multipartFile.getBytes());
        System.out.println(content);
        return "success";
    }
}
