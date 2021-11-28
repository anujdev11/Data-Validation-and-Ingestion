package app.data_ingestion.data_layer.models;

public class ValidationRule {

	String operator;
	String rhsValue;

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String getRhsValue() {
		return rhsValue;
	}

	public void setRhsValue(String rhsValue) {
		this.rhsValue = rhsValue;
	}

	@Override
	public String toString() {
		return "{operator=" + operator + ", rhsValue=" + rhsValue + "}";
	}

}
