package app.data_ingestion.business_logic_layer.services;

import app.data_ingestion.business_logic_layer.servicesImpl.UserServiceStatus;
import app.data_ingestion.data_layer.models.User;

public interface UserService {

    /**
    * @return User
    */
    public User getUser();

    /**
     * @param username
     * @param password
     * @return UserServiceStatus
     */
    public UserServiceStatus userAuthentication(String username, String password);

    /**
     * @param user
     * @return UserServiceStatus
     */
    public UserServiceStatus userRegistration(User user);
}
