package app.data_ingestion.helpers;

public class QueryModificationHelper {

    private QueryModificationHelper() {
        throw new IllegalStateException("Helper class");
    }

    /** 
     * add underspace in place of space
     * @param name
     * @return String
     */
    public static String addUnderscores(String name) {
        return name.replaceAll(name, LiteralConstants.UNDERSCORE);
    }

    
    /** 
     * return nvarchar in place of string
     * @param dtype
     * @return String
     */
    public static String getDataType(String dType) {
        if (dType.equalsIgnoreCase(LiteralConstants.STRING)) {
            return LiteralConstants.SIZED_NVARCHAR;
        }
        return dType;
    }
}
