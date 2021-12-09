package app.data_ingestion.dataLayer.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import app.data_ingestion.dataLayer.database.DatabaseConnection;
import app.data_ingestion.dataLayer.models.ColumnDetails;
import app.data_ingestion.dataLayer.models.FileType;
import app.data_ingestion.dataLayer.models.FileTypeFactory;
import app.data_ingestion.helpers.LiteralConstants;
import app.data_ingestion.helpers.QueryConstants;

@Repository
public class FileTypeDao extends JdbcDaoSupport implements IFileTypeDao {

    @Autowired
    ObjectMapper objectMapper;

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
     * add file definition record in database
     *
     * @param fileTypeDef
     * @return
     * @throws SQLException
     */
    @Override
    public int addFileDefinition(FileType fileTypeDef) throws SQLException {

        String listString = fileTypeDef.getColumnDetails().stream().map(Object::toString)
                .collect(Collectors.joining(", "));
        String fileDefinitionColumnDetails = "[" + listString + "]";

        String insertQuery = QueryConstants.FILE_DEFINITION_INSERT_QUERY;
        PreparedStatement preparedStatment = DaoUtility.createPrepareStatement(connection, insertQuery, false, fileTypeDef.getFileTypeName(), fileDefinitionColumnDetails);

        return preparedStatment.executeUpdate();
    }

    /**
     * get file definition record based on id
     *
     * @param id
     * @return
     * @throws SQLException
     * @throws JsonMappingException
     * @throws JsonProcessingException
     */
    @Override
    public FileType getFileTypeById(int id) throws SQLException, JsonMappingException, JsonProcessingException {
        FileType fileType = FileTypeFactory.getInstance().createFileType();

        String query = QueryConstants.FILE_DEFINITION_SELECT_QUERY;
        PreparedStatement preparedStatment = DaoUtility.createPrepareStatement(connection, query, false, id);
        ResultSet rs = preparedStatment.executeQuery();
        if (rs.next() == false) {
            throw new SQLException(LiteralConstants.NO_FILE_DEFINITION_FOR_ID + id);
        } else {
            fileType.setFileTypeId(rs.getInt(LiteralConstants.FILE_DEFINITION_ID));
            fileType.setFileTypeName(rs.getString(LiteralConstants.FILE_DEFINITION_NAME));
            String columnDetails = rs.getString(LiteralConstants.FILE_DEFINITION_DETAILS);
            ColumnDetails[] colDetailsArray = objectMapper.readValue(columnDetails, ColumnDetails[].class);
            fileType.setColumnDetails(Arrays.asList(colDetailsArray));
        }
        return fileType;
    }

    /**
     * delete file definition and all records related to it
     *
     * @param fileDefinitionId
     * @return
     * @throws SQLException
     * @throws JsonProcessingException
     */
    public boolean deleteFileDefinition(int fileDefinitionId) throws SQLException, JsonProcessingException {
        FileType fileType = getFileTypeById(fileDefinitionId);
        String fileTypeName = fileType.getFileTypeName();
        String deleteQuery = QueryConstants.FILE_DEFINITION_DELETE_QUERY;
        String deleteTable = String.format(QueryConstants.DROP_QUERY, fileTypeName);
        PreparedStatement preparedStatement = DaoUtility.createPrepareStatement(connection, deleteQuery, false, fileDefinitionId);
        preparedStatement.executeUpdate();
        preparedStatement = DaoUtility.createPrepareStatement(connection, deleteTable, false);
        preparedStatement.executeUpdate();
        return true;
    }


    /**
     * update a file definition
     *
     * @param fileTypeDef
     * @return int
     * @throws SQLException
     */
    @Override
    public int updateFileDefinition(FileType fileTypeDef) throws SQLException {
        String listString = fileTypeDef.getColumnDetails().stream().map(Object::toString)
                .collect(Collectors.joining(", "));
        String fileDefinitionColumnDetails = "[" + listString + "]";
        String updateQuery = QueryConstants.FILE_DEFINITION_UPDATE_QUERY;
        PreparedStatement preparedStatement = DaoUtility.createPrepareStatement(connection, updateQuery, false, fileTypeDef.getFileTypeName(), fileDefinitionColumnDetails, fileTypeDef.getFileTypeId());
        return preparedStatement.executeUpdate();

    }


    /**
     * updation of table for inline editing
     *
     * @param data
     * @return int
     * @throws SQLException
     */
    @Override
    public int editInlineData(Map<String, Object> data) throws SQLException {


        int id = (int) data.get("id");
        String tableName = (String) data.get("tableName");

        Map<String, Object> mapFields = (Map<String, Object>) data.get("fields");
        String updateInlineDataQuery = QueryConstants.UPDATE_QUERY_TEXT + tableName + LiteralConstants.SET_TEXT;


        for (Map.Entry<String, Object> entry : mapFields.entrySet()) {

            if (entry.getValue().getClass().getName().equals(LiteralConstants.STRING_CLASS_NAME)) {

                updateInlineDataQuery += entry.getKey() + " = '" + entry.getValue().toString() + "' ,";
            } else {

                updateInlineDataQuery += entry.getKey() + " = " + entry.getValue().toString() + ",";
            }

        }
        updateInlineDataQuery = updateInlineDataQuery.substring(0, updateInlineDataQuery.length() - 1);

        updateInlineDataQuery += " where id = " + id;
        System.out.println(updateInlineDataQuery);


        PreparedStatement preparedStatement = DaoUtility.createPrepareStatement(connection, updateInlineDataQuery, false);
        return preparedStatement.executeUpdate();

    }


}