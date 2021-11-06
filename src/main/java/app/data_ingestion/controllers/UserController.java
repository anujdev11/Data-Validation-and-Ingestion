package app.data_ingestion.controllers;

import app.data_ingestion.business_logic_layer.services.UserService;
import app.data_ingestion.data_layer.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController

public class UserController {

    @Autowired
    UserService userService;


    @GetMapping(path = "/userAuthentication")
    public boolean userAuthentication(){
        return userService.userAuthentication("anujdev","Anujdev");
    }

    @GetMapping(path = "/userRegistration")
    public boolean userRegistration(){
        User user = new User("anujdev1","Anu","admin","Dal");
        return userService.userRegistration(user);
    }

}
