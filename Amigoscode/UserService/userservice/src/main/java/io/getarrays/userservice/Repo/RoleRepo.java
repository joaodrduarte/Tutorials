package io.getarrays.userservice.Repo;

import io.getarrays.userservice.Domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepo extends JpaRepository<Role, Long> {
    Role findByName(String name);

}
