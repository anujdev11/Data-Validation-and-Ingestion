package app.data_ingestion.data_layer.database;

import java.sql.Connection;
import java.sql.SQLException;

import org.springframework.jdbc.core.JdbcTemplate;

public class DatabaseConnection {
  
    public static Connection getConnection(JdbcTemplate jdbcTemplate) throws SQLException
    {   
        return jdbcTemplate.getDataSource().getConnection();
    }
}
