package app.data_ingestion.data_layer.repositories;

import app.data_ingestion.data_layer.models.User;
import org.springframework.stereotype.Repository;

public interface UserRepository {

//    public void register(User user) throws Exception;

    /**
     * @param username
     * @param password
     * @return
     */
    boolean userAuthentication(String username, String password);

    boolean userRegistration(User user);

}
