package app.data_ingestion.dataLayer.models;

public class ValidationRule {

    String operator;
    String rhsValue;

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator.toUpperCase();
    }

    public String getRhsValue() {
        return rhsValue;
    }

    public void setRhsValue(String rhsValue) {
        this.rhsValue = rhsValue.toUpperCase();
    }

    @Override
    public String toString() {
        return "{\"operator\":\"" + operator + "\", \"rhsValue\":\"" + rhsValue + "\"}";
    }

}