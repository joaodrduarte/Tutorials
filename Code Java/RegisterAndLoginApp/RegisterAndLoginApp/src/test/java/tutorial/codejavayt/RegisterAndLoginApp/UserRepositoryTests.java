package tutorial.codejavayt.RegisterAndLoginApp;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class UserRepositoryTests {
    @Autowired
    private UserRepository repo;
    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void testCreateUser(){
       User user = new User();
       user.setEmail("madonna@gmail.com");
       user.setPassword("maddy2022");
       user.setFirstName("Madonna");
       user.setLastName("Rafaela");
       User existUser = repo.save(user);
       entityManager.find(User.class,existUser.getId());
       assertThat(existUser.getEmail()).isEqualTo(user.getEmail());
    }

    @Test
    public void testFindUserByEmail(){
        assertThat(repo.findByEmail("flash@avengers.com")).isNotNull();
    }
}
