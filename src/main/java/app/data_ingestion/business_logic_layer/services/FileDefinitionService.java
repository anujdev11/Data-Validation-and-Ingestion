package app.data_ingestion.business_logic_layer.services;

import app.data_ingestion.business_logic_layer.servicesImpl.UserServiceStatus;
import app.data_ingestion.data_layer.models.FileType;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.sql.SQLException;

public interface FileDefinitionService {

	/**
	 * @param fileDef
	 * @return
	 */
	public UserServiceStatus fileDefinition(FileType fileDef);

	public boolean deleteFileDefinition(int file_definition_id) throws SQLException, JsonProcessingException;

}
