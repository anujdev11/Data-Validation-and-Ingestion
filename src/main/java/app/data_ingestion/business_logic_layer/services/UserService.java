package app.data_ingestion.business_logic_layer.services;

import app.data_ingestion.business_logic_layer.servicesImpl.UserServiceStatus;
import app.data_ingestion.data_layer.models.User;

public interface UserService {
    /**
     * @param username
     * @param password
     * @return
     */
    public UserServiceStatus userAuthentication(String username, String password);

    /**
     * @param user
     * @return
     */
    public UserServiceStatus userRegistration(User user);
}
