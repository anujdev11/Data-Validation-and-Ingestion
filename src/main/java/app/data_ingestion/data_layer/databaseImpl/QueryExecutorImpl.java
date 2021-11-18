package app.data_ingestion.data_layer.databaseImpl;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import app.data_ingestion.data_layer.database.DatabaseConnection;
import app.data_ingestion.data_layer.database.QueryExecutor;

@Repository
public class QueryExecutorImpl extends JdbcDaoSupport implements QueryExecutor {

    static Connection connection;
    final DataSource dataSource;
    final JdbcTemplate jdbcTemplate;

    public QueryExecutorImpl(JdbcTemplate jdbcTemplate, DataSource dataSource){
        this.jdbcTemplate = jdbcTemplate;
        this.dataSource = dataSource;
        try {
            connection = DatabaseConnection.getConnection(this.jdbcTemplate);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @PostConstruct
    private void initialize() {
        setDataSource(dataSource);
    }

    @Override
    public void createTable(String query) {
    }

    @Override
    public void executeQuery(String query) {
        
    }

    @Override
    public void insertRecords(String query, List<Map<String, Object>> data_records, Map<String, String> map_column_to_datatype) {

        /*jdbc_template.batchUpdate(query, new BatchPreparedStatementSetter() {

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
        });*/
    }

    @Override
    public List<Map<String, Object>> fetchRecords(String query) {
        List<Map<String, Object>> data_records = jdbcTemplate.queryForList(query);
        return data_records;
    }
    
}
