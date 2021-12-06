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
        PreparedStatement preparedStatment = DaoUtility.createPrepareStatement(connection, insertQuery,
                user.getUsername(),
                user.getPassword(),
                user.getAccess_level(),
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
        PreparedStatement preparedStatment = DaoUtility.createPrepareStatement(connection, deleteQuery, username);
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
        PreparedStatement preparedStatment = DaoUtility.createPrepareStatement(connection, selectQuery, username);
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
        PreparedStatement preparedStatment = DaoUtility.createPrepareStatement(connection, updateQuery,
                user.getPassword(),
                user.getAccess_level(),
                user.getOrganization());
        preparedStatment.executeUpdate();

    }

}
