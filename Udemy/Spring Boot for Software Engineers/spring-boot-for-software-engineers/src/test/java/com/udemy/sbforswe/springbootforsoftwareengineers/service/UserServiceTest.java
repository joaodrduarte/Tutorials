package com.udemy.sbforswe.springbootforsoftwareengineers.service;

import com.google.common.collect.ImmutableList;
import com.udemy.sbforswe.springbootforsoftwareengineers.dao.FakeDataDao;
import com.udemy.sbforswe.springbootforsoftwareengineers.enums.Gender;
import com.udemy.sbforswe.springbootforsoftwareengineers.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

class UserServiceTest {

    @Mock
    private FakeDataDao fakeDataDao;
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        userService = new UserService(fakeDataDao);
    }

    @Test
    void shouldGetAllUsers() {
        UUID annaUserUUID = UUID.randomUUID();
        User anna = new User(annaUserUUID, "Anna", "Montana", Gender.FEMALE,30, "anna@gmail.com");
        ImmutableList<User> users = new ImmutableList.Builder<User>().add(anna).build();
        given(fakeDataDao.selectAllUsers()).willReturn(users);
        List<User> allUsers = userService.getAllUsers();
        assertThat(allUsers).hasSize(1);
        User user = allUsers.get(0);
        assertUserFields(user);
    }

    private static void assertUserFields(User user) {
        assertThat(user.getAge()).isEqualTo(30);
        assertThat(user.getFirstName()).isEqualTo("Anna");
        assertThat(user.getLastName()).isEqualTo("Montana");
        assertThat(user.getGender()).isEqualTo(Gender.FEMALE);
        assertThat(user.getEmail()).isEqualTo("anna@gmail.com");
        assertThat(user.getUserUUID()).isNotNull();
    }

    @Test
    void shouldGetUser() {
        UUID annaUserUUID = UUID.randomUUID();
        User anna = new User(annaUserUUID, "Anna", "Montana", Gender.FEMALE,30, "anna@gmail.com");
        given(fakeDataDao.selectUserByUserUUID(annaUserUUID)).willReturn(Optional.of(anna));
        Optional<User> userOptional = userService.getUser(annaUserUUID);
        assertThat(userOptional.isPresent()).isTrue();
        User user = userOptional.get();
        assertUserFields(user);
    }

    @Test
    void updateUser() {
        UUID annaUserUUID = UUID.randomUUID();
        User anna = new User(annaUserUUID, "Anna", "Montana", Gender.FEMALE,30, "anna@gmail.com");
        given(fakeDataDao.selectUserByUserUUID(annaUserUUID)).willReturn(Optional.of(anna));
        given(fakeDataDao.updateUser(anna)).willReturn(1);
        ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);

        int updateResult = userService.updateUser(anna);
        verify(fakeDataDao).selectUserByUserUUID(annaUserUUID);
        verify(fakeDataDao).updateUser(captor.capture());
        User user = captor.getValue();
        assertUserFields(user);
        assertThat(updateResult).isEqualTo(1);
    }

    @Test
    void removeUser() {
    }

    @Test
    void insertUser() {
    }
}