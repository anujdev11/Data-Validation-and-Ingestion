package app.data_ingestion.business_logic_layer.servicesImpl;

import app.data_ingestion.business_logic_layer.services.UserService;
import app.data_ingestion.data_layer.models.User;
import app.data_ingestion.data_layer.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    public boolean userAuthentication(String username, String password) {
        return userRepository.userAuthentication(username, password);
    }

    @Override
    public boolean userRegistration(User user) {
        return userRepository.userRegistration(user);
    }


}
