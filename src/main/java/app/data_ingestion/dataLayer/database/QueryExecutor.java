package app.data_ingestion.dataLayer.database;

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

import app.data_ingestion.helpers.LiteralConstants;
import app.data_ingestion.helpers.QueryConstants;

@Repository
public class QueryExecutor extends JdbcDaoSupport implements IQueryExecutor {

    static Connection connection;
    final DataSource dataSource;
    final JdbcTemplate jdbcTemplate;

    public QueryExecutor(JdbcTemplate jdbcTemplate, DataSource dataSource) {
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
     * execute sql queries
     * @param query
     */
    @Override
    public void execute(String query) {
        jdbcTemplate.execute(query);
    }

    /**
     * iterate over rows and headers
     * insert data into the specified table
     * @param headers
     * @param tableName
     * @param rows
     * @param mapColumnToDatatype
     * @throws SQLException
     */
    @Override
    public void insertRecords(List<String> headers, String tableName, List<List<String>> rows,
                              Map<String, String> mapColumnToDatatype) throws SQLException {

        connection.setAutoCommit(false);
        String colsPlaceholder = "?,".repeat(headers.size());
        colsPlaceholder = colsPlaceholder.substring(0, colsPlaceholder.length() - 1);
        String query = String.format(QueryConstants.INSERT_QUERY, tableName, String.join(",", headers), colsPlaceholder);
        PreparedStatement statement = connection.prepareStatement(query);

        int rowCounter = 0;
        for (List<String> row : rows) {
            int cellCounter = 1;
            for (String value : row) {
                switch (mapColumnToDatatype.get(headers.get(cellCounter - 1))) {
                    case LiteralConstants.STRING:
                        statement.setString(cellCounter, value);
                        break;
                    case LiteralConstants.INTEGER:
                        statement.setInt(cellCounter, Integer.valueOf(value));
                        break;
                    case LiteralConstants.FLOAT:
                        statement.setFloat(cellCounter, Float.valueOf(value));
                        break;
                    case LiteralConstants.DATE:
                        statement.setDate(cellCounter, Date.valueOf(value));
                        break;
                    default:
                        statement.setString(cellCounter, value);
                        break;
                }
                ++cellCounter;
            }
            ++rowCounter;
            statement.addBatch();
            if (rowCounter % 500 == 0 || rowCounter == rows.size()) {
                int[] count = statement.executeBatch();
            }
        }
        connection.commit();
        connection.setAutoCommit(true);

    }

    /**
     * execute select query and return records
     * @param query
     * @return
     */
    @Override
    public List<Map<String, Object>> fetchRecords(String query) {
        List<Map<String, Object>> dataRecords = jdbcTemplate.queryForList(query);
        return dataRecords;
    }


}
