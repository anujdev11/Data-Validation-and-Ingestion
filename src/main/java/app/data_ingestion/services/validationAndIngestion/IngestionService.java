package app.data_ingestion.services.validationAndIngestion;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import app.data_ingestion.dataLayer.dao.IFileTypeDao;
import app.data_ingestion.dataLayer.database.IQueryExecutor;
import app.data_ingestion.dataLayer.models.FileType;
import app.data_ingestion.helpers.ValidationRules;

@Service
public class IngestionService implements IIngestionService {

    @Autowired
    IFileTypeDao fileTypeDao;

    @Autowired
    IQueryExecutor queryExecutor;

    List<String> headers;
    List<List<String>> rows;
    List<List<String>> invalidRows;
    List<List<String>> validRows;
    FileType fileType;
    Map<String, String> mapColumnToDatatype;


    /**
     * @param id
     * @param file
     * @param delimiter
     * @param action
     * @throws Exception
     */
    public void ingestData(int id, MultipartFile file, String delimiter, String action) throws Exception {

        StateRunner stateRunner = StateRunnerFactory.getInstance().createStateRunner();
        stateRunner.run(this, id, file, delimiter, action);

    }


    /**
     * @return List<List < String>>
     */
    public List<List<String>> getInvalidRows() {
        return invalidRows;
    }


    /**
     * @return List<List < String>>
     */
    public List<List<String>> getValidRows() {
        return validRows;
    }


    /**
     * @return IFileTypeDao
     */
    public IFileTypeDao getFileTypeDao() {
        return fileTypeDao;
    }


    /**
     * @param fileTypeDao
     */
    public void setFileTypeDao(IFileTypeDao fileTypeDao) {
        this.fileTypeDao = fileTypeDao;
    }


    /**
     * @return IQueryExecutor
     */
    public IQueryExecutor getQueryExecutor() {
        return queryExecutor;
    }


    /**
     * @return List<String>
     */
    public List<String> getHeaders() {
        return headers;
    }


    /**
     * @param headers
     */
    public void setHeaders(List<String> headers) {
        this.headers = headers;
    }


    /**
     * @return List<List < String>>
     */
    public List<List<String>> getRows() {
        return rows;
    }


    /**
     * @param rows
     */
    public void setRows(List<List<String>> rows) {
        this.rows = rows;
    }


    /**
     * @param invalidRows
     */
    public void setInvalidRows(List<List<String>> invalidRows) {
        this.invalidRows = invalidRows;
    }


    /**
     * @param validRows
     */
    public void setValidRows(List<List<String>> validRows) {
        this.validRows = validRows;
    }


    /**
     * @return FileType
     */
    public FileType getFileType() {
        return fileType;
    }


    /**
     * @param fileType
     */
    public void setFileType(FileType fileType) {
        this.fileType = fileType;
    }


    /**
     * @return Map<String, String>
     */
    public Map<String, String> getMapColumnToDatatype() {
        return mapColumnToDatatype;
    }


    /**
     * @param mapColumnToDatatype
     */
    public void setMapColumnToDatatype(Map<String, String> mapColumnToDatatype) {
        this.mapColumnToDatatype = mapColumnToDatatype;
    }


    /**
     * @param ruleDataType
     * @return String[]
     */
    public static String[] getValidationRule(String ruleDataType) {
        return ValidationRules.getValidationRule(ruleDataType);
    }


}
