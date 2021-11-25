package app.data_ingestion.data_layer.models;

public class ValidationRule {
    
    String operator;
    String rhs_value;
    
    public String getOperator() {
        return operator;
    }
    public void setOperator(String operator) {
        this.operator = operator.toUpperCase();
    }
    @Override
    public String toString() {
        return "ValidationRule [operator=" + operator + ", rhs_value=" + rhs_value + "]";
    }
    public String getRhs_value() {
        return rhs_value;
    }
    public void setRhs_value(String rhs_value) {
        this.rhs_value = rhs_value.toUpperCase();
    }
    
}
