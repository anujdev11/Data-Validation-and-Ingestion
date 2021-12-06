package app.data_ingestion.helpers;

public class QueryModificationHelper {

    private QueryModificationHelper() {
        throw new IllegalStateException("Helper class");
    }

    public static String addUnderscores(String name) {
        return name.replaceAll(name, "_");
    }

    public static String getDataType(String dType) {
        if (dType.equalsIgnoreCase("string")) {
            return "nvarchar(1000)";
        }
        return dType;
    }
}
