package app.data_ingestion.dataLayer.databaseImpl;

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

import app.data_ingestion.dataLayer.database.DatabaseConnection;
import app.data_ingestion.dataLayer.database.QueryExecutor;
import app.data_ingestion.helpers.LiteralConstants;
import app.data_ingestion.helpers.QueryConstants;

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

    /**
     * @param query
     */
    @Override
    public void execute(String query) {
        jdbcTemplate.execute(query);
    }

    /**
     * @param headers
     * @param table_name
     * @param rows
     * @param map_column_to_datatype
     * @throws SQLException
     */
    @Override
    public void insertRecords(List<String> headers, String table_name, List<List<String>> rows,
                              Map<String, String> map_column_to_datatype) throws SQLException {

        connection.setAutoCommit(false);
        String cols_placeholder = "?,".repeat(headers.size());
        cols_placeholder = cols_placeholder.substring(0, cols_placeholder.length() - 1);
        String query = String.format(QueryConstants.INSERT_QUERY, table_name, String.join(",", headers), cols_placeholder);
        PreparedStatement statement = connection.prepareStatement(query);

        int row_counter = 0;
        for (List<String> row : rows) {
            int cell_counter = 1;
            for (String value : row) {
                switch (map_column_to_datatype.get(headers.get(cell_counter - 1))) {
                    case LiteralConstants.STRING:
                        statement.setString(cell_counter, value);
                        break;
                    case LiteralConstants.INTEGER:
                        statement.setInt(cell_counter, Integer.valueOf(value));
                        break;
                    case LiteralConstants.FLOAT:
                        statement.setFloat(cell_counter, Float.valueOf(value));
                        break;
                    case LiteralConstants.DATE:
                        statement.setDate(cell_counter, Date.valueOf(value));
                        break;
                    default:
                        statement.setString(cell_counter, value);
                        break;
                }
                ++cell_counter;
            }
            ++row_counter;
            statement.addBatch();
            if (row_counter % 500 == 0 || row_counter == rows.size()) {
                int[] count = statement.executeBatch();
            }
        }
        connection.commit();
        connection.setAutoCommit(true);

    }

    /**
     * @param query
     * @return
     */
    @Override
    public List<Map<String, Object>> fetchRecords(String query) {
        List<Map<String, Object>> data_records = jdbcTemplate.queryForList(query);
        return data_records;
    }


}
