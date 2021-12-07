package app.data_ingestion.controllers.userAuthAndRegister;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import app.data_ingestion.dataLayer.models.User;
import app.data_ingestion.helpers.GenericControllerOperations;
import app.data_ingestion.helpers.LiteralConstants;
import app.data_ingestion.services.userService.IUserService;
import app.data_ingestion.services.userService.UserServiceStatus;

@CrossOrigin(origins = "http://localhost:5555")
@RestController

public class UserAuthenticationController {

    @Autowired
    IUserService userService;
    static final String SUCCESS_MESSAGE = LiteralConstants.LOGIN_SUCCESS_MESSAGE;
    static final String INVALID_CREDENTIALS_MESSAGE = LiteralConstants.LOGIN_INVALID_CREDENTIALS_MESSAGE;
    static final String SYSTEM_ERROR_MESSAGE = LiteralConstants.SYSTEM_ERROR_MESSAGE;

    /**
     * @param users
     * @return
     */
    @PostMapping(path = "/users/authenticate")
    @ResponseBody
    public ResponseEntity<Object> userAuthentication(@RequestBody User users) {
        UserServiceStatus status = userService.userAuthentication(users.getUsername(), users.getPassword());
        Map<String, String> bodyMap = new HashMap<String, String>();

        if (status == UserServiceStatus.SUCCESS) {
            bodyMap.put(LiteralConstants.STATUS, String.valueOf(HttpStatus.OK.value()));
            bodyMap.put(LiteralConstants.MESSAGE, SUCCESS_MESSAGE);
            bodyMap.put(LiteralConstants.ACCESS_LEVEL, userService.getUser().getAccessLevel());

            return ResponseEntity.status(HttpStatus.OK).body(GenericControllerOperations.getInstance().createResponseBody(bodyMap));
        } 
        else if (status == UserServiceStatus.INVALID_CREDENTIALS) {

            bodyMap.put(LiteralConstants.MESSAGE, INVALID_CREDENTIALS_MESSAGE);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(GenericControllerOperations.getInstance().createResponseBody(bodyMap));
        }
        
        bodyMap.put(LiteralConstants.MESSAGE, SYSTEM_ERROR_MESSAGE);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(GenericControllerOperations.getInstance().createResponseBody(bodyMap));
    }

}
