package app.data_ingestion.helpers;

public class QueryModificationHelper {

    public static String addUnderscores(String name) {
        return name.replaceAll(name, "_");
    }

    public static String getDataType(String dtype) {
        if (dtype.equalsIgnoreCase("string")) {
            return "nvarchar(1000)";
        }
        return dtype;
    }
}
