package app.data_ingestion.dataLayer.models;

import java.util.HashMap;
import java.util.Map;

public class SystemUser {

    private int id;
    private String firstName;
    private String lastName;
    private String username;
    private String password;

    public SystemUser() {
    }

    /**
     * This constructor used when creating system user
     *
     * @param id
     * @param firstName
     * @param lastName
     * @param username
     * @param password
     */
    public SystemUser(int id, String firstName, String lastName, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    /**
     * This constructor used when fetching system user information
     *
     * @param id
     * @param firstName
     * @param lastName
     * @param username
     */
    public SystemUser(int id, String firstName, String lastName, String username) {
        this.id = id;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public SystemUser(String firstName, String lastName, String username) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public SystemUser(String username, String password) {
        this.username = username;
        this.password = password;
    }


    /**
     * @return int
     */
    public int getId() {
        return id;
    }


    /**
     * @return String
     */
    public String getFirstName() {
        return firstName;
    }


    /**
     * @return String
     */
    public String getLastName() {
        return lastName;
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
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }


    /**
     * @param firstName
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }


    /**
     * @param lastName
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }


    /**
     * @param username
     */
    public void setUsername(String username) {
        this.username = username;
    }


    /**
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
    }


    /**
     * @return Map<String, String>
     */
    public Map<String, String> toJson() {
        Map<String, String> jsonObject = new HashMap<>();
        jsonObject.put("id", String.valueOf(id));
        jsonObject.put("firstName", firstName);
        jsonObject.put("lastName", lastName);
        jsonObject.put("username", username);
        return jsonObject;
    }


    /**
     * @return String
     */
    @Override
    public String toString() {
        return "SystemUser = {" +
                "id=" + id +
                ", firstName=" + firstName +
                ", lastName=" + lastName +
                ", username=" + username +
                "}";
    }

}
