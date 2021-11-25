package app.data_ingestion.data_layer.daoImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import app.data_ingestion.data_layer.dao.FileTypeDao;
import app.data_ingestion.data_layer.database.DatabaseConnection;
import app.data_ingestion.data_layer.models.ColumnDetails;
import app.data_ingestion.data_layer.models.FileType;

@Repository
public class FileTypeDaoImpl extends JdbcDaoSupport implements FileTypeDao {

    static Connection connection;
    final DataSource dataSource;
    final JdbcTemplate jdbcTemplate;

    public FileTypeDaoImpl(JdbcTemplate jdbcTemplate, DataSource dataSource){
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
    public FileType getFileTypeById(int id) throws SQLException, JsonMappingException, JsonProcessingException {
        FileType fileType = null;

        String query = "select * from file_definition where file_definition_id = ? limit 1";
        PreparedStatement prepared_statment = DaoUtility.createPrepareStatement(connection, query, id);
        System.out.println("----prepared_statment-----"+prepared_statment);
        ResultSet rs = prepared_statment.executeQuery();
        if(rs.next() == false){
            throw new SQLException("No file definition for id: "+id);
        }
        else{
            fileType= new FileType();
            System.out.println("----rs-----"+rs.getString("file_definition_name"));
            fileType.setFileTypeId(rs.getInt("file_definition_id"));
            fileType.setFileTypeName(rs.getString("file_definition_name"));
            
            String columnDetails = rs.getString("file_definition_details");


            ObjectMapper objectMapper = new ObjectMapper();
            ColumnDetails[] colDetailsArray = objectMapper.readValue(columnDetails, ColumnDetails[].class);
            System.out.println("---colDetailsArray---- ");

            System.out.println(colDetailsArray.length);
            fileType.setColumnDetails(Arrays.asList(colDetailsArray) );
        }
        
        return fileType;
        
    }

}