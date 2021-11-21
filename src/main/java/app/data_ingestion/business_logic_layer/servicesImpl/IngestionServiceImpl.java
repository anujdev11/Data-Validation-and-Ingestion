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
import app.data_ingestion.data_layer.dao.FileTypeDao;
import app.data_ingestion.data_layer.database.QueryExecutor;
import app.data_ingestion.data_layer.models.FileType;
import app.data_ingestion.helpers.CustomExceptions;

@Service
public class IngestionServiceImpl implements IngestionService {

    @Autowired
    FileTypeDao fileTypeDao;

    @Autowired
    QueryExecutor queryExecutor;
    
    List<String> headers;
    List<List<String>> rows; 
    FileType fileType;
    Map<String,String> map_column_to_datatype;

    public void ingestData(int id, MultipartFile file, String delimiter) throws Exception {

        fileType = fileTypeDao.getFileTypeById(id);
        map_column_to_datatype = fileType.getColumn_to_datatype();
        String contents = retrieveFileContentsAsString(file);

        if(contents != null && !contents.trim().isEmpty()){
            populateHeadersAndRows(contents, delimiter);

            //validate table headers
            validateFileHeaders(headers);

            //insert data
            insertRecodsToDatabase();

        }
        else throw new CustomExceptions.EmptyFileException("Data Ingestion cannot be performed on an empty file. Please try again.");

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
        queryExecutor.insertRecords(headers, fileType.getFileTypeName(), rows, map_column_to_datatype);
    }

    @Override
    public void populateHeadersAndRows(String file_content, String delimiter) {
        headers = new ArrayList<>();
        rows = new ArrayList<>();
        
        List<String> lines = Arrays.asList( file_content.split(System.lineSeparator()) );
        headers = Arrays.asList( lines.get(0).split(delimiter) );

        lines.remove(0);
        for(String line : lines){
            List<String> row = Arrays.asList(line.split(delimiter));
            rows.add(row);
        }
        
    }
    
}
