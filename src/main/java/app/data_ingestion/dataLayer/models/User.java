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

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getAccess_level() {
        return access_level;
    }

    public String getOrganization() {
        return organization;
    }

    @Override
    public String toString() {
        return "User [access_level=" + access_level + ", organization=" + organization + ", password=" + password
                + ", username=" + username + "]";
    }


}
