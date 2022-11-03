package tutorial.javaguidesyt.RegLog.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import tutorial.javaguidesyt.RegLog.model.User;
import tutorial.javaguidesyt.RegLog.web.dto.UserRegistrationDto;

@Service
public interface UserService extends UserDetailsService {
    User save(UserRegistrationDto registrationDto);
}
