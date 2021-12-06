package app.data_ingestion.dataLayer.dao;

import java.sql.SQLException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import app.data_ingestion.dataLayer.models.FileType;

public interface IFileTypeDao {

    /**
     * @param fileTypeDef
     * @return
     * @throws SQLException
     */
    public int addFileDefinition(FileType fileTypeDef) throws SQLException;

    /**
     * @param fileTypeId
     * @return
     * @throws SQLException
     * @throws JsonMappingException
     * @throws JsonProcessingException
     */
    public FileType getFileTypeById(int fileTypeId) throws SQLException, JsonMappingException, JsonProcessingException;

    /**
     * @param file_definition_id
     * @return
     * @throws SQLException
     * @throws JsonProcessingException
     */
    public boolean deleteFileDefinition(int file_definition_id) throws SQLException, JsonProcessingException;
    
    /**
     * @param fileTypeDef
     * @return
     * @throws SQLException
     */
    public int updateFileDefinition(FileType fileTypeDef) throws SQLException;


}
