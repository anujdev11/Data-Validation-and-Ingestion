package app.data_ingestion.data_layer;

import org.springframework.beans.factory.annotation.Autowired;

import app.data_ingestion.data_layer.models.User;
import app.data_ingestion.data_layer.repositories.UserRepository;

public class UserAuthImpl implements UserAuth {

    @Autowired
    UserRepository user_repo;

    @Override
    public void register(User user) throws Exception {
        User existing_user = user_repo.findById(user.getUsername())
                            .orElseGet(() -> user_repo.save(user));
        
        if(existing_user != null){
            throw new Exception("Username already exists.");
        }
    }

    @Override
    public boolean authenticate(String username, String password) throws Exception {
        User user = user_repo.findById(username)
                    .orElseThrow(() -> new Exception("Authentication failed. User not found."));

        
        return user.getPassword().equals(password);
    }


    
}
