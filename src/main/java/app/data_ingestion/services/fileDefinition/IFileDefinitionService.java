package app.data_ingestion.services.fileDefinition;

import app.data_ingestion.dataLayer.models.FileType;
import app.data_ingestion.services.userService.UserServiceStatus;

import com.fasterxml.jackson.core.JsonProcessingException;

import java.sql.SQLException;

public interface IFileDefinitionService {

    /**
     * @param fileDef
     * @return
     */
    public UserServiceStatus fileDefinition(FileType fileDef);

    /**
     * @param fileDefinitionId
     * @return
     * @throws SQLException
     * @throws JsonProcessingException
     */
    public boolean deleteFileDefinition(int fileDefinitionId) throws SQLException, JsonProcessingException;

    /**
     * @param fileType
     * @return
     */
    public UserServiceStatus updateFileDefinition(FileType fileType);

}
