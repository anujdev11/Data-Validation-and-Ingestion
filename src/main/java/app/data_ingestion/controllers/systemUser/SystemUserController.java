package app.data_ingestion.controllers.systemUser;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import app.data_ingestion.dataLayer.models.SystemUser;
import app.data_ingestion.exceptions.ResourceAlreadyExistsException;
import app.data_ingestion.exceptions.ResourceNotFoundException;
import app.data_ingestion.helpers.GenericControllerOperations;
import app.data_ingestion.helpers.LiteralConstants;
import app.data_ingestion.services.systemUserService.ISystemUserService;

@CrossOrigin
@RestController
public class SystemUserController {

    @Autowired
    ISystemUserService systemUserService;
    static final String SUCCESS_MESSAGE = LiteralConstants.LOGIN_SUCCESS_MESSAGE;
    static final String INVALID_CREDENTIALS_MESSAGE = LiteralConstants.LOGIN_INVALID_CREDENTIALS_MESSAGE;
    static final String SYSTEM_ERROR_MESSAGE = LiteralConstants.SYSTEM_ERROR_MESSAGE;

    /**
     * controller to authenticate a system user
     *
     * @param systemUser
     * @return
     */
    @PostMapping(path = "/system-user/authenticate")
    @ResponseBody
    public ResponseEntity<Object> authenticateSystemUser(@RequestBody SystemUser systemUser) {
        SystemUser response;
        try {
            response = systemUserService.systemUserAuthentication(systemUser.getUsername(),
                    systemUser.getPassword());
            return ResponseEntity.status(HttpStatus.OK)
                    .body(GenericControllerOperations.getInstance().createResponseBody(response.toJson()));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(GenericControllerOperations.getInstance().createResponseBody(e.getMessage()));
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(GenericControllerOperations.getInstance()
                            .createResponseBody(LiteralConstants.INTERNAL_SERVER_ERROR_STRING));
        }
    }

    /**
     * controller to add a system user
     *
     * @param systemUser
     * @return
     */
    @PostMapping(path = "/system-user/")
    @ResponseBody
    public ResponseEntity<Object> addSystemUser(@RequestBody SystemUser systemUser) {
        SystemUser response;
        try {
            response = systemUserService.addSystemUser(systemUser);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(GenericControllerOperations.getInstance().createResponseBody(response.toJson()));
        } catch (ResourceAlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(GenericControllerOperations.getInstance().createResponseBody(e.getMessage()));
        } catch (SQLException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(GenericControllerOperations.getInstance()
                            .createResponseBody(LiteralConstants.INTERNAL_SERVER_ERROR_STRING));
        }
    }


    /**
     * controller to update a system user
     *
     * @param username
     * @param systemUser
     * @return
     */
    @PutMapping(path = "/system-user/{username}")
    @ResponseBody
    public ResponseEntity<Object> updateSystemUser(@PathVariable(required = false, name = "username") String username,
                                                   @RequestBody SystemUser systemUser) {
        SystemUser response;
        systemUser.setUsername(username);
        try {
            response = systemUserService.updateSystemUser(systemUser);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(GenericControllerOperations.getInstance().createResponseBody(response.toJson()));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(GenericControllerOperations.getInstance().createResponseBody(e.getMessage()));
        } catch (SQLException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(GenericControllerOperations.getInstance()
                            .createResponseBody(LiteralConstants.INTERNAL_SERVER_ERROR_STRING));
        }
    }


    /**
     * controller to delete a system user
     *
     * @param username
     * @return
     */
    @DeleteMapping(path = "/system-user/{username}")
    @ResponseBody
    public ResponseEntity<Object> deleteSystemUser(@PathVariable(required = false, name = "username") String username) {
        SystemUser response;
        try {
            response = systemUserService.deleteSystemUser(username);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(GenericControllerOperations.getInstance().createResponseBody(response.toJson()));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(GenericControllerOperations.getInstance().createResponseBody(e.getMessage()));
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(GenericControllerOperations.getInstance()
                            .createResponseBody(LiteralConstants.INTERNAL_SERVER_ERROR_STRING));
        }
    }
}
