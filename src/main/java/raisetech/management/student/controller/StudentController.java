package raisetech.management.student.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import raisetech.management.student.controller.converter.StudentConverter;
import raisetech.management.student.data.Student;
import raisetech.management.student.data.StudentCourse;
import raisetech.management.student.domain.StudentDetail;
import raisetech.management.student.service.StudentService;

@Controller
public class StudentController {

  private StudentService service;
  private StudentConverter converter;

  @Autowired
  public StudentController(StudentService service, StudentConverter converter){
    this.service = service;
    this.converter = converter;
  }

  @GetMapping("/studentList")
  public String getStudentList(Model model) {
    List<Student> students = service.searchStudentList();
    List<StudentCourse> studentCourses = service.searchStudentCoursesList();

    model.addAttribute("studentList", converter.convertStudentDetails(students, studentCourses));
    return "studentList";
  }



  @GetMapping("/studentCoursesList")
  public List<StudentCourse> getStudentCoursesList(){
    return service.searchStudentCoursesList();
  }

}
