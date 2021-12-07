package app.data_ingestion;
import app.data_ingestion.dataLayer.dao.IUserDao;
import app.data_ingestion.dataLayer.models.User;
import app.data_ingestion.services.userAuthAndRegister.UserService;
import app.data_ingestion.services.userAuthAndRegister.UserServiceStatus;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
@ContextConfiguration
public class UserServiceTest {


    @Mock
    IUserDao userDao;

    @InjectMocks
    UserService userServiceImpl;

    User user;

    private User MockUserDetails(){
        return new User("anujdev1","Anu","admin","Dalhousie");
    }


    @Test
    void userAuthenticateTest() throws SQLException {
        String username = "anujdev1";
        String password = "Anu";
        List<User> users = new ArrayList<>();
        users.add(MockUserDetails());
        when(userDao.getUsers()).thenReturn(users);
        UserServiceStatus userServiceStatus = userServiceImpl.userAuthentication(username,password);
        assertEquals(UserServiceStatus.SUCCESS,userServiceStatus);
    }
    @Test
    void userAuthenticateInvalidTest() throws SQLException {
        String username = "anujdev";
        String password = "Anu";
        List<User> users = new ArrayList<>();
        users.add(MockUserDetails());
        when(userDao.getUsers()).thenReturn(users);
        UserServiceStatus userServiceStatus = userServiceImpl.userAuthentication(username,password);
        assertEquals(UserServiceStatus.INVALID_CREDENTIALS,userServiceStatus);
    }
    @Test
    void userAuthenticateExceptionTest() throws SQLException {
        String username = "anujdev1";
        String password = "Anu";
        when(userDao.getUsers()).thenThrow(SQLException.class);
        UserServiceStatus userServiceStatus = userServiceImpl.userAuthentication(username,password);
        assertEquals(UserServiceStatus.FAILURE,userServiceStatus);
    }

    @Test
    void userAlreadyExistsTest() throws SQLException {
        User user = MockUserDetails();
        List<User> users = new ArrayList<>();
        users.add(MockUserDetails());
        when(userDao.getUsers()).thenReturn(users);
        UserServiceStatus userServiceStatus = userServiceImpl.userRegistration(user);
        assertEquals(UserServiceStatus.USER_ALREADY_EXISTS,userServiceStatus);
    }

    @Test
    void userRegistrationTest() throws SQLException {
        User user = new User("anujd","anu","admin","Dalhousie");
        List<User> users = new ArrayList<>();
        users.add(MockUserDetails());
        when(userDao.getUsers()).thenReturn(users);
        when(userDao.addUser(user)).thenReturn(1);
        UserServiceStatus userServiceStatus = userServiceImpl.userRegistration(user);
        assertEquals(UserServiceStatus.SUCCESS,userServiceStatus);
    }

    @Test
    void userRegistrationFailTest() throws SQLException {
        User user = new User("anujd","anu","admin","Dalhousie");
        List<User> users = new ArrayList<>();
        users.add(MockUserDetails());
        when(userDao.getUsers()).thenThrow(SQLException.class);
        when(userDao.addUser(user)).thenThrow(SQLException.class);
        UserServiceStatus userServiceStatus = userServiceImpl.userRegistration(user);
        assertEquals(UserServiceStatus.FAILURE,userServiceStatus);
    }



}
