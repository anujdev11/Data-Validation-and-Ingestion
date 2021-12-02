package app.data_ingestion;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import app.data_ingestion.service_layer.services.ValidationRulesService;
import app.data_ingestion.service_layer.servicesImpl.ValidationRulesServiceImpl;
import app.data_ingestion.data_layer.models.ValidationRule;

@SpringBootTest
public class NumberValidationRulesServiceTest {
    
    @Test
    void notNullInValid(){

        ValidationRule rule = new ValidationRule();
        rule.setOperator("not null");
        List<ValidationRule> rules = new ArrayList<>();
        rules.add(rule);

        Map<String, String> mapColumnToDatatype = new HashMap<>();
        mapColumnToDatatype.put("SALES", "FLOAT");

        ValidationRulesService service = new ValidationRulesServiceImpl();
        String expected = String.format("%s %s", "SALES", rule.getOperator());
        assertEquals(expected, service.validate(rules, "SALES", "", mapColumnToDatatype)); ;

    }

    @Test
    void notNullValid(){

        ValidationRule rule = new ValidationRule();
        rule.setOperator("not null");
        List<ValidationRule> rules = new ArrayList<>();
        rules.add(rule);

        Map<String, String> mapColumnToDatatype = new HashMap<>();
        mapColumnToDatatype.put("SALES", "FLOAT");

        ValidationRulesService service = new ValidationRulesServiceImpl();
        String expected = "";
        assertEquals(expected, service.validate(rules, "SALES", "10", mapColumnToDatatype)); ;

    }

    @Test
    void equalsToInValid(){

        ValidationRule rule = new ValidationRule();
        rule.setOperator("equals to");
        rule.setRhsValue("1700");
        List<ValidationRule> rules = new ArrayList<>();
        rules.add(rule);

        Map<String, String> mapColumnToDatatype = new HashMap<>();
        mapColumnToDatatype.put("SALES", "FLOAT");

        ValidationRulesService service = new ValidationRulesServiceImpl();
        String expected = String.format("%s %s %s", "SALES", rule.getOperator(), rule.getRhsValue());
        assertEquals(expected, service.validate(rules, "SALES", "1700.01", mapColumnToDatatype)); ;

    }

    @Test
    void equalsToValid(){

        ValidationRule rule = new ValidationRule();
        rule.setOperator("equals to");
        rule.setRhsValue("1700");
        List<ValidationRule> rules = new ArrayList<>();
        rules.add(rule);

        Map<String, String> mapColumnToDatatype = new HashMap<>();
        mapColumnToDatatype.put("SALES", "FLOAT");

        ValidationRulesService service = new ValidationRulesServiceImpl();
        String expected = "";
        assertEquals(expected, service.validate(rules, "SALES", "1700.00", mapColumnToDatatype)); ;

    }

    @Test
    void minLengthInValid(){

        ValidationRule rule = new ValidationRule();
        rule.setOperator("min length");
        rule.setRhsValue("3");
        List<ValidationRule> rules = new ArrayList<>();
        rules.add(rule);

        Map<String, String> mapColumnToDatatype = new HashMap<>();
        mapColumnToDatatype.put("SALES", "FLOAT");

        ValidationRulesService service = new ValidationRulesServiceImpl();
        String expected = String.format("%s %s %s", "SALES", rule.getOperator(), rule.getRhsValue());
        assertEquals(expected, service.validate(rules, "SALES", "1700", mapColumnToDatatype));

    }

    @Test
    void minLengthValid(){

        ValidationRule rule = new ValidationRule();
        rule.setOperator("min length");
        rule.setRhsValue("3");
        List<ValidationRule> rules = new ArrayList<>();
        rules.add(rule);

        Map<String, String> mapColumnToDatatype = new HashMap<>();
        mapColumnToDatatype.put("SALES", "FLOAT");

        ValidationRulesService service = new ValidationRulesServiceImpl();
        String expected = "";
        assertEquals(expected, service.validate(rules, "SALES", "170", mapColumnToDatatype));

    }

    @Test
    void greaterThanEqualToInValid(){

        ValidationRule rule = new ValidationRule();
        rule.setOperator(">=");
        rule.setRhsValue("1800.02");
        List<ValidationRule> rules = new ArrayList<>();
        rules.add(rule);

        Map<String, String> mapColumnToDatatype = new HashMap<>();
        mapColumnToDatatype.put("SALES", "FLOAT");

        ValidationRulesService service = new ValidationRulesServiceImpl();
        String expected = String.format("%s %s %s", "SALES", rule.getOperator(), rule.getRhsValue());
        assertAll(
            () -> assertEquals(expected, service.validate(rules, "SALES", "1700", mapColumnToDatatype)),
            () -> assertEquals(expected, service.validate(rules, "SALES", "1800.01", mapColumnToDatatype))
        );
    }

