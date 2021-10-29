package app.data_ingestion.data_layer;

import app.data_ingestion.data_layer.models.User;

public interface UserAuth {

    public void register(User user) throws Exception;

    public boolean authenticate(String username, String password) throws Exception;
}
