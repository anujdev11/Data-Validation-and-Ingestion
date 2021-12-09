package app.data_ingestion.helpers;

public class QueryModificationHelper {

    private QueryModificationHelper() {
        throw new IllegalStateException("Helper class");
    }

    /** 
     * @param name
     * @return String
     */
    public static String addUnderscores(String name) {
        return name.replaceAll(name, LiteralConstants.UNDERSCORE);
    }


    /**
     * @param dType
     * @return
     */
    public static String getDataType(String dType) {
        if (dType.equalsIgnoreCase(LiteralConstants.STRING)) {
            return LiteralConstants.SIZED_NVARCHAR;
        }
        return dType;
    }
}
