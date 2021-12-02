package app.data_ingestion.service_layer.servicesImpl;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class CSVExport {

    /**
     * @param path
     * @param headers
     * @param invalidRows
     * @throws IOException
     */
    public static void createCSV(String path, List<String> headers, List<List<String>> invalidRows) throws IOException {
        String content = "";


        for (String header : headers) {
            content += header + ",";
        }
        content = content.substring(0, content.length() - 1) + "\n";

        for (List<String> row : invalidRows) {
            for (String value : row) {
                content += value + ",";
            }
            content = formatEnding(content);
        }
        content = content.trim();
        writeToFile(path, content);


    }

    /**
     * @param path
     * @param content
     * @throws IOException
     */
    private static void writeToFile(String path, String content) throws IOException {

        File file = new File(path);

        // if file does not exists, then create it
        if (!file.exists()) {
            file.createNewFile();
        }
        FileWriter fileWriter = new FileWriter(path);
        fileWriter.write(content);
        fileWriter.flush();
        fileWriter.close();
    }

    /**
     * @param content
     * @return
     */
    private static String formatEnding(String content) {
        return content.substring(0, content.length() - 1) + "\n";
    }


}
