package app.data_ingestion.data_layer;


public interface UserAuth {

    public boolean register(String username, String password, String access_level) throws Exception;

    public boolean authenticate(String username, String password, String access_level);
}
