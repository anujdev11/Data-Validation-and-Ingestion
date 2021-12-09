package app.data_ingestion.dataLayer.models;

import java.util.List;

public class ColumnDetails {

    String columnName;
    String dataType;
    List<ValidationRule> rules;


    /**
     * @return String
     */
    @Override
    public String toString() {
        return "{\"columnName\":\"" + columnName + "\", \"dataType\":\"" + dataType + "\", \"rules\":" + rules + "}";
    }


    /**
     * @return String
     */
    public String getColumnName() {
        return columnName;
    }


    /**
     * @param columnName
     */
    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }


    /**
     * @return String
     */
    public String getDataType() {
        return dataType;
    }


    /**
     * @param dataType
     */
    public void setDataType(String dataType) {
        this.dataType = dataType;
    }


    /**
     * @return List<ValidationRule>
     */
    public List<ValidationRule> getRules() {
        return rules;
    }


    /**
     * @param rules
     */
    public void setRules(List<ValidationRule> rules) {
        this.rules = rules;
    }

}
