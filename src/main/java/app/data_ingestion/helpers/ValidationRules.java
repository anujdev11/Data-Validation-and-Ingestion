package app.data_ingestion.helpers;

import java.util.ArrayList;
import java.util.List;

public class ValidationRules {
    static String[] integer = {"NOT NULL","EQUALS TO","MIN LENGTH",">=",">","<","<="};
    static String[] string = {"NOT NULL","EQUALS TO","MAX LENGTH","NOT CONTAINS","CONTAINS"};
    static String[] date = {"NOT NULL","EQUALS TO",">=",">","<","<="};

    public static String[] getValidationRule(String ruleDataType){
        switch (ruleDataType){
            case "Integer":
                return integer;
            case "String":
                return string;
            case "Date":
                return date;
            default:
                break;
        }
        String[] emptyArray = {};
        return emptyArray;
    }
}
