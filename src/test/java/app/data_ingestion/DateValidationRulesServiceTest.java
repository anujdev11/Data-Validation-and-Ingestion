package app.data_ingestion;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

import app.data_ingestion.service_layer.services.ValidationRulesService;
import app.data_ingestion.service_layer.servicesImpl.ValidationRulesServiceImpl;
import app.data_ingestion.data_layer.models.ValidationRule;

public class DateValidationRulesServiceTest {

    @Test
    void notNullInValid() {

        ValidationRule rule = new ValidationRule();
        rule.setOperator("not null");
        List<ValidationRule> rules = new ArrayList<>();
        rules.add(rule);

        Map<String, String> mapColumnToDatatype = new HashMap<>();
        mapColumnToDatatype.put("TERMINATION_DATE", "DATE");

        ValidationRulesService service = new ValidationRulesServiceImpl();
        String expected = String.format("%s %s", "TERMINATION_DATE", rule.getOperator());
        assertEquals(expected, service.validate(rules, "TERMINATION_DATE", "", mapColumnToDatatype));
        ;

    }

    @Test
    void notNullValid() {

        ValidationRule rule = new ValidationRule();
        rule.setOperator("not null");
        List<ValidationRule> rules = new ArrayList<>();
        rules.add(rule);

        Map<String, String> mapColumnToDatatype = new HashMap<>();
        mapColumnToDatatype.put("TERMINATION_DATE", "DATE");

        ValidationRulesService service = new ValidationRulesServiceImpl();
        String expected = "";
        assertEquals(expected, service.validate(rules, "TERMINATION_DATE", "2012-01-01", mapColumnToDatatype));
        ;

    }

    @Test
    void equalsToInValid() {

        ValidationRule rule = new ValidationRule();
        rule.setOperator("equals to");
        rule.setRhsValue("2012-01-01");
        List<ValidationRule> rules = new ArrayList<>();
        rules.add(rule);

        Map<String, String> mapColumnToDatatype = new HashMap<>();
        mapColumnToDatatype.put("TERMINATION_DATE", "DATE");

        ValidationRulesService service = new ValidationRulesServiceImpl();
        String expected = String.format("%s %s %s", "TERMINATION_DATE", rule.getOperator(), rule.getRhsValue());
        assertEquals(expected, service.validate(rules, "TERMINATION_DATE", "2012-01-02", mapColumnToDatatype));
        ;

    }

    @Test
    void equalsToValid() {

        ValidationRule rule = new ValidationRule();
        rule.setOperator("equals to");
        rule.setRhsValue("2012-01-01");
        List<ValidationRule> rules = new ArrayList<>();
        rules.add(rule);

        Map<String, String> mapColumnToDatatype = new HashMap<>();
        mapColumnToDatatype.put("TERMINATION_DATE", "DATE");

        ValidationRulesService service = new ValidationRulesServiceImpl();
        String expected = "";
        assertEquals(expected, service.validate(rules, "TERMINATION_DATE", "2012-01-01", mapColumnToDatatype));
        ;

    }


    @Test
    void greaterThanEqualToInValid() {

        ValidationRule rule = new ValidationRule();
        rule.setOperator(">=");
        rule.setRhsValue("2012-01-01");
        List<ValidationRule> rules = new ArrayList<>();
        rules.add(rule);

        Map<String, String> mapColumnToDatatype = new HashMap<>();
        mapColumnToDatatype.put("TERMINATION_DATE", "DATE");

        ValidationRulesService service = new ValidationRulesServiceImpl();
        String expected = String.format("%s %s %s", "TERMINATION_DATE", rule.getOperator(), rule.getRhsValue());
        assertAll(
                () -> assertEquals(expected, service.validate(rules, "TERMINATION_DATE", "2011-01-01", mapColumnToDatatype)),
                () -> assertEquals(expected, service.validate(rules, "TERMINATION_DATE", "2011-12-31", mapColumnToDatatype))
        );
    }

    @Test
    void greaterThanEqualToValid() {

        ValidationRule rule = new ValidationRule();
        rule.setOperator(">=");
        rule.setRhsValue("2012-01-01");
        List<ValidationRule> rules = new ArrayList<>();
        rules.add(rule);

        Map<String, String> mapColumnToDatatype = new HashMap<>();
        mapColumnToDatatype.put("TERMINATION_DATE", "DATE");

        ValidationRulesService service = new ValidationRulesServiceImpl();
        String expected = "";
        assertAll(
                () -> assertEquals(expected, service.validate(rules, "TERMINATION_DATE", "2012-01-02", mapColumnToDatatype)),
                () -> assertEquals("", service.validate(rules, "TERMINATION_DATE", "2012-01-01", mapColumnToDatatype))
        );
    }

