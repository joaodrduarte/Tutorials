package tutorial.javaguidesyt.StudentManagementSystem.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import tutorial.javaguidesyt.StudentManagementSystem.entity.Student;

public interface StudentRepository extends JpaRepository<Student,Long> {

}
