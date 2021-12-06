package app.data_ingestion.helpers;

public class QueryModificationHelper {

    
    /** 
     * @param name
     * @return String
     */
    public static String addUnderscores(String name) {
        return name.replaceAll(name, LiteralConstants.UNDERSCORE);
    }

    
    /** 
     * @param dtype
     * @return String
     */
    public static String getDataType(String dtype) {
        if (dtype.equalsIgnoreCase(LiteralConstants.STRING)) {
            return LiteralConstants.SIZED_NVARCHAR;
        }
        return dtype;
    }
}
