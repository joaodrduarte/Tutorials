package com.udemy.sbforswe.springbootforsoftwareengineers.dao;

import com.udemy.sbforswe.springbootforsoftwareengineers.enums.Gender;
import com.udemy.sbforswe.springbootforsoftwareengineers.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


class FakeDataDaoTest {
    private FakeDataDao fakeDataDao;

    @BeforeEach
    void setUp() {
        fakeDataDao = new FakeDataDao();
    }

    @Test
    void shouldSelectAllUsers() {
        List<User> users = fakeDataDao.selectAllUsers();
        assertThat(users).hasSize(1);
        User user = users.get(0);
        assertUserFields(user);
    }

    private static void assertUserFields(User user) {
        assertThat(user.getAge()).isEqualTo(22);
        assertThat(user.getFirstName()).isEqualTo("Joe");
        assertThat(user.getLastName()).isEqualTo("Jones");
        assertThat(user.getGender()).isEqualTo(Gender.MALE);
        assertThat(user.getEmail()).isEqualTo("joe.jones@gmail.com");
        assertThat(user.getUserUUID()).isNotNull();
    }

    @Test
    void shouldSelectUserByUserUUID() {
        UUID annaUserUUID = UUID.randomUUID();
        User anna = new User(annaUserUUID, "Anna", "Montana", Gender.FEMALE,30, "anna@gmail.com");
        fakeDataDao.insertUser(annaUserUUID,anna);
        assertThat(fakeDataDao.selectAllUsers()).hasSize(2);
        Optional<User> annaOptional = fakeDataDao.selectUserByUserUUID(annaUserUUID);
        assertThat(annaOptional.isPresent()).isTrue();
        assertThat(annaOptional.get()).isEqualToComparingFieldByField(anna);
    }

    @Test
    void shouldNotSelectUserByUserUUID() {
        Optional<User> user = fakeDataDao.selectUserByUserUUID(UUID.randomUUID());
        assertThat(user.isPresent()).isFalse();
    }

    @Test
    void shouldUpdateUser() {
        UUID joeUserUUID = fakeDataDao.selectAllUsers().get(0).getUserUUID();
        User newJoe = new User(joeUserUUID, "Anna", "Montana", Gender.FEMALE,30, "anna@gmail.com");
        fakeDataDao.updateUser(newJoe);
        Optional<User> user = fakeDataDao.selectUserByUserUUID(joeUserUUID);
        assertThat(user.isPresent()).isTrue();
        assertThat(fakeDataDao.selectAllUsers()).hasSize(1);
        assertThat(user.get()).isEqualToComparingFieldByField(newJoe);
    }

    @Test
    void shouldDeleteUserByUserUUId() {
        UUID joeUserUDUID = fakeDataDao.selectAllUsers().get(0).getUserUUID();
        fakeDataDao.deleteUserByUserUUId(joeUserUDUID);
        assertThat(fakeDataDao.selectUserByUserUUID(joeUserUDUID).isPresent()).isFalse();
        assertThat(fakeDataDao.selectAllUsers()).isEmpty();
    }

    @Test
    void shouldInsertUser() {
        UUID userUUId = UUID.randomUUID();
        User user = new User(userUUId, "Anna", "Montana", Gender.FEMALE,30, "anna@gmail.com");
        fakeDataDao.insertUser(userUUId, user);
        List<User> users = fakeDataDao.selectAllUsers();
        assertThat(users).hasSize(2);
        assertThat(fakeDataDao.selectUserByUserUUID(userUUId).get()).isEqualToComparingFieldByField(user);
    }
}