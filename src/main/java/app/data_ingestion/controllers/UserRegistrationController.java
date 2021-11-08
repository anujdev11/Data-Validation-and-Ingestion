package app.data_ingestion.controllers;

import app.data_ingestion.business_logic_layer.services.UserService;
import app.data_ingestion.business_logic_layer.servicesImpl.UserServiceStatus;
import app.data_ingestion.data_layer.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class UserRegistrationController {

    @Autowired
    UserService userService;
    static final String SUCCESS_MESSAGE = "Registration done successfully!";
    static final String USER_ALREADY_EXISTS_MESSAGE = "Username already exits. Please try with another one";
    static final String SYSTEM_ERROR_MESSAGE = "System error";

    @GetMapping(path = "/test")
    public String test(){
        return "service is working";
    }

    /**
     * @param user
     * @return
     */
    @PostMapping(path = "/users/register")
    public ResponseEntity<Object> userRegistration(@RequestBody User user){
        UserServiceStatus status = userService.userRegistration(user);
        if(status == UserServiceStatus.SUCCESS){
            return ResponseEntity.status(HttpStatus.CREATED).body(SUCCESS_MESSAGE);
        }
        else if(status == UserServiceStatus.USER_ALREADY_EXISTS){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(USER_ALREADY_EXISTS_MESSAGE);  
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(SYSTEM_ERROR_MESSAGE);         
    }

}
