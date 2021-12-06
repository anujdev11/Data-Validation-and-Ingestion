package app.data_ingestion.dataLayer.daoImpl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import app.data_ingestion.helpers.LiteralConstants;

public class DaoUtility {

    /**
     * @param sql
     * @param params
     * @return
     * @throws SQLException
     */
    public static PreparedStatement createPrepareStatement(Connection connection, String sql,
            Boolean returnGeneratedKeys, Object... params)
            throws SQLException {
        PreparedStatement preparedStatement = null;
        if (Boolean.TRUE.equals(returnGeneratedKeys)) {
            preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        } else {
            preparedStatement = connection.prepareStatement(sql);
        }
        int counter = 1;
        for (Object param : params) {
            switch (param.getClass().getName()) {
                case LiteralConstants.STRING_CLASS_NAME:
                    preparedStatement.setString(counter, (String) param);
                    break;
                case LiteralConstants.INTEGER_CLASS_NAME:
                    preparedStatement.setInt(counter, (int) param);
                    break;
                case LiteralConstants.FLOAT_CLASS_NAME:
                    preparedStatement.setFloat(counter, (float) param);
                    break;
                case LiteralConstants.DATE_CLASS_NAME:
                    preparedStatement.setDate(counter, (Date) param);
                    break;
                default:
                    preparedStatement.setString(counter, String.valueOf(param));
                    break;
            }
            counter++;
        }
        return preparedStatement;
    }
}