    @Test
    void greaterThanEqualToValid(){

        ValidationRule rule = new ValidationRule();
        rule.setOperator(">=");
        rule.setRhsValue("1800.02");
        List<ValidationRule> rules = new ArrayList<>();
        rules.add(rule);

        Map<String, String> mapColumnToDatatype = new HashMap<>();
        mapColumnToDatatype.put("SALES", "FLOAT");

        ValidationRulesService service = new ValidationRulesServiceImpl();
        String expected = "";
        assertAll(
            () -> assertEquals(expected, service.validate(rules, "SALES", "1900", mapColumnToDatatype)),
            () -> assertEquals("", service.validate(rules, "SALES", "1800.02", mapColumnToDatatype))
        );
    }

    @Test
    void greaterThanInValid(){

        ValidationRule rule = new ValidationRule();
        rule.setOperator(">");
        rule.setRhsValue("1800.02");
        List<ValidationRule> rules = new ArrayList<>();
        rules.add(rule);

        Map<String, String> mapColumnToDatatype = new HashMap<>();
        mapColumnToDatatype.put("SALES", "FLOAT");

        ValidationRulesService service = new ValidationRulesServiceImpl();
        String expected = String.format("%s %s %s", "SALES", rule.getOperator(), rule.getRhsValue());
        assertAll(
            () -> assertEquals(expected, service.validate(rules, "SALES", "1700", mapColumnToDatatype)),
            () -> assertEquals(expected, service.validate(rules, "SALES", "1800.02", mapColumnToDatatype))
        );
    }

    @Test
    void greaterThanValid(){

        ValidationRule rule = new ValidationRule();
        rule.setOperator(">");
        rule.setRhsValue("1800.02");
        List<ValidationRule> rules = new ArrayList<>();
        rules.add(rule);

        Map<String, String> mapColumnToDatatype = new HashMap<>();
        mapColumnToDatatype.put("SALES", "FLOAT");

        ValidationRulesService service = new ValidationRulesServiceImpl();
        String expected = "";
        assertAll(
            () -> assertEquals(expected, service.validate(rules, "SALES", "1900", mapColumnToDatatype)),
            () -> assertEquals("", service.validate(rules, "SALES", "1800.03", mapColumnToDatatype))
        );
    }

    @Test
    void lessThanEqualToInValid(){

        ValidationRule rule = new ValidationRule();
        rule.setOperator("<=");
        rule.setRhsValue("1800.02");
        List<ValidationRule> rules = new ArrayList<>();
        rules.add(rule);

        Map<String, String> mapColumnToDatatype = new HashMap<>();
        mapColumnToDatatype.put("SALES", "FLOAT");

        ValidationRulesService service = new ValidationRulesServiceImpl();
        String expected = String.format("%s %s %s", "SALES", rule.getOperator(), rule.getRhsValue());
        assertAll(
            () -> assertEquals(expected, service.validate(rules, "SALES", "1900", mapColumnToDatatype)),
            () -> assertEquals(expected, service.validate(rules, "SALES", "1800.03", mapColumnToDatatype))
        );
    }

    @Test
    void lessThanEqualToValid(){

        ValidationRule rule = new ValidationRule();
        rule.setOperator("<=");
        rule.setRhsValue("1800.02");
        List<ValidationRule> rules = new ArrayList<>();
        rules.add(rule);

        Map<String, String> mapColumnToDatatype = new HashMap<>();
        mapColumnToDatatype.put("SALES", "FLOAT");

        ValidationRulesService service = new ValidationRulesServiceImpl();
        String expected = "";
        assertAll(
            () -> assertEquals(expected, service.validate(rules, "SALES", "1800", mapColumnToDatatype)),
            () -> assertEquals("", service.validate(rules, "SALES", "1800.02", mapColumnToDatatype))
        );
    }

    @Test
    void lessThanInValid(){

        ValidationRule rule = new ValidationRule();
        rule.setOperator("<");
        rule.setRhsValue("1800.02");
        List<ValidationRule> rules = new ArrayList<>();
        rules.add(rule);

        Map<String, String> mapColumnToDatatype = new HashMap<>();
        mapColumnToDatatype.put("SALES", "FLOAT");

        ValidationRulesService service = new ValidationRulesServiceImpl();
        String expected = String.format("%s %s %s", "SALES", rule.getOperator(), rule.getRhsValue());
        assertAll(
            () -> assertEquals(expected, service.validate(rules, "SALES", "1900", mapColumnToDatatype)),
            () -> assertEquals(expected, service.validate(rules, "SALES", "1800.02", mapColumnToDatatype))
        );
    }

    @Test
    void lessThanValid(){

        ValidationRule rule = new ValidationRule();
        rule.setOperator("<");
        rule.setRhsValue("1800.02");
        List<ValidationRule> rules = new ArrayList<>();
        rules.add(rule);

        Map<String, String> mapColumnToDatatype = new HashMap<>();
        mapColumnToDatatype.put("SALES", "FLOAT");

        ValidationRulesService service = new ValidationRulesServiceImpl();
        String expected = "";
        assertAll(
            () -> assertEquals(expected, service.validate(rules, "SALES", "1700", mapColumnToDatatype)),
            () -> assertEquals("", service.validate(rules, "SALES", "1800.01", mapColumnToDatatype))
        );
    }
    

}
