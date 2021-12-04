package app.data_ingestion.helpers;

public class QueryConstants {
    
    public static final String SELECT_WITHOUT_CONDITION = "select * from %s";
    public static final String CREATE_TABLE_QUERY= "create table if not exists `%s` (%s)";
    public static final String DELETE_QUERY= "delete from `%s`";
    public static final String DROP_QUERY= "drop table `%s` ";
    public static final String INSERT_QUERY= "insert into %s (%s) values (%s)";
    public static final String FILE_DEFINITION_INSERT_QUERY = "insert into file_definition ("
                                                            + "file_definition_name,"
                                                            + "file_definition_details) VALUES (?, ?)";
    public static final String FILE_DEFINITION_SELECT_QUERY = "select * from file_definition where file_definition_id = ? limit 1";
    public static final String FILE_DEFINITION_DELETE_QUERY = "delete from file_definition where file_definition_id = ?";

    public static final String USER_INSERT_QUERY = "insert into user (username, "
                                                    + "password, access_level, "
                                                    + "organization) VALUES (?, ?, ?, ?)";
    public static final String USER_DELETE_QUERY = "delete from user where username = ?";
    public static final String USER_SELECT_QUERY = "select * from user where username= ? limit 1";
    public static final String USER_UPDATE_QUERY = "update user set password= ?, access_level= ?, organization= ? where username = ?";
    
    
}
