package app.data_ingestion.data_layer.models;

import java.util.List;

public class ColumnDetails {

    String column_name;
    String data_type;
    List<ValidationRule> rules;

    public String getColumn_name() {
        return column_name;
    }

    public void setColumn_name(String column_name) {
        this.column_name = column_name;
    }

    public String getData_type() {
        return data_type;
    }

    public void setData_type(String data_type) {
        this.data_type = data_type;
    }

    public List<ValidationRule> getRules() {
        return rules;
    }
    
    public void setRules(List<ValidationRule> rules) {
        this.rules = rules;
    }

	@Override
	public String toString() {
		return "ColumnDetails [column_name=" + column_name + ", data_type=" + data_type + ", rules=" + rules + "]";
	}

	public ColumnDetails(String column_name, String data_type, List<ValidationRule> rules) {
		super();
		this.column_name = column_name;
		this.data_type = data_type;
		this.rules = rules;
	}

	
	
	
	
	
    
    
    
}
