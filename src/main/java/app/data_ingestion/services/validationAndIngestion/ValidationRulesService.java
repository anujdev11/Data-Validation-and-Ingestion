package app.data_ingestion.services.validationAndIngestion;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;
import java.util.Map;

import app.data_ingestion.dataLayer.models.ValidationRule;
import app.data_ingestion.helpers.LiteralConstants;

public class ValidationRulesService implements IValidationRulesService {

    /**
     * @param rules
     * @param header
     * @param cellValue
     * @param mapColumnToDatatype
     * @return
     */
    public String validate(List<ValidationRule> rules, String header, String cellValue, Map<String, String> mapColumnToDatatype) {

        String violatedValidationRule = "";
        for (ValidationRule rule : rules) {
            String operator = rule.getOperator();
            String rhsValue = rule.getRhsValue();

            switch (mapColumnToDatatype.get(header)) {
                case LiteralConstants.STRING:
                    violatedValidationRule = stringValidation(operator, rhsValue, header, cellValue);
                    break;
                case LiteralConstants.INTEGER:
                case LiteralConstants.FLOAT:
                    violatedValidationRule = numberValidation(operator, rhsValue, header, cellValue);
                    break;
                case LiteralConstants.DATE:
                    violatedValidationRule = dateValidation(operator, rhsValue, header, cellValue);
                    break;
                default:
                    break;
            }

        }
        return violatedValidationRule;


    }

    /**
     * @param operator
     * @param rhsValue
     * @param header
     * @param cellValue
     * @return
     */
    private String numberValidation(String operator, String rhsValue, String header, String cellValue) {
        String violatedValidationRule = "";
        BigDecimal numCellValue = BigDecimal.ZERO;
        BigDecimal numRhsValue = BigDecimal.ZERO;
        int comparedValue = 0;
        if (!cellValue.isBlank()) {
            numCellValue = new BigDecimal(cellValue);
        }
        if (rhsValue != null && !rhsValue.isBlank()) {
            numRhsValue = new BigDecimal(rhsValue);
            comparedValue = numCellValue.compareTo(numRhsValue);
        }
        switch (operator) {
            case LiteralConstants.NOT_NULL:
                if (cellValue.isBlank()) {
                    violatedValidationRule = String.format("%s %s", header, operator);
                }
                break;
            case LiteralConstants.EQUALS_TO:
                if (comparedValue != 0) {
                    violatedValidationRule = String.format("%s %s %s", header, operator, rhsValue);
                }
                break;
            case LiteralConstants.MIN_LENGTH:
                if (cellValue.length() > Integer.valueOf(rhsValue)) {
                    violatedValidationRule = String.format("%s %s %s", header, operator, rhsValue);
                }
                break;
            case LiteralConstants.GREATER_THAN_EQUAL_TO:
                if (comparedValue < 0) {
                    violatedValidationRule = String.format("%s %s %s", header, operator, rhsValue);
                }
                break;
            case LiteralConstants.GREATER_THAN:
                if (comparedValue <= 0) {
                    violatedValidationRule = String.format("%s %s %s", header, operator, rhsValue);
                }
                break;
            case LiteralConstants.LESS_THAN:
                if (comparedValue >= 0) {
                    violatedValidationRule = String.format("%s %s %s", header, operator, rhsValue);
                }
                break;
            case LiteralConstants.LESS_THAN_EQUAL_TO:
                if (comparedValue > 0) {
                    violatedValidationRule = String.format("%s %s %s", header, operator, rhsValue);
                }
                break;
            default:
                break;
        }
        return violatedValidationRule;
    }

    /**
     * @param operator
     * @param rhsValue
     * @param header
     * @param cellValue
     * @return
     */
    private String dateValidation(String operator, String rhsValue, String header, String cellValue) {
        String violatedValidationRule = "";
        Date dateCellValue = null;
        Date dateRhsValue = null;
        if (!cellValue.isBlank()) {
            dateCellValue = Date.valueOf(cellValue);
        }
        if (rhsValue != null && !rhsValue.isBlank()) {
            dateRhsValue = Date.valueOf(rhsValue);
        }

        switch (operator) {
            case LiteralConstants.NOT_NULL:
                if (cellValue.isBlank()) {
                    violatedValidationRule = String.format("%s %s", header, operator);
                }
                break;
            case LiteralConstants.EQUALS_TO:
                if (!dateCellValue.equals(dateRhsValue)) {
                    violatedValidationRule = String.format("%s %s %s", header, operator, rhsValue);
                }
                break;
            case LiteralConstants.GREATER_THAN_EQUAL_TO:
                if (dateCellValue.before(dateRhsValue)) {
                    violatedValidationRule = String.format("%s %s %s", header, operator, rhsValue);
                }
                break;
            case LiteralConstants.GREATER_THAN:
                if (dateCellValue.equals(dateRhsValue) || dateCellValue.before(dateRhsValue)) {
                    violatedValidationRule = String.format("%s %s %s", header, operator, rhsValue);
                }
                break;
            case LiteralConstants.LESS_THAN:
                if (dateCellValue.equals(dateRhsValue) || dateCellValue.after(dateRhsValue)) {
                    violatedValidationRule = String.format("%s %s %s", header, operator, rhsValue);
                }
                break;
            case LiteralConstants.LESS_THAN_EQUAL_TO:
                if (dateCellValue.after(dateRhsValue)) {
                    violatedValidationRule = String.format("%s %s %s", header, operator, rhsValue);
                }
                break;
            default:
                break;
        }
        return violatedValidationRule;
    }


    /**
     * @param operator
     * @param rhsValue
     * @param header
     * @param cellValue
     * @return String
     */
    private String stringValidation(String operator, String rhsValue, String header, String cellValue) {
        String violatedValidationRule = "";
        switch (operator) {
            case LiteralConstants.NOT_NULL:
                if (cellValue.isBlank()) {
                    violatedValidationRule = String.format("%s %s", header, operator);
                }
                break;
            case LiteralConstants.EQUALS_TO:
                if (!cellValue.equalsIgnoreCase(rhsValue)) {
                    violatedValidationRule = String.format("%s %s %s", header, operator, rhsValue);
                }
                break;
            case LiteralConstants.MAX_LENGTH:
                if (cellValue.length() > Integer.valueOf(rhsValue)) {
                    violatedValidationRule = String.format("%s %s %s", header, operator, rhsValue);
                }
                break;
            case LiteralConstants.NOT_CONTAINS:
                if (cellValue.toUpperCase().contains(rhsValue)) {
                    violatedValidationRule = String.format("%s %s %s", header, operator, rhsValue);
                }
                break;
            case LiteralConstants.CONTAINS:
                if (!cellValue.toUpperCase().contains(rhsValue)) {
                    violatedValidationRule = String.format("%s %s %s", header, operator, rhsValue);
                }
                break;
            default:
                break;
        }
        return violatedValidationRule;
    }


}
