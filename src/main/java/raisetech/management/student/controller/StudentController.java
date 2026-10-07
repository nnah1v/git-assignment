package raisetech.management.student.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import raisetech.management.student.data.Student;
import raisetech.management.student.data.StudentCourse;
import raisetech.management.student.service.StudentService;

@RestController
public class StudentController {

  private StudentService service;

  @Autowired
  public StudentController(StudentService service){
    this.service = service;
  }

  @GetMapping("/studentList")
  public List<Student> getStudentList() {
    return service.searchStudentList();
  }

  @GetMapping("/studentCoursesList")
  public List<StudentCourse> getStudentCoursesList(){
    return service.searchStudentCoursesList();
  }

}
