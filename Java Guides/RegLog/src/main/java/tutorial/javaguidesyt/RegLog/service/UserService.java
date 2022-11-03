package tutorial.javaguidesyt.RegLog.service;

import org.springframework.stereotype.Service;
import tutorial.javaguidesyt.RegLog.model.User;
import tutorial.javaguidesyt.RegLog.web.dto.UserRegistrationDto;

@Service
public interface UserService {
    User save(UserRegistrationDto registrationDto);
}
