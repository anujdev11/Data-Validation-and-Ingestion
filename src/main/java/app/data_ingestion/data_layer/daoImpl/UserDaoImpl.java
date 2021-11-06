package app.data_ingestion.data_layer.daoImpl;


import app.data_ingestion.data_layer.dao.UserDao;
import app.data_ingestion.data_layer.database.DatabaseConnection;
import app.data_ingestion.data_layer.models.User;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

@Repository
public class UserDaoImpl extends JdbcDaoSupport implements UserDao {

    static Connection connection;
    final DataSource dataSource;
    final JdbcTemplate jdbcTemplate;

    public UserDaoImpl(JdbcTemplate jdbcTemplate, DataSource dataSource){
        this.jdbcTemplate = jdbcTemplate;
        this.dataSource = dataSource;
        try {
            connection = DatabaseConnection.getConnection(this.jdbcTemplate);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    
    @PostConstruct
    private void initialize() {
        setDataSource(dataSource);
    }


    @Override
    public int add(User user) throws SQLException {
        String insert_query = "insert into user (username, "
              + "password, access_level, "
              + "organization) VALUES (?, ?, ?, ?)";
        PreparedStatement prepared_statment = createPrepareStatement(insert_query, 
              user.getPassword(), 
              user.getAccess_level(), 
              user.getOrganization());
        return prepared_statment.executeUpdate();
    }

    @Override
    public void delete(String username) throws SQLException {
        String delete_query = "delete from user where username = ?";
        PreparedStatement prepared_statment = createPrepareStatement(delete_query, username);
        prepared_statment.executeUpdate();
    }

    @Override
    public User getUser(String username) throws SQLException {
        String select_query = "select * from user where username= ? limit 1";
        PreparedStatement prepared_statment = createPrepareStatement(select_query, username);
        ResultSet rs = prepared_statment.executeQuery();
        User user = null;
        while (rs.next()) {
            user= new User(rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("access_level"),
                        rs.getString("organization"));
        }
        return user;
    }

    @Override
    public List<User> getUsers() throws SQLException {
        String select_query = "select * from user";
        PreparedStatement prepared_statment = connection.prepareStatement(select_query);
        ResultSet rs = prepared_statment.executeQuery();
        List<User> users = new ArrayList<User>();
  
        while (rs.next()) {
            users.add(
                new User(rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("access_level"),
                        rs.getString("organization"))
            );
        }
        return users;
    }

    @Override
    public void update(User user) throws SQLException {
        String update_query = "update user set password= ?, access_level= ?, organization= ? where username = ?";
        PreparedStatement prepared_statment = createPrepareStatement(update_query, 
                                                user.getPassword(), 
                                                user.getAccess_level(), 
                                                user.getOrganization());
        prepared_statment.executeUpdate();
        
    }

    private PreparedStatement createPrepareStatement(String sql, String... params) throws SQLException {
        PreparedStatement prepared_statment = connection.prepareStatement(sql);
        int counter = 1;
        for(String param : params){
            prepared_statment.setString(counter, param);
            counter++;
        }
        return prepared_statment;
    }

}
