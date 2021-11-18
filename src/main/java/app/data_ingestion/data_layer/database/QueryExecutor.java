package app.data_ingestion.data_layer.database;

import java.util.List;
import java.util.Map;

public interface QueryExecutor {
    
    //for create table
    public void createTable(String query);

    //for single insert, update and delete query
    public void executeQuery(String query);

    //for batch insertion
    public void insertRecords(String query, List<Map<String, Object>> data_records, Map<String, String> map_column_to_datatype);

    //for fetching records- select statment
    public List<Map<String, Object>> fetchRecords(String query);

}
