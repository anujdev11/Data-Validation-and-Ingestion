package app.data_ingestion.data_layer.databaseImpl;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
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
    public void insertRecords(List<String> headers, String table_name, List<List<String>> rows, 
                Map<String, String> map_column_to_datatype) throws SQLException {

        String cols_placeholder = "?".repeat(headers.size());
        String query = String.format("insert into %s (%s) values (%s)", table_name, String.join(",", headers), cols_placeholder);
        PreparedStatement statement = connection.prepareStatement(query);

        int row_counter = 0;
        for(List<String> row : rows){
            int cell_counter = 0;
            for(String value : row){
                switch (headers.get(row_counter)) {
                    case "String":
                        statement.setString(cell_counter, value);
                        break;
                    case "Integer":
                        statement.setInt(cell_counter, Integer.valueOf(value));
                        break;
                    case "Float":
                        statement.setFloat(cell_counter, Float.valueOf(value));
                        break;
                    case "Date":
                        statement.setDate(cell_counter, Date.valueOf(value));
                        break;
                    default:
                        statement.setString(cell_counter, value);
                        break;
                }
                cell_counter++;
            }
            row_counter++;

            statement.addBatch();
            if (row_counter % 500 == 0 || row_counter == rows.size()) {
                statement.executeBatch();
            }

        }
           
    }

    @Override
    public List<Map<String, Object>> fetchRecords(String query) {
        List<Map<String, Object>> data_records = jdbcTemplate.queryForList(query);
        return data_records;
    }
    
}
