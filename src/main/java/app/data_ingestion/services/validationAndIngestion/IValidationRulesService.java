package app.data_ingestion.services.validationAndIngestion;

import java.util.List;
import java.util.Map;

import app.data_ingestion.dataLayer.models.ValidationRule;

public interface IValidationRulesService {

    /**
     * @param rules
     * @param header
     * @param cellValue
     * @param map_column_to_datatype
     * @return
     */
    String validate(List<ValidationRule> rules, String header, String cellValue, Map<String, String> map_column_to_datatype);

}
