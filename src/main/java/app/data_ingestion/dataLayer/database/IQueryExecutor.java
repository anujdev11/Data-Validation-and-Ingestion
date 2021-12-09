package app.data_ingestion.dataLayer.database;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface IQueryExecutor {

    /**
     * @param query
     */
    public void execute(String query);

    /**
     * @param headers
     * @param table_name
     * @param rows
     * @param map_column_to_datatype
     * @throws SQLException
     */
    //for batch insertion
    public void insertRecords(List<String> headers, String table_name, List<List<String>> rows,
                              Map<String, String> map_column_to_datatype) throws SQLException;

    /**
     * @param query
     * @return
     */
    //for fetching records- select statment
    public List<Map<String, Object>> fetchRecords(String query);

}
