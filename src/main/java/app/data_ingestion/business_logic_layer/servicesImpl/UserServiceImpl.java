package app.data_ingestion.business_logic_layer.servicesImpl;

import app.data_ingestion.business_logic_layer.services.UserService;
import app.data_ingestion.data_layer.dao.UserDao;
import app.data_ingestion.data_layer.daoImpl.UserDaoImpl;
import app.data_ingestion.data_layer.models.User;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;


@Service
public class UserServiceImpl implements UserService {

    UserDao userDao;
    
    public UserServiceImpl(){
        userDao = new UserDaoImpl();
    }

    public boolean userAuthentication(String username, String password) {

        try {
            return userExists(username);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean userRegistration(User user) {
        try {
            if(!userExists(user.getUsername())){
                return userDao.add(user) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private boolean userExists(String username) throws SQLException {
        List<User> users = userDao.getUsers();
        Optional<User> authenticated_user = users.stream()
                                                .filter(user -> user.getUsername().equalsIgnoreCase(username))
                                                .findFirst();
        return authenticated_user != null;
    }


}
