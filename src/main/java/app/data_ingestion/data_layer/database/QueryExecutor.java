package app.data_ingestion.data_layer.database;

import java.util.List;
import java.util.Map;

public interface QueryExecutor {
    
    //for create table
    public void create_table(String query);

    //for single insert, update and delete query
    public void execute_query(String query);

    //for batch insertion
    public void insert_records(String query, List<Map<String, Object>> data_records, Map<String, String> map_column_to_datatype);

    //for fetching records- select statment
    public List<Map<String, Object>> fetch_records(String query);

}
