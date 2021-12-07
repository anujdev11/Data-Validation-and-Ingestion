package app.data_ingestion.controllers.validationAndIngestion;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import app.data_ingestion.config.ConfigReader;
import app.data_ingestion.dataLayer.models.FileType;
import app.data_ingestion.dataLayer.models.User;
import app.data_ingestion.exceptions.ResourceNotFoundException;
import app.data_ingestion.helpers.ConfigPropertiesKeyConstants;
import app.data_ingestion.helpers.GenericControllerOperations;
import app.data_ingestion.helpers.LiteralConstants;
import app.data_ingestion.services.userAuthAndRegister.UserServiceStatus;
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
    public ResponseEntity<Object> ingestDataFromFile(@RequestParam("file_definition_id") String fileDefinitionId,
                                                     @RequestParam("input_file") MultipartFile multipartFile,
                                                     @RequestParam("delimiter") String delimiter,
                                                     @RequestParam("action") String action) throws Exception {

        ingestionService.ingestData(Integer.valueOf(fileDefinitionId), multipartFile, delimiter, action);
        String fileName = ConfigReader.getInstance().getProperty(ConfigPropertiesKeyConstants.INVALID_ROWS_CSV_PATH);
        InputStreamResource fileInputStream = null;
        File file = new File(fileName);
        if (file.exists()) {
            InputStream in = new FileInputStream(fileName);
            fileInputStream = new InputStreamResource(in);

            HttpHeaders headers = new HttpHeaders();
            headers.set(HttpHeaders.CONTENT_DISPOSITION, LiteralConstants.CONTENT_DISPOSITION_ATTACHMENT + fileName);
            headers.set(HttpHeaders.CONTENT_TYPE, LiteralConstants.INGEST_CONTENT_TYPE);

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
    
    
    /**
     * @param fileType
     * @return
     * @throws Exception 
     */
    @PostMapping(path = "/editInlineData")
    public ResponseEntity<Object> editInlineData( @RequestBody Map<String,Object> body) throws Exception {
        UserServiceStatus status = ingestionService.editInlineData(body);

        if (status == UserServiceStatus.SUCCESS) {
            return ResponseEntity.status(HttpStatus.OK).body(LiteralConstants.SQL_UPDATE_DONE); //TODO: 
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(LiteralConstants.SYSTEM_ERROR_MESSAGE); //TODO:
    }
    
   


}