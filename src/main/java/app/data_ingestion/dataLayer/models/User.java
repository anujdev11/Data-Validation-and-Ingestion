package app.data_ingestion.dataLayer.models;

public class User {

    private String username;
    private String password;
    private String access_level;
    private String organization;

    /**
     * @param username
     * @param password
     * @param access_level
     * @param organization
     */
    public User(String username, String password, String access_level, String organization) {
        this.username = username;
        this.password = password;
        this.access_level = access_level;
        this.organization = organization;
    }

    
    /** 
     * @return String
     */
    public String getUsername() {
        return username;
    }

    
    /** 
     * @return String
     */
    public String getPassword() {
        return password;
    }

    
    /** 
     * @return String
     */
    public String getAccess_level() {
        return access_level;
    }

    
    /** 
     * @return String
     */
    public String getOrganization() {
        return organization;
    }

    
    /** 
     * @return String
     */
    @Override
    public String toString() {
        return "User [access_level=" + access_level + ", organization=" + organization + ", password=" + password
                + ", username=" + username + "]";
    }


}