    @Test
    void greaterThanInValid() {

        ValidationRule rule = new ValidationRule();
        rule.setOperator(">");
        rule.setRhsValue("2012-01-01");
        List<ValidationRule> rules = new ArrayList<>();
        rules.add(rule);

        Map<String, String> mapColumnToDatatype = new HashMap<>();
        mapColumnToDatatype.put("TERMINATION_DATE", "DATE");

        ValidationRulesService service = new ValidationRulesServiceImpl();
        String expected = String.format("%s %s %s", "TERMINATION_DATE", rule.getOperator(), rule.getRhsValue());
        assertAll(
                () -> assertEquals(expected, service.validate(rules, "TERMINATION_DATE", "2011-01-01", mapColumnToDatatype)),
                () -> assertEquals(expected, service.validate(rules, "TERMINATION_DATE", "2010-01-01", mapColumnToDatatype))
        );
    }

    @Test
    void greaterThanValid() {

        ValidationRule rule = new ValidationRule();
        rule.setOperator(">");
        rule.setRhsValue("2012-01-01");
        List<ValidationRule> rules = new ArrayList<>();
        rules.add(rule);

        Map<String, String> mapColumnToDatatype = new HashMap<>();
        mapColumnToDatatype.put("TERMINATION_DATE", "DATE");

        ValidationRulesService service = new ValidationRulesServiceImpl();
        String expected = "";
        assertAll(
                () -> assertEquals(expected, service.validate(rules, "TERMINATION_DATE", "2012-01-02", mapColumnToDatatype)),
                () -> assertEquals("", service.validate(rules, "TERMINATION_DATE", "2012-01-06", mapColumnToDatatype))
        );
    }

    @Test
    void lessThanEqualToInValid() {

        ValidationRule rule = new ValidationRule();
        rule.setOperator("<=");
        rule.setRhsValue("2012-01-01");
        List<ValidationRule> rules = new ArrayList<>();
        rules.add(rule);

        Map<String, String> mapColumnToDatatype = new HashMap<>();
        mapColumnToDatatype.put("TERMINATION_DATE", "DATE");

        ValidationRulesService service = new ValidationRulesServiceImpl();
        String expected = String.format("%s %s %s", "TERMINATION_DATE", rule.getOperator(), rule.getRhsValue());
        assertAll(
                () -> assertEquals(expected, service.validate(rules, "TERMINATION_DATE", "2012-01-02", mapColumnToDatatype)),
                () -> assertEquals(expected, service.validate(rules, "TERMINATION_DATE", "2012-01-08", mapColumnToDatatype))
        );
    }

    @Test
    void lessThanEqualToValid() {

        ValidationRule rule = new ValidationRule();
        rule.setOperator("<=");
        rule.setRhsValue("2012-01-01");
        List<ValidationRule> rules = new ArrayList<>();
        rules.add(rule);

        Map<String, String> mapColumnToDatatype = new HashMap<>();
        mapColumnToDatatype.put("TERMINATION_DATE", "DATE");

        ValidationRulesService service = new ValidationRulesServiceImpl();
        String expected = "";
        assertAll(
                () -> assertEquals(expected, service.validate(rules, "TERMINATION_DATE", "2012-01-01", mapColumnToDatatype)),
                () -> assertEquals("", service.validate(rules, "TERMINATION_DATE", "2011-01-01", mapColumnToDatatype))
        );
    }

    @Test
    void lessThanInValid() {

        ValidationRule rule = new ValidationRule();
        rule.setOperator("<");
        rule.setRhsValue("2012-01-01");
        List<ValidationRule> rules = new ArrayList<>();
        rules.add(rule);

        Map<String, String> mapColumnToDatatype = new HashMap<>();
        mapColumnToDatatype.put("TERMINATION_DATE", "DATE");

        ValidationRulesService service = new ValidationRulesServiceImpl();
        String expected = String.format("%s %s %s", "TERMINATION_DATE", rule.getOperator(), rule.getRhsValue());
        assertAll(
                () -> assertEquals(expected, service.validate(rules, "TERMINATION_DATE", "2013-01-01", mapColumnToDatatype)),
                () -> assertEquals(expected, service.validate(rules, "TERMINATION_DATE", "2012-01-22", mapColumnToDatatype))
        );
    }

    @Test
    void lessThanValid() {

        ValidationRule rule = new ValidationRule();
        rule.setOperator("<");
        rule.setRhsValue("2012-01-01");
        List<ValidationRule> rules = new ArrayList<>();
        rules.add(rule);

        Map<String, String> mapColumnToDatatype = new HashMap<>();
        mapColumnToDatatype.put("TERMINATION_DATE", "DATE");

        ValidationRulesService service = new ValidationRulesServiceImpl();
        String expected = "";
        assertAll(
                () -> assertEquals(expected, service.validate(rules, "TERMINATION_DATE", "2011-01-01", mapColumnToDatatype)),
                () -> assertEquals("", service.validate(rules, "TERMINATION_DATE", "2007-01-01", mapColumnToDatatype))
        );
    }

}
