package app.data_ingestion.services.user_service;

import app.data_ingestion.dataLayer.models.User;

public interface IUserService {

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
