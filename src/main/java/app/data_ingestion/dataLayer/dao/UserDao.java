package app.data_ingestion.dataLayer.dao;


import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import app.data_ingestion.dataLayer.database.DatabaseConnection;
import app.data_ingestion.dataLayer.models.User;
import app.data_ingestion.helpers.LiteralConstants;
import app.data_ingestion.helpers.QueryConstants;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

@Repository
public class UserDao extends JdbcDaoSupport implements IUserDao {

    static Connection connection;
    final DataSource dataSource;
    final JdbcTemplate jdbcTemplate;

    public UserDao(JdbcTemplate jdbcTemplate, DataSource dataSource) {
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

    /**
     * @param user
     * @return
     * @throws SQLException
     */
    @Override
    public int addUser(User user) throws SQLException {
        String insertQuery = QueryConstants.USER_INSERT_QUERY;
        PreparedStatement preparedStatment = DaoUtility.createPrepareStatement(connection, insertQuery, false,
                user.getUsername(),
                user.getPassword(),
                user.getAccessLevel(),
                user.getOrganization());

        return preparedStatment.executeUpdate();
    }

    /**
     * @param username
     * @throws SQLException
     */
    @Override
    public void deleteUser(String username) throws SQLException {
        String deleteQuery = QueryConstants.USER_DELETE_QUERY;
        PreparedStatement preparedStatment = DaoUtility.createPrepareStatement(connection, deleteQuery, false, username);
        preparedStatment.executeUpdate();
    }

    /**
     * @param username
     * @return
     * @throws SQLException
     */
    @Override
    public User getUser(String username) throws SQLException {
        String selectQuery = QueryConstants.USER_SELECT_QUERY;
        PreparedStatement preparedStatment = DaoUtility.createPrepareStatement(connection, selectQuery, false, username);
        ResultSet rs = preparedStatment.executeQuery();
        User user = null;
        while (rs.next()) {
            user = new User(rs.getString(LiteralConstants.USERNAME),
                    rs.getString(LiteralConstants.PASSWORD),
                    rs.getString(LiteralConstants.ACCESS_LEVEL),
                    rs.getString(LiteralConstants.ORGANIZATION));
        }
        return user;
    }

    /**
     * @return
     * @throws SQLException
     */
    @Override
    public List<User> getUsers() throws SQLException {
        String selectQuery = QueryConstants.USER_SELECT_QUERY;
        PreparedStatement preparedStatment = connection.prepareStatement(selectQuery);
        ResultSet rs = preparedStatment.executeQuery();
        List<User> users = new ArrayList<User>();

        while (rs.next()) {
            users.add(
                    new User(rs.getString(LiteralConstants.USERNAME),
                            rs.getString(LiteralConstants.PASSWORD),
                            rs.getString(LiteralConstants.ACCESS_LEVEL),
                            rs.getString(LiteralConstants.ORGANIZATION))
            );
        }
        return users;
    }

    /**
     * @param user
     * @throws SQLException
     */
    @Override
    public void updateUser(User user) throws SQLException {
        String updateQuery = QueryConstants.USER_UPDATE_QUERY;
        PreparedStatement preparedStatment = DaoUtility.createPrepareStatement(connection, updateQuery, false,
                user.getPassword(),
                user.getAccessLevel(),
                user.getOrganization());
        preparedStatment.executeUpdate();

    }


    /**
     * @param username
     * @return User
     * @throws SQLException
     */
    @Override
    public User getOrganizationAdmin(String username) throws SQLException {
        String selectQuery = QueryConstants.ORGANIZATION_ADMIN_SELECT_QUERY;
        User user = null;
        try (PreparedStatement preparedStatement = DaoUtility.createPrepareStatement(connection, selectQuery, false,
                username);
             ResultSet resultSet = preparedStatement.executeQuery();) {
            while (resultSet.next()) {
                user = new User(resultSet.getString(LiteralConstants.USERNAME),
                        resultSet.getString(LiteralConstants.ACCESS_LEVEL),
                        resultSet.getString(LiteralConstants.ORGANIZATION));
            }
            return user;
        }
    }


    /**
     * @param user
     * @return User
     * @throws SQLException
     */
    @Override
    public User addOrganization(User user) throws SQLException {
        String insertQuery = QueryConstants.ORGANIZATION_ADMIN_INSERT_QUERY;
        try (PreparedStatement preparedStatement = DaoUtility.createPrepareStatement(connection, insertQuery, true,
                user.getUsername(), user.getPassword(), user.getOrganization(), user.getAccessLevel());) {
            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException(LiteralConstants.SQL_USER_INSERTION_FAILED_STRING);
            }
            return user;
        }
    }


    /**
     * @param user
     * @return User
     * @throws SQLException
     */
    @Override
    public User deleteOrganization(User user) throws SQLException {
        String selectQuery = QueryConstants.ORGANIZATION_ADMIN_DELETE_QUERY;
        try (PreparedStatement preparedStatement = DaoUtility.createPrepareStatement(connection, selectQuery, false,
                user.getUsername());) {
            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException(LiteralConstants.SQL_USER_UPDATING_FAILED_STRING);
            }
            return user;
        }
    }


    /**
     * @param user
     * @return User
     * @throws SQLException
     */
    @Override
    public User updatedOrganization(User user) throws SQLException {
        String updateQuery = QueryConstants.ORGANIZATION_ADMIN_UPDATE_QUERY;
        try (PreparedStatement preparedStatement = DaoUtility.createPrepareStatement(connection, updateQuery, true,
                user.getPassword(), user.getOrganization(), user.getAccessLevel(), user.getUsername());) {
            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException(LiteralConstants.SQL_USER_UPDATING_FAILED_STRING);
            }
            return user;
        }
    }


    /**
     * @return List<User>
     * @throws SQLException
     */
    @Override
    public List<User> listAllOrganizations() throws SQLException {
        String selectQuery = QueryConstants.ORGANIZATION_ADMIN_LIST_SELECT_QUERY;
        try (PreparedStatement preparedStatement = DaoUtility.createPrepareStatement(connection, selectQuery, true);
             ResultSet resultSet = preparedStatement.executeQuery();) {
            List<User> users = new ArrayList<>();
            while (resultSet.next()) {
                users.add(
                        new User(resultSet.getString(LiteralConstants.USERNAME),
                                resultSet.getString(LiteralConstants.ACCESS_LEVEL),
                                resultSet.getString(LiteralConstants.ORGANIZATION)));
            }
            return users;
        }
    }
    
    /** 
     * @return List<User>
     * @throws SQLException
     */
    @Override
    public List<User> listAllOrganizationsUser(String organizationName) throws SQLException {
        String selectQuery = QueryConstants.ORGANIZATION_USER_LIST_SELECT_QUERY;
        
        try (PreparedStatement preparedStatement = DaoUtility.createPrepareStatement(connection, selectQuery, false , organizationName);
                ResultSet resultSet = preparedStatement.executeQuery();){
        	
            List<User> users = new ArrayList<>();
            while (resultSet.next()) {
                users.add(
                        new User(resultSet.getString(LiteralConstants.USERNAME),
                                resultSet.getString(LiteralConstants.ACCESS_LEVEL),
                                resultSet.getString(LiteralConstants.ORGANIZATION)));
            }
            return users;
        }
    }

}
