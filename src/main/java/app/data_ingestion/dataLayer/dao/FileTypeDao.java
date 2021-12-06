package app.data_ingestion.dataLayer.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import app.data_ingestion.dataLayer.database.DatabaseConnection;
import app.data_ingestion.dataLayer.models.ColumnDetails;
import app.data_ingestion.dataLayer.models.FileType;
import app.data_ingestion.helpers.LiteralConstants;
import app.data_ingestion.helpers.QueryConstants;

@Repository
public class FileTypeDao extends JdbcDaoSupport implements IFileTypeDao {

    static Connection connection;
    final DataSource dataSource;
    final JdbcTemplate jdbcTemplate;

    public FileTypeDao(JdbcTemplate jdbcTemplate, DataSource dataSource) {
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
     * @param fileTypeDef
     * @return
     * @throws SQLException
     */
    @Override
    public int addFileDefinition(FileType fileTypeDef) throws SQLException {

        String listString = fileTypeDef.getColumnDetails().stream().map(Object::toString)
                .collect(Collectors.joining(", "));
        String fileDefinitionColumnDetails = "[" + listString + "]";

        String insert_query = QueryConstants.FILE_DEFINITION_INSERT_QUERY;
        PreparedStatement prepared_statment = DaoUtility.createPrepareStatement(connection, insert_query, fileTypeDef.getFileTypeName(), fileDefinitionColumnDetails);

        return prepared_statment.executeUpdate();
    }

    /**
     * @param id
     * @return
     * @throws SQLException
     * @throws JsonMappingException
     * @throws JsonProcessingException
     */
    @Override
    public FileType getFileTypeById(int id) throws SQLException, JsonMappingException, JsonProcessingException {
        FileType fileType = null;

        String query = QueryConstants.FILE_DEFINITION_SELECT_QUERY;
        PreparedStatement prepared_statment = DaoUtility.createPrepareStatement(connection, query, id);
        ResultSet rs = prepared_statment.executeQuery();
        if (rs.next() == false) {
            throw new SQLException(LiteralConstants.NO_FILE_DEFINITION_FOR_ID + id);
        } 
        else {
            fileType = new FileType();
            fileType.setFileTypeId(rs.getInt(LiteralConstants.FILE_DEFINITION_ID));
            fileType.setFileTypeName(rs.getString(LiteralConstants.FILE_DEFINITION_NAME));
            String columnDetails = rs.getString(LiteralConstants.FILE_DEFINITION_DETAILS);
            ObjectMapper objectMapper = new ObjectMapper();
            ColumnDetails[] colDetailsArray = objectMapper.readValue(columnDetails, ColumnDetails[].class);
            fileType.setColumnDetails(Arrays.asList(colDetailsArray));
        }
        return fileType;
    }

    /**
     * @param file_definition_id
     * @return
     * @throws SQLException
     * @throws JsonProcessingException
     */
    public boolean deleteFileDefinition(int file_definition_id) throws SQLException, JsonProcessingException {
        FileType fileType = getFileTypeById(file_definition_id);
        String fileTypeName = fileType.getFileTypeName();
        String delete_query = QueryConstants.FILE_DEFINITION_DELETE_QUERY;
        String delete_table = String.format(QueryConstants.DROP_QUERY, fileTypeName);
        PreparedStatement prepared_statement = DaoUtility.createPrepareStatement(connection, delete_query, file_definition_id);
        prepared_statement.executeUpdate();
        PreparedStatement prepared_statement_table = DaoUtility.createPrepareStatement(connection, delete_table);
        prepared_statement_table.executeUpdate();
        return true;
    }
    
    @Override
    public int updateFileDefinition(FileType fileTypeDef) throws SQLException {    	   	
    	String listString = fileTypeDef.getColumnDetails().stream().map(Object::toString)
                .collect(Collectors.joining(", "));
    	String fileDefinitionColumnDetails="["+listString+"]";	
    	String updateQuery = "update file_definition set file_definition_name = ? ,file_definition_details = ? where file_definition_id = ? ";    	
        PreparedStatement prepared_statment = DaoUtility.createPrepareStatement(connection, updateQuery,fileTypeDef.getFileTypeName(),fileDefinitionColumnDetails , fileTypeDef.getFileTypeId());
        return prepared_statment.executeUpdate(); 	

	}


}