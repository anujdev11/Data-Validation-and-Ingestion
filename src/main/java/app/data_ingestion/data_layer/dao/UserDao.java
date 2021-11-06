package app.data_ingestion.data_layer.dao;

import java.sql.SQLException;
import java.util.List;

import app.data_ingestion.data_layer.models.User;

public interface UserDao {

    /**
     * @param username
     * @param password
     * @return
     */
    
    public int add(User user) throws SQLException;
    public void delete(String username) throws SQLException;
    public User getUser(String username) throws SQLException;
    public List<User> getUsers() throws SQLException;
    public void update(User user) throws SQLException;

}
