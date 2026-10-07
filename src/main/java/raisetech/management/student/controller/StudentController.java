package raisetech.management.student.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import raisetech.management.student.controller.converter.StudentConverter;
import raisetech.management.student.data.Student;
import raisetech.management.student.data.StudentCourse;
import raisetech.management.student.domain.StudentDetail;
import raisetech.management.student.service.StudentService;

@RestController
public class StudentController {

  private StudentService service;
  private StudentConverter converter;

  @Autowired
  public StudentController(StudentService service, StudentConverter converter){
    this.service = service;
    this.converter = converter;
  }

  @GetMapping("/studentList")
  public List<StudentDetail> getStudentList() {
    List<Student> students = service.searchStudentList();
    List<StudentCourse> studentCourses = service.searchStudentCoursesList();

    return converter.convertStudentDetails(students, studentCourses);
  }



  @GetMapping("/studentCoursesList")
  public List<StudentCourse> getStudentCoursesList(){
    return service.searchStudentCoursesList();
  }

}
