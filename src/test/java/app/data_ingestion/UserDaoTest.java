//package app.data_ingestion;
//
//import app.data_ingestion.dataLayer.dao.UserDao;
//import app.data_ingestion.dataLayer.models.User;
//import app.data_ingestion.services.userAuthAndRegister.UserServiceStatus;
//import org.junit.Before;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.ArgumentMatchers;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.mockito.junit.jupiter.MockitoSettings;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.test.util.ReflectionTestUtils;
//
//import javax.sql.DataSource;
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.util.ArrayList;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertFalse;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.ArgumentMatchers.eq;
//import static org.mockito.Mockito.when;
//
//@ExtendWith(MockitoExtension.class)
//public class UserDaoTest {
//
//    @Mock
//    private DataSource ds;
//
//    @Mock
//    private Connection c;
//
//    @Mock
//    private PreparedStatement stmt;
//
//    @Mock
//    JdbcTemplate  jdbcTemplate;
//
//    @Mock
//    private ResultSet rs;
//
////    @InjectMocks
////    private UserDao userDao;
//
//
//    User user;
//
//
//    @Before
//    public void setUp() throws Exception {
//        when(c.prepareStatement(any(String.class))).thenReturn(stmt);
//        when(ds.getConnection()).thenReturn(c);
//        when(jdbcTemplate.getDataSource().getConnection()).thenReturn(c);
//
//        user = new User("anujdev1","Anu","admin","Dalhousie");
//
//        when(rs.first()).thenReturn(true);
//        when(rs.getInt(1)).thenReturn(1);
//        when(rs.getString(2)).thenReturn(user.getUsername());
//        when(rs.getString(3)).thenReturn(user.getPassword());
//        when(rs.getString(4)).thenReturn(user.getAccessLevel());
//        when(rs.getString(5)).thenReturn(user.getOrganization());
//        when(stmt.executeQuery()).thenReturn(rs);
//    }
//
//    private User MockUserDetails(){
//        return new User("anujdev1","Anu","admin","Dalhousie");
//    }
//
//    @Test
//    void getUsersTest() throws SQLException {
//        UserDao userDao = new UserDao(jdbcTemplate,ds);
//        userDao.addUser(user);
////        List<User> users = new ArrayList<>();
////        users.add(MockUserDetails());
////        when(jdbcTemplate.)
////        when(userDao.getUsers()).thenReturn(users);
////
////        List<User> users_all = userDao.getUsers();
////        assertEquals(users,users_all);
//    }
//}
