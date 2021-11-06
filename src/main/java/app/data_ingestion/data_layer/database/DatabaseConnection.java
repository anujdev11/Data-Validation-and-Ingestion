package app.data_ingestion.data_layer.database;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseConnection {
  
    private static Connection connection = null;
  
    static
    {   
        try (InputStream input = new FileInputStream("../src/app/data_ingestion/resources/config.properties")) {

            Properties prop = new Properties();
            prop.load(input);
            String db_url = prop.getProperty("spring.datasource.url");
            String db_usename = prop.getProperty("spring.datasource.username");
            String db_password = prop.getProperty("spring.datasource.password");
            try {
                Class.forName("com.mysql.jdbc.Driver");
                connection = DriverManager.getConnection(db_url, db_usename, db_password);
            }
            catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
            }

        } catch (IOException ex) {
            ex.printStackTrace();
        }

        
    }
    public static Connection getConnection()
    {
        return connection;
    }
}
