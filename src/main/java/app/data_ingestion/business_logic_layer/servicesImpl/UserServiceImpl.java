package app.data_ingestion.business_logic_layer.servicesImpl;

import app.data_ingestion.business_logic_layer.services.UserService;
import app.data_ingestion.data_layer.dao.UserDao;
import app.data_ingestion.data_layer.models.User;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserDao userDao;
    User user;

    

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
            
            if(authenticatedUser.isPresent()){
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
            if(!userExists(user.getUsername())){
                return userDao.add(user) > 0 ? UserServiceStatus.SUCCESS : UserServiceStatus.FAILURE;
            }
            else{
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
