package app.data_ingestion.data_layer.databaseImpl;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import app.data_ingestion.data_layer.database.QueryExecutor;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;

public class QueryExecutorImpl implements QueryExecutor {

    private JdbcTemplate jdbc_template;

    public QueryExecutorImpl(){
        jdbc_template = new JdbcTemplate();
    }

    @Override
    public void create_table(String query) {
        jdbc_template.execute(query);
    }

    @Override
    public void execute_query(String query) {
        jdbc_template.update(query);
        
    }

    @Override
    public void insert_records(String query, List<Map<String, Object>> data_records, Map<String, String> map_column_to_datatype) {

        jdbc_template.batchUpdate(query, new BatchPreparedStatementSetter() {

            @Override
            public void setValues(PreparedStatement ps, int i)
                throws SQLException {

                Map<String, Object> data_row = data_records.get(i);
                int count = 1;
                for (Map.Entry<String,Object> data_cell : data_row.entrySet()){
                    //String column = data_cell.getKey();
                    String col_value = (String) data_cell.getValue();
                    //String data_type = map_column_to_datatype.get(column);
                    ps.setString(count, col_value);
                }
            }
    
            @Override
            public int getBatchSize() {
                return data_records.size();
            }
        });
    }

    @Override
    public List<Map<String, Object>> fetch_records(String query) {
        List<Map<String, Object>> data_records = jdbc_template.queryForList(query);
        return data_records;
    }
    
}
