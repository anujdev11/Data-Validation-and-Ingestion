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

    public QueryExecutorImpl(JdbcTemplate jdbcTemplate, DataSource dataSource) {
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
        System.out.println("---createTable-- " + query);
        jdbcTemplate.execute(query);
    }

    @Override
    public void executeQuery(String query) {

    }

    @Override
    public void insertRecords(List<String> headers, String table_name, List<List<String>> rows,
            Map<String, String> map_column_to_datatype) throws SQLException {

        System.out.println("---headers---- " + headers);
        System.out.println("---table_name---- " + table_name);
        System.out.println("---rows---- " + rows);
        System.out.println("---map_column_to_datatype---- " + map_column_to_datatype);

        connection.setAutoCommit(false);
        String cols_placeholder = "?,".repeat(headers.size());
        cols_placeholder = cols_placeholder.substring(0, cols_placeholder.length() - 1);
        String query = String.format("insert into %s (%s) values (%s)", table_name, String.join(",", headers),
                cols_placeholder);
        System.out.println("---query---- " + query);
        PreparedStatement statement = connection.prepareStatement(query);

        int row_counter = 0;
        for (List<String> row : rows) {
            int cell_counter = 1;
            for (String value : row) {
                System.out.println("---header name---- " + map_column_to_datatype.get(headers.get(cell_counter - 1)));
                switch (map_column_to_datatype.get(headers.get(cell_counter - 1))) {
                    case "STRING":
                        statement.setString(cell_counter, value);
                        break;
                    case "INTEGER":
                        statement.setInt(cell_counter, Integer.valueOf(value));
                        break;
                    case "FLOAT":
                        statement.setFloat(cell_counter, Float.valueOf(value));
                        break;
                    case "DATE":
                        statement.setDate(cell_counter, Date.valueOf(value));
                        break;
                    default:
                        statement.setString(cell_counter, value);
                        break;
                }
                ++cell_counter;
            }
            ++row_counter;
            System.out.println("----row_counter---- " + row_counter);

            System.out.println("----rows.size()---- " + rows.size());
            statement.addBatch();
            if (row_counter % 500 == 0 || row_counter == rows.size()) {
                System.out.println("executing batch");
                System.out.println("---statement--- " + statement);
                int[] count = statement.executeBatch();
                System.out.println("---count--- " + count.length);

            }

        }
        connection.commit();
        connection.setAutoCommit(true);

    }

    @Override
    public List<Map<String, Object>> fetchRecords(String query) {
        List<Map<String, Object>> data_records = jdbcTemplate.queryForList(query);
        return data_records;
    }

}
