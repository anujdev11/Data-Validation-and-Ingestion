package app.data_ingestion.controllers.userAuthAndRegister;

import app.data_ingestion.dataLayer.models.User;
import app.data_ingestion.helpers.LiteralConstants;
import app.data_ingestion.services.userAuthAndRegister.IUserService;
import app.data_ingestion.services.userAuthAndRegister.UserServiceStatus;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:5555")
@RestController
public class UserRegistrationController {

    static final String SUCCESS_MESSAGE = LiteralConstants.REGISTER_SUCCESS_MESSAGE;
    static final String USER_ALREADY_EXISTS_MESSAGE = LiteralConstants.REGISTER_USER_ALREADY_EXISTS_MESSAGE;
    static final String SYSTEM_ERROR_MESSAGE = LiteralConstants.SYSTEM_ERROR_MESSAGE;

    @Autowired
    IUserService userService;

    /**
     * controller to take registration request
     * @param user
     * @return
     */
    @PostMapping(path = "/users/register")
    public ResponseEntity<Object> userRegistration(@RequestBody User user) {
        UserServiceStatus status = userService.userRegistration(user);
        if (status == UserServiceStatus.SUCCESS) {
            return ResponseEntity.status(HttpStatus.CREATED).body(SUCCESS_MESSAGE);
        } else if (status == UserServiceStatus.USER_ALREADY_EXISTS) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(USER_ALREADY_EXISTS_MESSAGE);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(SYSTEM_ERROR_MESSAGE);
    }

}
