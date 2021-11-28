package app.data_ingestion.business_logic_layer.servicesImpl;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.data_ingestion.business_logic_layer.services.FileDefinitionService;
import app.data_ingestion.data_layer.models.FileType;
import app.data_ingestion.data_layer.dao.FileTypeDao;

@Service
public class FileDefinitionImpl implements FileDefinitionService {

    @Autowired
    FileTypeDao FileTypeDao;

    /**
     * @param fileDef
     * @return
     */
    @Override
    public UserServiceStatus fileDefinition(FileType fileDef) {
        try {
            return FileTypeDao.addFileDefinition(fileDef) > 0 ? UserServiceStatus.SUCCESS : UserServiceStatus.FAILURE;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return UserServiceStatus.FAILURE;
    }

}
