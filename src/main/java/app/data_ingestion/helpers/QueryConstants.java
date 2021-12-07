package app.data_ingestion.helpers;

public class QueryConstants {

    private QueryConstants() {
        throw new IllegalStateException("Helper Class");
    }

    public static final String SELECT_WITHOUT_CONDITION = "SELECT * FROM %s";
    public static final String CREATE_TABLE_QUERY = "CREATE TABLE IF NOT EXISTS `%s` (%s)";
    public static final String DELETE_QUERY = "DELETE FROM `%s`";
    public static final String DROP_QUERY = "DROP TABLE `%s` ";
    public static final String INSERT_QUERY = "INSERT INTO %s (%s) VALUES (%s)";
    public static final String FILE_DEFINITION_INSERT_QUERY = "INSERT INTO file_definition ("
            + "file_definition_name,"
            + "file_definition_details) VALUES (?, ?)";
    public static final String FILE_DEFINITION_SELECT_QUERY = "SELECT * from file_definition WHERE file_definition_id = ? limit 1";
    public static final String FILE_DEFINITION_DELETE_QUERY = "DELETE from file_definition WHERE file_definition_id = ?";
    public static final String FILE_DEFINITION_UPDATE_QUERY = "update file_definition set file_definition_name = ? ,file_definition_details = ? where file_definition_id = ? ";
    
    public static final String USER_INSERT_QUERY = "INSERT INTO user (username, "
            + "password, access_level, "
            + "organization) VALUES (?, ?, ?, ?)";
    public static final String USER_DELETE_QUERY = "DELETE FROM user WHERE username = ?";
    public static final String USER_SELECT_QUERY = "SELECT * FROM user WHERE username= ? AND ISNULL(deleted_at) limit 1";
    public static final String USER_UPDATE_QUERY = "UPDATE user SET password= ?, access_level= ?, organization= ? where username = ?";
    public static final String USER_SELECT_ALL_QUERY = "SELECT * FROM user?";

    public static final String SYSTEM_USER_AUTHENTICATE_QUERY = "SELECT id, first_name, last_name, username FROM system_user WHERE username = ? AND password = ? AND ISNULL(deleted_at) LIMIT 1";
    public static final String SYSTEM_USER_INSERT_QUERY = "INSERT INTO system_user (first_name, last_name, username, password) VALUES (?, ?, ?, ?)";
    public static final String SYSTEM_USER_DELETE_QUERY = "UPDATE system_user SET deleted_at = CURRENT_TIMESTAMP() WHERE username = ? AND ISNULL(deleted_at)";
    public static final String SYSTEM_USER_UPDATE_QUERY = "UPDATE system_user SET first_name = ?, last_name = ?, password = ?, updated_at = current_timestamp() WHERE username = ? AND ISNULL(deleted_at)";
    public static final String SYSTEM_USER_SELECT_QUERY = "SELECT id, first_name, last_name, username FROM system_user WHERE username = ? AND ISNULL(deleted_at)";

    public static final String ORGANIZATION_ADMIN_INSERT_QUERY = "INSERT INTO user (username, password, organization, access_level) VALUES (?, ?, ?, ?)";
    public static final String ORGANIZATION_ADMIN_DELETE_QUERY = "UPDATE user SET deleted_at = CURRENT_TIMESTAMP() WHERE username = ? AND ISNULL(deleted_at)";
    public static final String ORGANIZATION_ADMIN_UPDATE_QUERY = "UPDATE user SET password= ?, access_level= ?, organization= ?, updated_at = current_timestamp() WHERE username = ? AND ISNULL(deleted_at)";
    public static final String ORGANIZATION_ADMIN_SELECT_QUERY = "SELECT username, access_level, organization FROM user WHERE username = ? AND ISNULL(deleted_at)";
    public static final String ORGANIZATION_ADMIN_LIST_SELECT_QUERY = "SELECT username, access_level, organization FROM user WHERE ISNULL(deleted_at)";
}
