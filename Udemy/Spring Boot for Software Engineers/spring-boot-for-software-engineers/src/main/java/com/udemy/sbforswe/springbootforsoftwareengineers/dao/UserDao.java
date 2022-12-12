package com.udemy.sbforswe.springbootforsoftwareengineers.dao;

import com.udemy.sbforswe.springbootforsoftwareengineers.model.User;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserDao {
    List<User> selectAllUsers();
    Optional<User> selectUserByUserUUID(UUID userUUID);
    int updateUser(User user);
    int deleteUserByUserUUId(UUID userUUID);
    int insertUser(UUID userUUID, User user);
}
