package io.getarrays.userservice.Service;

import io.getarrays.userservice.Domain.Role;
import io.getarrays.userservice.Domain.User;
import io.getarrays.userservice.Repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public interface UserService {
    User saveUser(User user);
    Role saveRole(Role role);
    void addRoleToUser(String username, String roleName);
    User getUser(String username);
    List<User> getUsers();
}
