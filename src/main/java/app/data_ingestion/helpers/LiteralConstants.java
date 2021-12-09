package app.data_ingestion.helpers;

public class LiteralConstants {

    private LiteralConstants() {
        throw new IllegalStateException("Helper Class");
    }

    public static final String APP_CONFIG_PATH = ".//src//main//resources//app_config.properties";
    public static final String FILE_DEFINITION_CREATION_SUCCESS = "File Definition created successfully!";
    public static final String SYSTEM_ERROR_MESSAGE = "System error";
    public static final String LOGIN_SUCCESS_MESSAGE = "Login Successful";
    public static final String LOGIN_INVALID_CREDENTIALS_MESSAGE = "Invalid username/password";
    public static final String REGISTER_SUCCESS_MESSAGE = "Registration done successfully!";
    public static final String REGISTER_USER_ALREADY_EXISTS_MESSAGE = "Username already exits. Please try with another one";
    public static final String INVALID_CREDENTIAL_STRING = "Invalid Credentials.";
    public static final String USER_ALREADY_EXISTS_STRING = "User already exists with given username.";
    public static final String USER_NOT_FOUND_STRING = "User not found with given username.";
    public static final String INTERNAL_SERVER_ERROR_STRING = "Something bad happened! Please try again.";
    public static final String SQL_USER_INSERTION_FAILED_STRING = "Creating user failed, no rows affected.";
    public static final String SQL_FETCHING_ID_FAILED_STRING = "Creating user failed, no ID obtained.";
    public static final String SQL_USER_UPDATING_FAILED_STRING = "Updating user failed, no rows affected.";
    public static final String SQL_UPDATE_DONE="Record has been updated successfully!";

    public static final String STRING_CLASS_NAME = "java.lang.String";
    public static final String INTEGER_CLASS_NAME = "java.lang.Integer";
    public static final String FLOAT_CLASS_NAME = "java.lang.Float";
    public static final String DATE_CLASS_NAME = "java.lang.Date";

    public static final String NO_FILE_DEFINITION_FOR_ID = "No file definition for id: ";
    public static final String FILE_DEFINITION_ID = "file_definition_id";
    public static final String FILE_DEFINITION_NAME = "file_definition_name";
    public static final String FILE_DEFINITION_DETAILS = "file_definition_details";

    public static final String USERNAME = "username";
    public static final String PASSWORD = "password";
    public static final String ACCESS_LEVEL = "access_level";
    public static final String ORGANIZATION = "organization";
    public final static String STATUS = "status";
    public final static String MESSAGE = "message";
    public final static String CONTENT_DISPOSITION_ATTACHMENT = "attachment; filename=";
    public final static String INGEST_CONTENT_TYPE = "text/csv";
    public static final String ID = "id";
    public static final String FIRST_NAME = "first_name";
    public static final String LAST_NAME = "last_name";

    public static final String STRING = "STRING";
    public static final String INTEGER = "INTEGER";
    public static final String FLOAT = "FLOAT";
    public static final String DATE = "DATE";
    public final static String SIZED_NVARCHAR = "nvarchar(1000)";
    public final static String UNDERSCORE = "_";

    public static final String INGESTION_EMPTY_FILE_MESSAGE = "Data Ingestion cannot be performed on an empty file. Please try again.";
    public static final String INGESTION_INVALID_HEADERS_MESSAGE = "Invalid headers. Correct headers are: ";

    public static final String NOT_NULL = "NOT NULL";
    public static final String EQUALS_TO = "EQUALS TO";
    public static final String MIN_LENGTH = "MIN LENGTH";
    public static final String MAX_LENGTH = "MAX LENGTH";
    public static final String CONTAINS = "CONTAINS";
    public static final String NOT_CONTAINS = "NOT CONTAINS";
    public static final String GREATER_THAN_EQUAL_TO = ">=";
    public static final String GREATER_THAN = ">";
    public static final String LESS_THAN_EQUAL_TO = "<=";
    public static final String LESS_THAN = "<";
    public static final String SET_TEXT=" set ";
}
