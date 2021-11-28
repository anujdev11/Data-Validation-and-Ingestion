package app.data_ingestion.data_layer.daoImpl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DaoUtility {

    /**
     * @param sql
     * @param params
     * @return
     * @throws SQLException
     */
    public static PreparedStatement createPrepareStatement(Connection connection, String sql, Object... params)
            throws SQLException {
        PreparedStatement prepared_statment = connection.prepareStatement(sql);
        int counter = 1;
        for (Object param : params) {
            System.out.println("----param.getClass().getName()---- " + param.getClass().getName());
            switch (param.getClass().getName()) {
                case "java.lang.String":
                    prepared_statment.setString(counter, (String) param);
                    break;
                case "java.lang.Integer":
                    prepared_statment.setInt(counter, (int) param);
                    break;
                case "java.lang.Float":
                    prepared_statment.setFloat(counter, (float) param);
                    break;
                case "java.lang.Date":
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
