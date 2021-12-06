package app.data_ingestion.controllers.organization_admin;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import app.data_ingestion.dataLayer.models.User;
import app.data_ingestion.exceptions.ResourceAlreadyExistsException;
import app.data_ingestion.exceptions.ResourceNotFoundException;
import app.data_ingestion.helpers.GenericControllerOperations;
import app.data_ingestion.helpers.LiteralConstants;
import app.data_ingestion.services.organization_admin_service.IOrganizationAdminService;

@CrossOrigin(origins = "http://localhost:5555")
@RestController
public class OrganizationManagementController {

    @Autowired
    IOrganizationAdminService organizationAdminService;
    static final String SUCCESS_MESSAGE = LiteralConstants.LOGIN_SUCCESS_MESSAGE;
    static final String INVALID_CREDENTIALS_MESSAGE = LiteralConstants.LOGIN_INVALID_CREDENTIALS_MESSAGE;
    static final String SYSTEM_ERROR_MESSAGE = LiteralConstants.SYSTEM_ERROR_MESSAGE;

    /**
     *
     * @param systemUser
     * @return
     */
    @PostMapping(path = "/organization/admin/")
    @ResponseBody
    public ResponseEntity<Object> addOrganizationUser(@RequestBody User user) {
        User response;
        try {
            response = organizationAdminService.addOrganizationUser(user);
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

    @PutMapping(path = "/organization/admin/{username}")
    @ResponseBody
    public ResponseEntity<Object> updateSystemUser(@PathVariable(required = false, name = "username") String username,
            @RequestBody User user) {
        User response;
        user.setUsername(username);
        try {
            response = organizationAdminService.updateOrganization(user);
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

    @DeleteMapping(path = "/organization/admin/{username}")
    @ResponseBody
    public ResponseEntity<Object> deleteOrganizationUser(
            @PathVariable(required = false, name = "username") String username) {
        User response;
        try {
            response = organizationAdminService.deleteOrganization(username);
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

    @GetMapping(path = "/organization/admin/{username}")
    @ResponseBody
    public ResponseEntity<Object> getOrganizationUser(
            @PathVariable(required = false, name = "username") String username) {
        User response;
        try {
            response = organizationAdminService.getOrganizationAdmin(username);
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

    @GetMapping(path = "/organization/admin/")
    @ResponseBody
    public ResponseEntity<Object> getOrganizationUser() {
        try {
            List<User> response = organizationAdminService.listAllOrganizationAdmin();
            return ResponseEntity.status(HttpStatus.OK)
                    .body(GenericControllerOperations.getInstance().createResponseBody(response));
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
