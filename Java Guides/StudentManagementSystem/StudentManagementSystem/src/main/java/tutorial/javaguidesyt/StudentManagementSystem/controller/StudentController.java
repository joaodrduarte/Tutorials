package tutorial.javaguidesyt.StudentManagementSystem.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import tutorial.javaguidesyt.StudentManagementSystem.entity.Student;
import tutorial.javaguidesyt.StudentManagementSystem.service.StudentService;

@Controller
public class StudentController {
    private StudentService studentService;

    public StudentController(StudentService studentService) {
        super();
        this.studentService = studentService;
    }

    //Handler Method to handle list students and return mode and view
    @GetMapping("/students")
    public String listStudents(Model model){
        model.addAttribute("students",studentService.getAllStudents());
        System.out.println("StudentController @ listStudents :: /students");
        return "students";
    }

    @GetMapping("/students/new")
    public String createStudentForm(Model model){
        //create student object to hold student form data
        Student student = new Student();
        model.addAttribute("student", student);
        System.out.println("StudentController @ createStudentForm :: /create_student");
        return "create_student";
    }

    @PostMapping("/students")
    public String saveStudent(@ModelAttribute("student") Student student){
        studentService.saveStudent(student);
        System.out.println("StudentController @ saveStudent :: /redirect:/students");
        return "redirect:/students";
    }

    @GetMapping("/students/edit/{id}")
    public String editStudentForm(@PathVariable Long id, Model model){
        model.addAttribute("student",studentService.getStudentById(id));
        System.out.println("StudentController @ editStudentForm :: /edit_student");
        return "edit_student";
    }

    @PostMapping("/students/{id}")
    public String updateStudent(@PathVariable Long id, @ModelAttribute("student") Student student, Model model){
        //get student from database by id
        Student existingStudent = studentService.getStudentById(id);
        existingStudent.setId(student.getId());
        existingStudent.setFirstName(student.getFirstName());
        existingStudent.setLastName(student.getLastName());
        existingStudent.setEmail(student.getEmail());
        //save updated student object
        studentService.updateStudent(existingStudent);
        System.out.println("StudentController @ updateStudent:: /redirect:/students");
        return "redirect:/students";
    }

    //handler method to handle delete student request
    @GetMapping("/students/{id}")
    public String deleteStudent(@PathVariable Long id){
        studentService.deleteStudentById(id);
        System.out.println("StudentController @ deleteStudent :: /redirect:/students");
        return "redirect:/students";
    }
}
