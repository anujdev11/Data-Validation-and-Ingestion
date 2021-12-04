package app.data_ingestion.services.validationAndIngestion;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import app.data_ingestion.dataLayer.dao.FileTypeDao;
import app.data_ingestion.dataLayer.database.QueryExecutor;
import app.data_ingestion.dataLayer.models.FileType;
import app.data_ingestion.helpers.ValidationRules;

@Service
public class IngestionService implements IIngestionService {

    @Autowired
    FileTypeDao fileTypeDao;

    @Autowired
    QueryExecutor queryExecutor;

    List<String> headers;
    List<List<String>> rows;
    List<List<String>> invalidRows;
    List<List<String>> validRows;
    FileType fileType;
    Map<String, String> mapColumnToDatatype;

    public void ingestData(int id, MultipartFile file, String delimiter, String action) throws Exception {

        StateRunner stateRunner = StateRunnerFactory.getInstance().createStateRunner();
        stateRunner.run(this, id, file, delimiter, action);
    
    } 

    public List<List<String>> getInvalidRows() {
        return invalidRows;
    }

    public List<List<String>> getValidRows() {
        return validRows;
    }

    public FileTypeDao getFileTypeDao() {
        return fileTypeDao;
    }

    public void setFileTypeDao(FileTypeDao fileTypeDao) {
        this.fileTypeDao = fileTypeDao;
    }

    public QueryExecutor getQueryExecutor() {
        return queryExecutor;
    }

    public List<String> getHeaders() {
        return headers;
    }

    public void setHeaders(List<String> headers) {
        this.headers = headers;
    }

    public List<List<String>> getRows() {
        return rows;
    }

    public void setRows(List<List<String>> rows) {
        this.rows = rows;
    }

    public void setInvalidRows(List<List<String>> invalidRows) {
        this.invalidRows = invalidRows;
    }

    public void setValidRows(List<List<String>> validRows) {
        this.validRows = validRows;
    }

    public FileType getFileType() {
        return fileType;
    }

    public void setFileType(FileType fileType) {
        this.fileType = fileType;
    }

    public Map<String, String> getMapColumnToDatatype() {
        return mapColumnToDatatype;
    }

    public void setMapColumnToDatatype(Map<String, String> mapColumnToDatatype) {
        this.mapColumnToDatatype = mapColumnToDatatype;
    }

    public static String[] getValidationRule(String ruleDataType) {
        return ValidationRules.getValidationRule(ruleDataType);
    }


}
