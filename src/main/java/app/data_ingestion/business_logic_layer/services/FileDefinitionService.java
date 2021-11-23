package app.data_ingestion.business_logic_layer.services;

import app.data_ingestion.business_logic_layer.servicesImpl.UserServiceStatus;
import app.data_ingestion.data_layer.models.FileType;

public interface FileDefinitionService {

	/**
	 * @param fileDef
	 * @return
	 */
	public UserServiceStatus fileDefinition(FileType fileDef);

}
