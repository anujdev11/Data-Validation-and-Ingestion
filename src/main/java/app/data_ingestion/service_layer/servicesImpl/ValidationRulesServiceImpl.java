package app.data_ingestion.service_layer.servicesImpl;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;
import java.util.Map;

import app.data_ingestion.service_layer.services.ValidationRulesService;
import app.data_ingestion.data_layer.models.ValidationRule;

public class ValidationRulesServiceImpl implements ValidationRulesService {

    /**
     * @param rules
     * @param header
     * @param cellValue
     * @param mapColumnToDatatype
     * @return
     */
    public String validate(List<ValidationRule> rules, String header, String cellValue, Map<String, String> mapColumnToDatatype) {
        System.out.println("-----rules-------------");
        System.out.println(rules);
        System.out.println("----header-----------");
        System.out.println(header);
        System.out.println("------cellValue-----");
        System.out.println(cellValue);
        System.out.println("------map_column_to_datatype----");
        System.out.println(mapColumnToDatatype);

        String violatedValidationRule = "";
        for (ValidationRule rule : rules) {
            String operator = rule.getOperator();
            String rhsValue = rule.getRhsValue();

            switch (mapColumnToDatatype.get(header)) {
                case "STRING":
                    violatedValidationRule = stringValidation(operator, rhsValue, header, cellValue);
                    break;
                case "INTEGER":
                case "FLOAT":
                    violatedValidationRule = numberValidation(operator, rhsValue, header, cellValue);
                    break;
                case "DATE":
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
            case "NOT NULL":
                if (cellValue.isBlank()) {
                    violatedValidationRule = String.format("%s %s", header, operator);
                }
                break;
            case "EQUALS TO":
                System.out.println("--numCellValue-- " + numCellValue + " --numRhsValue--- " + numRhsValue);
                if (comparedValue != 0) {
                    violatedValidationRule = String.format("%s %s %s", header, operator, rhsValue);
                }
                break;
            case "MIN LENGTH":
                if (cellValue.length() > Integer.valueOf(rhsValue)) {
                    violatedValidationRule = String.format("%s %s %s", header, operator, rhsValue);
                }
                break;
            case ">=":
                if (comparedValue < 0) {
                    violatedValidationRule = String.format("%s %s %s", header, operator, rhsValue);
                }
                break;
            case ">":
                if (comparedValue <= 0) {
                    violatedValidationRule = String.format("%s %s %s", header, operator, rhsValue);
                }
                break;
            case "<":
                if (comparedValue >= 0) {
                    violatedValidationRule = String.format("%s %s %s", header, operator, rhsValue);
                }
                break;
            case "<=":
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
        Date dateCellValue = null;//Date.valueOf(cellValue);
        Date dateRhsValue = null;//Date.valueOf(rhsValue);
        if (!cellValue.isBlank()) {
            dateCellValue = Date.valueOf(cellValue);
        }
        if (rhsValue != null && !rhsValue.isBlank()) {
            dateRhsValue = Date.valueOf(rhsValue);
        }

        switch (operator) {
            case "NOT NULL":
                if (cellValue.isBlank()) {
                    violatedValidationRule = String.format("%s %s", header, operator);
                }
                break;
            case "EQUALS TO":
                if (!dateCellValue.equals(dateRhsValue)) {
                    violatedValidationRule = String.format("%s %s %s", header, operator, rhsValue);
                }
                break;
            case ">=":
                if (dateCellValue.before(dateRhsValue)) {
                    violatedValidationRule = String.format("%s %s %s", header, operator, rhsValue);
                }
                break;
            case ">":
                if (dateCellValue.equals(dateRhsValue) || dateCellValue.before(dateRhsValue)) {
                    violatedValidationRule = String.format("%s %s %s", header, operator, rhsValue);
                }
                break;
            case "<":
                if (dateCellValue.equals(dateRhsValue) || dateCellValue.after(dateRhsValue)) {
                    violatedValidationRule = String.format("%s %s %s", header, operator, rhsValue);
                }
                break;
            case "<=":
                if (dateCellValue.after(dateRhsValue)) {
                    violatedValidationRule = String.format("%s %s %s", header, operator, rhsValue);
                }
                break;
            default:
                break;
        }
        return violatedValidationRule;
    }

    private String stringValidation(String operator, String rhsValue, String header, String cellValue) {
        String violatedValidationRule = "";
        switch (operator) {
            case "NOT NULL":
                if (cellValue.isBlank()) {
                    violatedValidationRule = String.format("%s %s", header, operator);
                }
                break;
            case "EQUALS TO":
                if (!cellValue.equalsIgnoreCase(rhsValue)) {
                    violatedValidationRule = String.format("%s %s %s", header, operator, rhsValue);
                }
                break;
            case "MAX LENGTH":
                if (cellValue.length() > Integer.valueOf(rhsValue)) {
                    violatedValidationRule = String.format("%s %s %s", header, operator, rhsValue);
                }
                break;
            case "NOT CONTAINS":
                if (cellValue.toUpperCase().contains(rhsValue)) {
                    violatedValidationRule = String.format("%s %s %s", header, operator, rhsValue);
                }
                break;
            case "CONTAINS":
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
