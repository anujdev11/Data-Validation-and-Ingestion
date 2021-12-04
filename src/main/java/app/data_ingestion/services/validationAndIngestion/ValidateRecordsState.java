package app.data_ingestion.services.validationAndIngestion;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import app.data_ingestion.dataLayer.models.ValidationRule;

@Service
public class ValidateRecordsState implements IState{

    @Override
    public IState execute(IngestionService ingestionService, int id, MultipartFile file, String delimiter, String action)
            throws Exception {
        
        validateRecords(ingestionService);
        
        return new CreateErrorFileState();
    }

    private void validateRecords(IngestionService ingestionService) {

        ingestionService.setValidRows(new ArrayList<>());
        ingestionService.setInvalidRows(new ArrayList<>());
        ValidationRulesService validator = new ValidationRulesService();

        Map<String, List<ValidationRule>> colToRules = ingestionService.getFileType().getColumnToRules();
        for (List<String> row : ingestionService.getRows()){
            String violatedValidationRule = null;
            int counter = 0;
            for (String cellValue : row) {
                String header = ingestionService.getHeaders().get(counter);

                if (colToRules.containsKey(header)) {
                    violatedValidationRule = validator.validate(colToRules.get(header), header, cellValue,
                                            ingestionService.getMapColumnToDatatype());

                    if (!violatedValidationRule.isBlank()) {
                        List<String> temp_list = new ArrayList<>(row);
                        temp_list.add(violatedValidationRule);
                        ingestionService.getInvalidRows().add(temp_list);
                        break;
                    }
                }
                ++counter;
            }
            if (violatedValidationRule.isBlank()) {
                ingestionService.getValidRows().add(row);
            }
        }
    }
    
}
