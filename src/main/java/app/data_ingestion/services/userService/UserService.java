package app.data_ingestion.services.userService;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.data_ingestion.dataLayer.dao.IUserDao;
import app.data_ingestion.dataLayer.models.User;

@Service
public class UserService implements IUserService {

    @Autowired
    IUserDao userDao;
    User user;

    
    /** 
     * @return User
     */
    public User getUser() {
        return user;
    }

    /**
     * @param username
     * @param password
     * @return
     */
    @Override
    public UserServiceStatus userAuthentication(String username, String password) {

        try {
            List<User> users = userDao.getUsers();
            Optional<User> authenticatedUser = users.stream()
                    .filter(user -> user.getUsername().equalsIgnoreCase(username) &&
                            user.getPassword().equalsIgnoreCase(password))
                    .findFirst();

            if (authenticatedUser.isPresent()) {
                user = authenticatedUser.get();
                return UserServiceStatus.SUCCESS;
            }
            return UserServiceStatus.INVALID_CREDENTIALS;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return UserServiceStatus.FAILURE;
    }

    /**
     * @param user
     * @return
     */
    @Override
    public UserServiceStatus userRegistration(User user) {
        try {
            if (!userExists(user.getUsername())) {
                return userDao.addUser(user) > 0 ? UserServiceStatus.SUCCESS : UserServiceStatus.FAILURE;
            } else {
                return UserServiceStatus.USER_ALREADY_EXISTS;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return UserServiceStatus.FAILURE;
    }

    /**
     * @param username
     * @return
     * @throws SQLException
     */
    private boolean userExists(String username) throws SQLException {
        List<User> users = userDao.getUsers();
        Optional<User> userExists = users.stream()
                .filter(user -> user.getUsername().equalsIgnoreCase(username))
                .findFirst();
        return userExists.isPresent();
    }

}
