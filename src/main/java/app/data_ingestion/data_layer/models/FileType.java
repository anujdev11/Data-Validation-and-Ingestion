package app.data_ingestion.data_layer.models;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FileType {

    int fileTypeId;
    String fileTypeName;
    List<ColumnDetails> columnDetails;
    
    
    public FileType(int fileTypeId, String fileTypeName, List<ColumnDetails> columnDetails) {
		super();
		this.fileTypeId = fileTypeId;
		this.fileTypeName = fileTypeName;
		this.columnDetails = columnDetails;
	}
    
    
	public FileType() {
		super();
	}




	public int getFileTypeId() {
        return fileTypeId;
    }


    public void setFileTypeId(int fileTypeId) {
        this.fileTypeId = fileTypeId;
    }


    public String getFileTypeName() {
        return fileTypeName;
    }


    public void setFileTypeName(String fileTypeName) {
        this.fileTypeName = fileTypeName.toUpperCase();
    }


    public List<ColumnDetails> getColumnDetails() {
        return columnDetails;
    }


    public void setColumnDetails(List<ColumnDetails> columnDetails) {
        this.columnDetails = columnDetails;
    }

    public Map<String,String> getColumn_to_datatype(){
        Map<String,String> col_datatype = new HashMap<>();
        for(ColumnDetails cd : columnDetails){
            col_datatype.put(cd.getColumnName(), cd.getDataType());
        }
        return col_datatype;
    }

    public Map<String,List<ValidationRule>> getColumn_to_rules(){
        Map<String,List<ValidationRule>> col_to_rules = new HashMap<>();
        for(ColumnDetails cd : columnDetails){
            List<ValidationRule> rules = cd.getRules();
            col_to_rules.put(cd.getColumnName(),rules);
        }
        return col_to_rules;
    }



	@Override
	public String toString() {
		return "FileType [fileTypeId=" + fileTypeId + ", fileTypeName=" + fileTypeName + ", columnDetails="
				+ columnDetails + "]";
	}






    
}
