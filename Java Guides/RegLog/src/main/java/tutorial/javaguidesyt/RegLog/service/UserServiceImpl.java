package tutorial.javaguidesyt.RegLog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tutorial.javaguidesyt.RegLog.model.Role;
import tutorial.javaguidesyt.RegLog.model.User;
import tutorial.javaguidesyt.RegLog.repository.UserRepository;
import tutorial.javaguidesyt.RegLog.web.dto.UserRegistrationDto;

import java.util.Arrays;

@Service
public class UserServiceImpl implements UserService{

    private UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User save(UserRegistrationDto registrationDto) {
        User user = new User(registrationDto.getFirstName(), registrationDto.getLastName(), registrationDto.getEmail(), registrationDto.getPassword(), Arrays.asList(new Role("ROLE_USER")));
        return userRepository.save(user);
    }
}
