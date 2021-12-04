package app.data_ingestion.dataLayer.models;

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

    public Map<String, String> getColumnToDatatype() {
        Map<String, String> colDatatype = new HashMap<>();
        for (ColumnDetails cd : columnDetails) {
            colDatatype.put(cd.getColumnName(), cd.getDataType());
        }
        return colDatatype;
    }

    public Map<String, List<ValidationRule>> getColumnToRules() {
        Map<String, List<ValidationRule>> colToRules = new HashMap<>();
        for (ColumnDetails cd : columnDetails) {
            List<ValidationRule> rules = cd.getRules();
            colToRules.put(cd.getColumnName(), rules);
        }
        return colToRules;
    }


    @Override
    public String toString() {
        return "FileType [fileTypeId=" + fileTypeId + ", fileTypeName=" + fileTypeName + ", columnDetails="
                + columnDetails + "]";
    }


}
