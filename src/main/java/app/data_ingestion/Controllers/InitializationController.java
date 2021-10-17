package app.data_ingestion.Controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class InitializationController {
    
    @GetMapping(path = "/initialize")
    public String initializeApp(){
        return "Application has been initialized. You may proceed.";
    }

}
