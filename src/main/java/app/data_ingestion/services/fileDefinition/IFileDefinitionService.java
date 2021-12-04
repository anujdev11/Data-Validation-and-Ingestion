package app.data_ingestion.services.fileDefinition;

import app.data_ingestion.dataLayer.models.FileType;
import app.data_ingestion.services.userAuthAndRegister.UserServiceStatus;

import com.fasterxml.jackson.core.JsonProcessingException;

import java.sql.SQLException;

public interface IFileDefinitionService {

    /**
     * @param fileDef
     * @return
     */
    public UserServiceStatus fileDefinition(FileType fileDef);

    /**
     * @param file_definition_id
     * @return
     * @throws SQLException
     * @throws JsonProcessingException
     */
    public boolean deleteFileDefinition(int file_definition_id) throws SQLException, JsonProcessingException;
    
    /**
	 * @param fileDef
	 * @return
	 */
	public UserServiceStatus updateFileDefinition(FileType FileDef);

}
