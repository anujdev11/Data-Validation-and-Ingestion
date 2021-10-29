package app.data_ingestion.data_layer.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "user")
public class User {
    
    @Id
    @Column(name = "username")
    private String username;

    @NotBlank
    @Column(name = "password")
    private String password;

    @NotBlank
    @Column(name = "access_level")
    private String access_level;

    public User(String username, String password, String access_level) {
        this.username = username;
        this.password = password;
        this.access_level = access_level;
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
    

    
}
