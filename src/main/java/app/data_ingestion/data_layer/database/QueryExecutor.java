package app.data_ingestion.data_layer.database;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface QueryExecutor {
    
    //for create table
    public void createTable(String query);

    //for single insert, update and delete query
    public void executeQuery(String query);

    //for batch insertion
    public void insertRecords(List<String> headers, String table_name, List<List<String>> rows, 
                Map<String, String> map_column_to_datatype) throws SQLException;

    //for fetching records- select statment
    public List<Map<String, Object>> fetchRecords(String query);

}
