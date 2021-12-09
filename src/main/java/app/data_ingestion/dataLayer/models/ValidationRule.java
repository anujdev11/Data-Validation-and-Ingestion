package app.data_ingestion.dataLayer.models;

public class ValidationRule {

    String operator;
    String rhsValue;


    /**
     * @return String
     */
    public String getOperator() {
        return operator;
    }


    /**
     * @param operator
     */
    public void setOperator(String operator) {
        this.operator = operator.toUpperCase();
    }


    /**
     * @return String
     */
    public String getRhsValue() {
        return rhsValue;
    }


    /**
     * @param rhsValue
     */
    public void setRhsValue(String rhsValue) {
        this.rhsValue = rhsValue.toUpperCase();
    }


    /**
     * @return String
     */
    @Override
    public String toString() {
        return "{\"operator\":\"" + operator + "\", \"rhsValue\":\"" + rhsValue + "\"}";
    }

}
