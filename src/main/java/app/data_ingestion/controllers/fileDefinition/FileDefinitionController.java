package app.data_ingestion.controllers.fileDefinition;

import app.data_ingestion.dataLayer.models.FileType;
import app.data_ingestion.helpers.LiteralConstants;
import app.data_ingestion.services.fileDefinition.IFileDefinitionService;
import app.data_ingestion.services.userAuthAndRegister.UserServiceStatus;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;

@CrossOrigin
@RestController
public class FileDefinitionController {

    @Autowired
    IFileDefinitionService fileDefService;
    static final String SUCCESS_MESSAGE = LiteralConstants.FILE_DEFINITION_CREATION_SUCCESS;
    static final String SYSTEM_ERROR_MESSAGE = LiteralConstants.SYSTEM_ERROR_MESSAGE;

    /**
     * add file_definition to the table
     *
     * @param fileType
     * @return
     */
    @PostMapping(path = "/fileDefinition")
    public ResponseEntity<Object> addFileDefinition(@RequestBody FileType fileType) {
        UserServiceStatus status = fileDefService.fileDefinition(fileType);
        if (status == UserServiceStatus.SUCCESS) {
            return ResponseEntity.status(HttpStatus.CREATED).body(SUCCESS_MESSAGE);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(SYSTEM_ERROR_MESSAGE);
    }

    /**
     * controller to get file_definition by id
     *
     * @param file_definition_id
     * @return http response
     */
    @PostMapping(path = "/getFileDefinitionById")
    public ResponseEntity<Object> getFileDefinitionById(@RequestParam("file_definition_id") int file_definition_id) throws JsonProcessingException {
        FileType fileType = fileDefService.getFileDefinitionById(file_definition_id);
        if (fileType != null) {
            return ResponseEntity.status(HttpStatus.CREATED).body(fileType);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(SYSTEM_ERROR_MESSAGE);
        }
    }

    /**
     * controller to delete file_definition record
     *
     * @param file_definition_id
     * @return http Response
     * @throws SQLException
     * @throws JsonProcessingException
     */
    @PostMapping(path = "/fileDefinition/delete")
    public ResponseEntity<Object> deleteFileDefinition(@RequestParam("file_definition_id") int file_definition_id)
            throws SQLException, JsonProcessingException {
        fileDefService.deleteFileDefinition(file_definition_id);
        return new ResponseEntity<>("Deleted", HttpStatus.OK);
    }

    /**
     * update filedefinition record
     *
     * @param fileType
     * @return
     */
    @PostMapping(path = "/updateFileDefinition")
    public ResponseEntity<Object> updateFileDefinition(@RequestBody FileType fileType) {
        UserServiceStatus status = fileDefService.updateFileDefinition(fileType);

        if (status == UserServiceStatus.SUCCESS) {
            return ResponseEntity.status(HttpStatus.CREATED).body(SUCCESS_MESSAGE);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(SYSTEM_ERROR_MESSAGE);
    }

}
