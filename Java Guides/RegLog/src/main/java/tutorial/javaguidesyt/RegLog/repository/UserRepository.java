package tutorial.javaguidesyt.RegLog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tutorial.javaguidesyt.RegLog.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
