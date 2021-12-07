package app.data_ingestion.services.fileDefinition;

import java.sql.SQLException;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.data_ingestion.dataLayer.dao.IFileTypeDao;
import app.data_ingestion.dataLayer.models.FileType;
import app.data_ingestion.services.userAuthAndRegister.UserServiceStatus;

@Service
public class FileDefinition implements IFileDefinitionService {

    @Autowired
    IFileTypeDao fileTypeDao;

    /**
     * @param fileDef
     * @return
     */
    @Override
    public UserServiceStatus fileDefinition(FileType fileDef) {
        try {
            if(fileTypeDao.addFileDefinition(fileDef) > 0){
                return UserServiceStatus.SUCCESS;
            }else{
                return UserServiceStatus.FAILURE;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return UserServiceStatus.FAILURE;
    }

    /**
     * @param fileDefinitionId
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

    
    /** 
     * @param fileType
     * @return UserServiceStatus
     */
    @Override
    public UserServiceStatus updateFileDefinition(FileType fileType) {
        try {
           if (fileTypeDao.updateFileDefinition(fileType) > 0){
               return UserServiceStatus.SUCCESS;
           }else {
              return UserServiceStatus.FAILURE;

           }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return UserServiceStatus.FAILURE;
    }

}
