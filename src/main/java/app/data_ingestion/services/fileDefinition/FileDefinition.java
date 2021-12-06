package app.data_ingestion.services.fileDefinition;

import java.sql.SQLException;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.data_ingestion.dataLayer.dao.FileTypeDao;
import app.data_ingestion.dataLayer.models.FileType;
import app.data_ingestion.services.user_service.UserServiceStatus;

@Service
public class FileDefinition implements IFileDefinitionService {

    @Autowired
    FileTypeDao fileTypeDao;

    /**
     * @param fileDef
     * @return
     */
    @Override
    public UserServiceStatus fileDefinition(FileType fileDef) {
        try {
            return fileTypeDao.addFileDefinition(fileDef) > 0 ? UserServiceStatus.SUCCESS : UserServiceStatus.FAILURE;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return UserServiceStatus.FAILURE;
    }

    /**
     * @param file_definition_id
     * @return
     * @throws SQLException
     * @throws JsonProcessingException
     */
    @Override
    public boolean deleteFileDefinition(int fileDefinitionId) throws SQLException, JsonProcessingException {
        boolean status = fileTypeDao.deleteFileDefinition(fileDefinitionId);
        if (status) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public UserServiceStatus updateFileDefinition(FileType fileType) {
        try {
            return fileTypeDao.updateFileDefinition(fileType) > 0 ? UserServiceStatus.SUCCESS
                    : UserServiceStatus.FAILURE;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return UserServiceStatus.FAILURE;
    }

}
