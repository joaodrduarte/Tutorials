package com.udemy.sbforswe.springbootforsoftwareengineers.dao;

import com.udemy.sbforswe.springbootforsoftwareengineers.enums.Gender;
import com.udemy.sbforswe.springbootforsoftwareengineers.model.User;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class FakeDataDao implements UserDao{
    private Map<UUID, User> database;

    public FakeDataDao() {
        database = new HashMap<>();
        UUID joeUerUUID = UUID.randomUUID();
        database.put(joeUerUUID, new User(joeUerUUID,"Joe", "Jones", Gender.MALE,22,"joe.jones@gmail.com"));
    }

    @Override
    public List<User> selectAllUsers() {
        return new ArrayList<>(database.values());
    }

    @Override
    public Optional<User> selectUserByUserUUID(UUID userUUID) {
        return Optional.ofNullable(database.get(userUUID));
    }

    @Override
    public int updateUser(User user) {
        database.put(user.getUserUUID(), user);
        return 1;
    }

    @Override
    public int deleteUserByUserUUId(UUID userUUID) {
        database.remove(userUUID);
        return 1;
    }

    @Override
    public int insertUser(UUID userUUID, User user) {
        database.put(userUUID,user);
        return 1;
    }
}
