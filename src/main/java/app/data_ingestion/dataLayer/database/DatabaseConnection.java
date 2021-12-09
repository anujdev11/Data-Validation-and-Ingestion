package app.data_ingestion.dataLayer.database;

import java.sql.Connection;
import java.sql.SQLException;

import org.springframework.jdbc.core.JdbcTemplate;

public class DatabaseConnection {


    /**
     * get mysql database connection
     *
     * @param jdbcTemplate
     * @return Connection
     * @throws SQLException
     */
    public static Connection getConnection(JdbcTemplate jdbcTemplate) throws SQLException {
        return jdbcTemplate.getDataSource().getConnection();
    }
}
