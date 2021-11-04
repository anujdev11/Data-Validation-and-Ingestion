package app.data_ingestion.business_logic_layer.services;

import app.data_ingestion.data_layer.models.User;

public interface UserService {
    boolean userAuthentication(String username, String password);

    boolean userRegistration(User user);
}
