package com.udemy.sbforswe.springbootforsoftwareengineers.service;

import com.udemy.sbforswe.springbootforsoftwareengineers.dao.UserDao;
import com.udemy.sbforswe.springbootforsoftwareengineers.enums.Gender;
import com.udemy.sbforswe.springbootforsoftwareengineers.model.User;
import org.jvnet.hk2.annotations.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Component
public class UserService {
    private UserDao userDao;

    @Autowired
    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public List<User> getAllUsers(Optional<String> gender) {
        List<User> users= userDao.selectAllUsers();
        if(!gender.isPresent()){
            return users;
        }
        try{
            Gender theGender = Gender.valueOf(gender.get().toUpperCase());
            return users.stream().filter(user -> user.getGender().equals(theGender)).collect(Collectors.toList());
        }catch (Exception e){
            throw new IllegalStateException("Invalid Gender!", e);
        }
    }

    public Optional<User> getUser(UUID userUUID) {
        return userDao.selectUserByUserUUID(userUUID);
    }

    public int updateUser(User user) {
        Optional<User> optionalUser = getUser(user.getUserUUID());
        if(optionalUser.isPresent()){
            return userDao.updateUser(user);
        }
        return -1;
    }

    public int removeUser(UUID userUUID) {
        Optional<User> optionalUser = getUser(userUUID);
        if (optionalUser.isPresent()) {
            return userDao.deleteUserByUserUUId(userUUID);
        }
        return -1;
    }

    public int insertUser(User user) {
        UUID userUUID = UUID.randomUUID();
        return userDao.insertUser(userUUID, User.newUser(userUUID, user));
    }
}
