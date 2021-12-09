package app.data_ingestion.helpers;

public class ValidationRules {

    private ValidationRules() {
        throw new IllegalStateException("Helper Class");
    }

    static String[] integer = { "NOT NULL", "EQUALS TO", "MIN LENGTH", ">=", ">", "<", "<=" };
    static String[] string = { "NOT NULL", "EQUALS TO", "MAX LENGTH", "NOT CONTAINS", "CONTAINS" };
    static String[] date = { "NOT NULL", "EQUALS TO", ">=", ">", "<", "<=" };

    
    /** 
     * return data type wise validation rules
     * @param ruleDataType
     * @return String[]
     */
    public static String[] getValidationRule(String ruleDataType) {

        String ruleDataTypeUpperCase = ruleDataType.toUpperCase();
        switch (ruleDataTypeUpperCase) {
            case LiteralConstants.INTEGER:
                return integer;
            case LiteralConstants.STRING:
                return string;
            case LiteralConstants.DATE:
                return date;
            default:
                break;
        }
        return new String[] {};
    }
}
