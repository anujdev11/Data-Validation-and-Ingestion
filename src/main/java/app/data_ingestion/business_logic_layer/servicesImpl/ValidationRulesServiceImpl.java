package app.data_ingestion.business_logic_layer.servicesImpl;

import java.sql.Date;
import java.util.List;
import java.util.Map;

import app.data_ingestion.business_logic_layer.services.ValidationRulesService;
import app.data_ingestion.data_layer.models.ValidationRule;

public class ValidationRulesServiceImpl implements ValidationRulesService {

    public String validate(List<ValidationRule> rules, String header, String cellValue,
            Map<String, String> map_column_to_datatype) {
        System.out.println("-----rules-------------");
        System.out.println(rules);
        System.out.println("----header-----------");
        System.out.println(header);
        System.out.println("------cellValue-----");
        System.out.println(cellValue);
        System.out.println("------map_column_to_datatype----");
        System.out.println(map_column_to_datatype);

        String violatedValidationRule = "";
        for (ValidationRule rule : rules) {
            String operator = rule.getOperator();
            String rhsValue = rule.getRhsValue();

            switch (map_column_to_datatype.get(header)) {
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

    private String numberValidation(String operator, String rhsValue, String header, String cellValue) {
        String violatedValidationRule = "";
        Float floatCellValue = Float.valueOf(cellValue);
        Float floatRhsValue = Float.valueOf(rhsValue);
        switch (operator) {
            case "NOT NULL":
                if (cellValue.isBlank()) {
                    violatedValidationRule = String.format("%s %s", header, operator);
                }
                break;
            case "EQUALS TO":
                if (floatCellValue != floatRhsValue) {
                    violatedValidationRule = String.format("%s %s %s", header, operator, rhsValue);
                }
                break;
            case "MIN LENGTH":
                if (cellValue.length() > Integer.valueOf(rhsValue)) {
                    violatedValidationRule = String.format("%s %s %s", header, operator, rhsValue);
                }
                break;
            case ">=":
                if (!(floatCellValue >= floatRhsValue)) {
                    violatedValidationRule = String.format("%s %s %s", header, operator, rhsValue);
                }
                break;
            case ">":
                if (!(floatCellValue > floatRhsValue)) {
                    violatedValidationRule = String.format("%s %s %s", header, operator, rhsValue);
                }
                break;
            case "<":
                if (!(floatCellValue < floatRhsValue)) {
                    violatedValidationRule = String.format("%s %s %s", header, operator, rhsValue);
                }
                break;
            case "<=":
                if (!(floatCellValue <= floatRhsValue)) {
                    violatedValidationRule = String.format("%s %s %s", header, operator, rhsValue);
                }
                break;
            default:
                break;
        }
        return violatedValidationRule;
    }

    private String dateValidation(String operator, String rhsValue, String header, String cellValue) {
        String violatedValidationRule = "";
        Date dateCellValue = Date.valueOf(cellValue);
        Date dateRhsValue = Date.valueOf(rhsValue);
        switch (operator) {
            case "NOT NULL":
                if (cellValue.isBlank()) {
                    violatedValidationRule = String.format("%s %s", header, operator);
                }
                break;
            case "EQUALS TO":
                if (dateCellValue != dateRhsValue) {
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
