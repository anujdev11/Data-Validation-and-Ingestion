package app.data_ingestion.data_layer;

import org.springframework.beans.factory.annotation.Autowired;

import app.data_ingestion.data_layer.models.User;
import app.data_ingestion.data_layer.repositories.UserRepository;

public class UserAuthImpl implements UserAuth {

    @Autowired
    UserRepository user_repo;

    @Override
    public boolean register(String username, String password, String access_level) throws Exception {

        return false;
    }

    @Override
    public boolean authenticate(String username, String password, String access_level) {
        boolean is_authenticated = false;
        try{
            User user = user_repo.findById(username)
            .orElseThrow(() -> new Exception());;

            if(user.getPassword().equals(password)){
                is_authenticated = true;
            }
        }
        catch(Exception e){
            System.out.println("Authentication failed. User not found.");
        }
        
        return is_authenticated;
    }


    
}
