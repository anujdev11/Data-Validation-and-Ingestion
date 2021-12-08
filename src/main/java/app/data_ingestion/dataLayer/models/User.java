package app.data_ingestion.dataLayer.models;

import java.util.HashMap;
import java.util.Map;

public class User {

    private String username;
    private String password;
    private String accessLevel;
    private String organization;

    /**
     * @param username
     * @param password
     * @param accessLevel
     * @param organization
     */
    public User(String username, String password, String accessLevel, String organization) {
        this.username = username;
        this.password = password;
        this.accessLevel = accessLevel;
        this.organization = organization;
    }

    public User(String username, String accessLevel, String organization) {
        this.username = username;
        this.accessLevel = accessLevel;
        this.organization = organization;
    }

    public User() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public String getAccessLevel() {
        return accessLevel;
    }

    public String getOrganization() {
        return organization;
    }

    public Map<String, String> toJson() {
        Map<String, String> jsonObject = new HashMap<>();
        jsonObject.put("username", username);
        jsonObject.put("organization", organization);
        jsonObject.put("accessLevel", accessLevel);
        return jsonObject;
    }

    @Override
    public String toString() {
        return "User [accessLevel=" + accessLevel + ", organization=" + organization + ", password=" + password
                + ", username=" + username + "]";
    }

}

