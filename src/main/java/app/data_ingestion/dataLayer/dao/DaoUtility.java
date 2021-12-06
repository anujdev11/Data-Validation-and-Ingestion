package app.data_ingestion.dataLayer.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import app.data_ingestion.helpers.LiteralConstants;

public class DaoUtility {

    /**
     * @param sql
     * @param params
     * @return
     * @throws SQLException
     */
    public static PreparedStatement createPrepareStatement(Connection connection, String sql, Object... params) throws SQLException {
        PreparedStatement prepared_statment = connection.prepareStatement(sql);
        int counter = 1;
        for (Object param : params) {
            System.out.println("----param.getClass().getName()---- " + param.getClass().getName());
            switch (param.getClass().getName()) {
                case LiteralConstants.STRING_CLASS_NAME:
                    prepared_statment.setString(counter, (String) param);
                    break;
                case LiteralConstants.INTEGER_CLASS_NAME:
                    prepared_statment.setInt(counter, (int) param);
                    break;
                case LiteralConstants.FLOAT_CLASS_NAME:
                    prepared_statment.setFloat(counter, (float) param);
                    break;
                case LiteralConstants.DATE_CLASS_NAME:
                    prepared_statment.setDate(counter, (Date) param);
                    break;
                default:
                    prepared_statment.setString(counter, String.valueOf(param));
                    break;
            }
            counter++;
        }
        return prepared_statment;
    }
}
