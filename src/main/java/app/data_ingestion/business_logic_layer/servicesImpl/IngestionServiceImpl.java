package app.data_ingestion.business_logic_layer.servicesImpl;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import app.data_ingestion.business_logic_layer.services.IngestionService;
import app.data_ingestion.config.ConfigReader;
import app.data_ingestion.data_layer.dao.FileTypeDao;
import app.data_ingestion.data_layer.database.QueryExecutor;
import app.data_ingestion.data_layer.models.FileType;
import app.data_ingestion.data_layer.models.ValidationRule;
import app.data_ingestion.helpers.CustomExceptions;
import app.data_ingestion.helpers.Helper;

@Service
public class IngestionServiceImpl implements IngestionService {

    @Autowired
    FileTypeDao fileTypeDao;

    @Autowired
    QueryExecutor queryExecutor;
    
    List<String> headers;
    List<List<String>> rows; 
    List<List<String>> invalidRows; 
    List<List<String>> validRows; 
    FileType fileType;
    Map<String,String> map_column_to_datatype;

    public void ingestData(int id, MultipartFile file, String delimiter) throws Exception {

        String contents = retrieveFileContentsAsString(file);

        if(contents != null && !contents.trim().isEmpty()){
            System.out.println("---fileTypeDao-- "+fileTypeDao);
            fileType = fileTypeDao.getFileTypeById(id);
            fileType = fileTypeDao.getFileTypeById(id);
            map_column_to_datatype = fileType.getColumn_to_datatype();

            populateHeadersAndRows(contents, delimiter);

            //validate table headers
            validateFileHeaders(headers);

            //apply validation rules
            filterFileRecords();

            createTable();

            //
            if(!invalidRows.isEmpty()){
                String path = ConfigReader.getProperty("INVALID_ROWS_CSV_PATH");
                CSVExport.createCSV(path, headers, invalidRows);
            }
            
            //insert data
            if(!validRows.isEmpty()){
                insertRecodsToDatabase();
            }

        }
        else throw new CustomExceptions.EmptyFileException("Data Ingestion cannot be performed on an empty file. Please try again.");

    }

    private void filterFileRecords() {

        validRows = new ArrayList<>();
        invalidRows = new ArrayList<>();
        ValidationRulesServiceImpl validator = new ValidationRulesServiceImpl();

        Map<String,List<ValidationRule>> col_to_rules = fileType.getColumn_to_rules();
        for(List<String> row: rows){
            String violatedValidationRule = null;
            int counter = 0;
            for(String cellValue: row){
                String header= headers.get(counter);
                
                if(col_to_rules.containsKey(header)){
                    violatedValidationRule = validator.validate(col_to_rules.get(header), header, cellValue, map_column_to_datatype);
                    
                    System.out.println("-----violatedValidationRule-------------");
                    System.out.println(violatedValidationRule);
                    
                    if(!violatedValidationRule.isBlank()){
                        List<String> temp_list = new ArrayList<>(row);
                        temp_list.add(violatedValidationRule);
                        invalidRows.add(temp_list);
                        break;
                    }
                } 
                ++counter;
            }
            if(violatedValidationRule.isBlank()){
                validRows.add(row);
            }
        }
    }

    @Override
    public String retrieveFileContentsAsString(MultipartFile inputFile) throws IOException {
        String content = new String(inputFile.getBytes());
        System.out.println(content);
        return content;
    }

    @Override
    public void validateFileHeaders(List<String> headers) throws Exception {
        
        List<String> columns = new ArrayList<>(map_column_to_datatype.keySet());
        columns.replaceAll(String::toUpperCase);
        headers.replaceAll(String::toUpperCase);

        for(String header : headers){
            if(!columns.contains(header)){
                throw new Exception("Invalid headers. Correct headers are: "+ String.join(", ", columns));
            }
        }
    }

    @Override
    public void insertRecodsToDatabase() throws SQLException {
        queryExecutor.insertRecords(headers, fileType.getFileTypeName(), validRows, map_column_to_datatype);
    }

    @Override
    public void populateHeadersAndRows(String file_content, String delimiter) {
        headers = new ArrayList<>();
        rows = new ArrayList<>();
        System.out.println("----System.lineSeparator()---- "+file_content.split(System.lineSeparator()).length);
        String[] lines = file_content.split(System.lineSeparator());
        headers = Arrays.asList(lines[0].split(delimiter) );

        for(int row_num = 1; row_num<lines.length; row_num++){
            List<String> row = Arrays.asList(lines[row_num].split(delimiter));
            rows.add(row);
        }
        System.out.println("headers "+headers);
        System.out.println("rows "+rows);
        
        
    }

    @Override
    public void createTable() {
        String col_detail = "";

        for(String col: map_column_to_datatype.keySet()){
            String dtype = map_column_to_datatype.get(col);
            col_detail += String.format("`%s` %s,", col, Helper.getDataType(dtype));
        }
        col_detail = col_detail.substring(0, col_detail.length()-1);
        String query = String.format("create table if not exists `%s` (%s)", fileType.getFileTypeName(), col_detail);
        queryExecutor.createTable(query);
    }

    public List<List<String>> getInvalidRows() {
        return invalidRows;
    }

    public List<List<String>> getValidRows() {
        return validRows;
    }
    
    
}
