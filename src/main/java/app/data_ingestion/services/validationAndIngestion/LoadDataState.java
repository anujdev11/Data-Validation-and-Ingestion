package app.data_ingestion.services.validationAndIngestion;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import app.data_ingestion.helpers.CustomExceptions;
import app.data_ingestion.helpers.LiteralConstants;

@Service
@Qualifier("loadDataState")
public class LoadDataState implements IState{

    @Override
    public IState execute(IngestionService ingestionService, int id, MultipartFile file, String delimiter, String action) 
                    throws Exception {

        String contents = retrieveFileContentsAsString(file);
        System.out.println(contents);
        if (contents != null && !contents.trim().isEmpty()) {
            ingestionService.setFileType(ingestionService.getFileTypeDao().getFileTypeById(id));
            ingestionService.setMapColumnToDatatype(ingestionService.getFileType().getColumnToDatatype());

            populateHeadersAndRows(ingestionService, contents, delimiter);
        }
        else{
            throw new CustomExceptions.EmptyFileException(
                    LiteralConstants.INGESTION_EMPTY_FILE_MESSAGE);
        }
        return new ValidateHeadersState();
    }

    public String retrieveFileContentsAsString(MultipartFile inputFile) throws IOException {
        String content = new String(inputFile.getBytes());
        System.out.println(content);
        return content;
    }

    private void populateHeadersAndRows(IngestionService ingestionService, String fileContent, String delimiter) {
        ingestionService.setHeaders(new ArrayList<>());
        ingestionService.setRows(new ArrayList<>());
        String[] lines = fileContent.split(System.lineSeparator());
        ingestionService.setHeaders(Arrays.asList(lines[0].split(delimiter)));

        for (int rowNum = 1; rowNum < lines.length; rowNum++) {
            List<String> row = Arrays.asList(lines[rowNum].split(delimiter));
            ingestionService.getRows().add(row);
        }

    }
    
}
