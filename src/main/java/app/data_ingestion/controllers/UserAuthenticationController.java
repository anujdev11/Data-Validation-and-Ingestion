package app.data_ingestion.controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import app.data_ingestion.business_logic_layer.services.UserService;
import app.data_ingestion.business_logic_layer.servicesImpl.UserServiceStatus;
import app.data_ingestion.helpers.Helper;

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
        Map<String,String> body_map = new HashMap<String, String>();
        if(status == UserServiceStatus.SUCCESS){
            
            
            body_map.put("message", SUCCESS_MESSAGE);
            body_map.put("access_level", userService.getUser().getAccess_level());
            return ResponseEntity.status(HttpStatus.OK).body(Helper.createResponseBody(body_map));  
        }
        else if(status == UserServiceStatus.INVALID_CREDENTIALS){
            body_map.put("message", INVALID_CREDENTIALS_MESSAGE);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Helper.createResponseBody(body_map)); 
        }
        body_map.put("message", SYSTEM_ERROR_MESSAGE);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Helper.createResponseBody(body_map)); 
    }
    
}
