package app.data_ingestion.business_logic_layer.servicesImpl;

import java.sql.SQLException;

import com.fasterxml.jackson.core.JsonProcessingException;
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

    @Override
    public boolean deleteFileDefinition(int file_definition_id) throws SQLException, JsonProcessingException {
        boolean status =  FileTypeDao.deleteFileDefinition(file_definition_id);
        return true;
    }

}
