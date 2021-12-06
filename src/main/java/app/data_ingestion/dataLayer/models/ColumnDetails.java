package app.data_ingestion.dataLayer.models;

import java.util.List;

public class ColumnDetails {

    String columnName;
    String dataType;
    List<ValidationRule> rules;

    @Override
    public String toString() {
        return "{\"columnName\":\"" + columnName + "\", \"dataType\":\"" + dataType + "\", \"rules\":" + rules + "}";
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public List<ValidationRule> getRules() {
        return rules;
    }

    public void setRules(List<ValidationRule> rules) {
        this.rules = rules;
    }

}
