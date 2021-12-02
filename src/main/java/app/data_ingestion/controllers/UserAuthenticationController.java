package app.data_ingestion.controllers;

import java.util.HashMap;
import java.util.Map;

import app.data_ingestion.data_layer.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import app.data_ingestion.service_layer.services.UserService;
import app.data_ingestion.service_layer.servicesImpl.UserServiceStatus;
import app.data_ingestion.helpers.Helper;

@CrossOrigin(origins = "http://localhost:5555")
@RestController

public class UserAuthenticationController {

    @Autowired
    UserService userService;
    static final String SUCCESS_MESSAGE = "Login Successful";
    static final String INVALID_CREDENTIALS_MESSAGE = "Invalid username/password";
    static final String SYSTEM_ERROR_MESSAGE = "System error";

    /**
     * @param users
     * @return
     */
    @PostMapping(path = "/users/authenticate")
    @ResponseBody
    public ResponseEntity<Object> userAuthentication(@RequestBody User users) {
        UserServiceStatus status = userService.userAuthentication(users.getUsername(), users.getPassword());
        Map<String, String> body_map = new HashMap<String, String>();
        if (status == UserServiceStatus.SUCCESS) {
            body_map.put("status", "200");
            body_map.put("message", SUCCESS_MESSAGE);
            body_map.put("access_level", userService.getUser().getAccess_level());
            return ResponseEntity.status(HttpStatus.OK).body(Helper.createResponseBody(body_map));
        } else if (status == UserServiceStatus.INVALID_CREDENTIALS) {
            body_map.put("message", INVALID_CREDENTIALS_MESSAGE);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Helper.createResponseBody(body_map));
        }
        body_map.put("message", SYSTEM_ERROR_MESSAGE);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Helper.createResponseBody(body_map));
    }

}
