package io.getarrays.userservice.Repo;

import io.getarrays.userservice.Domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
