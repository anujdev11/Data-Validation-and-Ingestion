package app.data_ingestion.controllers.validationAndIngestion;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import app.data_ingestion.config.ConfigReader;
import app.data_ingestion.helpers.ConfigPropertiesKeyConstants;
import app.data_ingestion.services.validationAndIngestion.IIngestionService;
import app.data_ingestion.services.validationAndIngestion.IngestionService;

@CrossOrigin(origins = "http://localhost:5555")
@RestController
public class IngestionController {

    @Autowired
    IIngestionService ingestionService;

    /**
     * @param file_definition_id
     * @param multipartFile
     * @param delimiter
     * @return
     * @throws Exception
     */
    @PostMapping(path = "/ingestion", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    @ResponseBody
    public ResponseEntity<Object> ingestDataFromFileAppend(@RequestParam("file_definition_id") String file_definition_id,
                                                     @RequestParam("input_file") MultipartFile multipartFile,
                                                     @RequestParam("delimiter") String delimiter,
                                                     @RequestParam("action") String action) throws Exception {

        System.out.println(file_definition_id);
        System.out.println(delimiter);
        ingestionService.ingestData(Integer.valueOf(file_definition_id), multipartFile, delimiter, action);
        String fileName = ConfigReader.getInstance().getProperty(ConfigPropertiesKeyConstants.INVALID_ROWS_CSV_PATH);
        InputStreamResource fileInputStream = null;
        File file = new File(fileName);
        if (file.exists()) {
            InputStream in = new FileInputStream(fileName);
            fileInputStream = new InputStreamResource(in);

            HttpHeaders headers = new HttpHeaders();
            headers.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName);
            headers.set(HttpHeaders.CONTENT_TYPE, "text/csv");

            return new ResponseEntity<>(
                    fileInputStream,
                    headers,
                    HttpStatus.OK);

        }

        return new ResponseEntity<>(
                HttpStatus.OK);
    }

    /**
     * @param ruleType
     * @return
     */
    @GetMapping(path = "/getValidationRules")
    public ResponseEntity<Object> getValidationRules(@RequestParam String ruleType) {
        String[] validationRule = IngestionService.getValidationRule(ruleType);
        return new ResponseEntity<>(validationRule, HttpStatus.OK);
    }

}