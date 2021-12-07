package app.data_ingestion.dataLayer.dao;

import app.data_ingestion.dataLayer.database.DatabaseConnection;
import app.data_ingestion.dataLayer.models.SystemUser;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;
import app.data_ingestion.helpers.LiteralConstants;
import app.data_ingestion.helpers.QueryConstants;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

@Repository
public class SystemUserDao extends JdbcDaoSupport implements ISystemUserDao {

    Connection connection;
    final DataSource dataSource;
    final JdbcTemplate jdbcTemplate;

    public SystemUserDao(JdbcTemplate jdbcTemplate, DataSource dataSource) {
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
     * @param systemUser
     * @return SystemUser
     * @throws SQLException
     */
    @Override
    public SystemUser addSystemUser(SystemUser systemUser)
            throws SQLException {
        String selectQuery = QueryConstants.SYSTEM_USER_INSERT_QUERY;

        try (PreparedStatement preparedStatement = DaoUtility.createPrepareStatement(connection, selectQuery, true,
                systemUser.getFirstName(), systemUser.getLastName(), systemUser.getUsername(),
                systemUser.getPassword());) {
            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException(LiteralConstants.SQL_USER_INSERTION_FAILED_STRING);
            }

            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    systemUser.setId(generatedKeys.getInt(1));
                } else {
                    throw new SQLException(LiteralConstants.SQL_FETCHING_ID_FAILED_STRING);
                }
            }
            return systemUser;
        }
    }


    /**
     * @param systemUser
     * @return SystemUser
     * @throws SQLException
     */
    @Override
    public SystemUser deleteSystemUser(SystemUser systemUser) throws SQLException {
        String selectQuery = QueryConstants.SYSTEM_USER_DELETE_QUERY;
        try (PreparedStatement preparedStatement = DaoUtility.createPrepareStatement(connection, selectQuery, false,
                systemUser.getUsername());) {
            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException(LiteralConstants.SQL_USER_UPDATING_FAILED_STRING);
            }
            return systemUser;
        }

    }


    /**
     * @param username
     * @param password
     * @return SystemUser
     * @throws SQLException
     */
    @Override
    public SystemUser authenticateSystemUser(String username, String password) throws SQLException {
        String selectQuery = QueryConstants.SYSTEM_USER_AUTHENTICATE_QUERY;
        SystemUser systemUser = null;
        try (PreparedStatement preparedStatement = DaoUtility.createPrepareStatement(connection, selectQuery, false,
                username, password);
             ResultSet resultSet = preparedStatement.executeQuery();) {
            while (resultSet.next()) {
                systemUser = new SystemUser(resultSet.getInt(LiteralConstants.ID),
                        resultSet.getString(LiteralConstants.FIRST_NAME),
                        resultSet.getString(LiteralConstants.LAST_NAME),
                        resultSet.getString(LiteralConstants.USERNAME));
            }
            return systemUser;
        }
    }


    /**
     * @param username
     * @return SystemUser
     * @throws SQLException
     */
    @Override
    public SystemUser getSystemUser(String username) throws SQLException {
        String selectQuery = QueryConstants.SYSTEM_USER_SELECT_QUERY;
        SystemUser systemUser = null;
        try (PreparedStatement preparedStatement = DaoUtility.createPrepareStatement(connection, selectQuery, false,
                username);
             ResultSet resultSet = preparedStatement.executeQuery();) {
            while (resultSet.next()) {
                systemUser = new SystemUser(resultSet.getInt(LiteralConstants.ID),
                        resultSet.getString(LiteralConstants.FIRST_NAME),
                        resultSet.getString(LiteralConstants.LAST_NAME),
                        resultSet.getString(LiteralConstants.USERNAME));
            }
            return systemUser;
        }
    }


    /**
     * @param systemUser
     * @return SystemUser
     * @throws SQLException
     */
    @Override
    public SystemUser updateSystemUser(SystemUser systemUser) throws SQLException {
        String updateQuery = QueryConstants.SYSTEM_USER_UPDATE_QUERY;
        try (PreparedStatement preparedStatement = DaoUtility.createPrepareStatement(connection, updateQuery, true,
                systemUser.getFirstName(), systemUser.getLastName(), systemUser.getPassword(),
                systemUser.getUsername());) {
            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException(LiteralConstants.SQL_USER_UPDATING_FAILED_STRING);
            }
            return systemUser;
        }
    }
}
