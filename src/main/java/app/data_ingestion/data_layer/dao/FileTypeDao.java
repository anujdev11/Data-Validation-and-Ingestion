package app.data_ingestion.data_layer.dao;

import java.sql.SQLException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import app.data_ingestion.data_layer.models.FileType;

public interface FileTypeDao {
    
    public FileType getFileTypeById(int fileTypeId) throws SQLException, JsonMappingException, JsonProcessingException;

}
