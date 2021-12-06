package app.data_ingestion.helpers;

public class ValidationRules {
    static final String[] integer = {"NOT NULL", "EQUALS TO", "MIN LENGTH", ">=", ">", "<", "<="};
    static final String[] string = {"NOT NULL", "EQUALS TO", "MAX LENGTH", "NOT CONTAINS", "CONTAINS"};
    static final String[] date = {"NOT NULL", "EQUALS TO", ">=", ">", "<", "<="};

    
    /** 
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
        String[] emptyArray = {};
        return emptyArray;
    }
}
