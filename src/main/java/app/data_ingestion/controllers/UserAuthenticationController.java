package app.data_ingestion.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import app.data_ingestion.business_logic_layer.services.UserService;
import app.data_ingestion.business_logic_layer.servicesImpl.UserServiceStatus;

@RestController
public class UserAuthenticationController {

    @Autowired
    UserService userService;
    static final String SUCCESS_MESSAGE = "Login Successful";
    static final String INVALID_CREDENTIALS_MESSAGE = "Invalid username/password";
    static final String SYSTEM_ERROR_MESSAGE = "System error";

    /**
     * @param username
     * @param password
     * @return
     */
    @PostMapping(path = "/users/authenticate")
    @ResponseBody
    public ResponseEntity<Object> userAuthentication(@RequestParam String username, @RequestParam String password){
        UserServiceStatus status = userService.userAuthentication(username, password);
        if(status == UserServiceStatus.SUCCESS){
            return ResponseEntity.status(HttpStatus.OK).body(SUCCESS_MESSAGE);  
        }
        else if(status == UserServiceStatus.INVALID_CREDENTIALS){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(INVALID_CREDENTIALS_MESSAGE);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(SYSTEM_ERROR_MESSAGE);
    }
    
}
