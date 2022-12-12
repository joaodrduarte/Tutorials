package com.udemy.sbforswe.springbootforsoftwareengineers.service;

import com.udemy.sbforswe.springbootforsoftwareengineers.dao.UserDao;
import com.udemy.sbforswe.springbootforsoftwareengineers.model.User;
import org.jvnet.hk2.annotations.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {
    private UserDao userDao;

    @Autowired
    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public List<User> getAllUsers() {
        return userDao.selectAllUsers();
    }

    public Optional<User> getUser(UUID userUUID) {
        return userDao.selectUserByUserUUID(userUUID);
    }

    public int updateUser(User user) {
        Optional<User> optionalUser = getUser(user.getUserUUID());
        if(optionalUser.isPresent()){
            userDao.updateUser(user);
            return 1;
        }
        return -1;
    }

    public int removeUser(UUID userUUID) {
        Optional<User> optionalUser = getUser(userUUID);
        if (optionalUser.isPresent()) {
            userDao.deleteUserByUserUUId(userUUID);
            return 1;
        }
        return -1;
    }

    public int insertUser(UUID userUUID, User user) {
        userDao.insertUser(UUID.randomUUID(), user);
        return 1;
    }
}
