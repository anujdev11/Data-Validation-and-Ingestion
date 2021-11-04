package app.data_ingestion.data_layer.repositoriesImpl;


import app.data_ingestion.data_layer.models.User;
import app.data_ingestion.data_layer.repositories.UserRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

@Repository
public class UserRepositoryImpl extends JdbcDaoSupport implements UserRepository {

    private final JdbcTemplate jdbcTemplate;

    final
    DataSource dataSource;

    public UserRepositoryImpl(JdbcTemplate jdbcTemplate, DataSource dataSource) {
        this.jdbcTemplate = jdbcTemplate;
        this.dataSource = dataSource;
    }

    @PostConstruct
    private void initialize() {
        setDataSource(dataSource);
    }

    @Override
    public boolean userAuthentication(String username, String password) {
        String sql = "SELECT COUNT(*) FROM user where username = ? and password = ?";
        int count = jdbcTemplate.queryForObject(sql, new Object[]{username,password}, Integer.class);
        System.out.println("This is count " + count);
        if(count > 0)
            return true;
        else
            return false;
    }

    @Override
    public boolean userRegistration(User user) {
        String userInsertQuery = "INSERT INTO user (username,password,access_level,organization) VALUES (?, ?, ?, ?)";
        int success = jdbcTemplate.update(userInsertQuery,user.getUsername(),user.getPassword(), user.getAccess_level(), user.getOrganization());
        System.out.println("Success"+success);
        return true;
    }
}
