package raisetech.management.student.service;


import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import raisetech.management.student.data.Student;
import raisetech.management.student.data.StudentCourse;
import raisetech.management.student.repository.StudentCourseRepository;
import raisetech.management.student.repository.StudentRepository;

@Service
public class StudentService {

  private StudentRepository studentRepository;
  private StudentCourseRepository studentCourseRepository;

  @Autowired
  public StudentService(StudentRepository studentRepository,
      StudentCourseRepository studentCourseRepository) {
    this.studentRepository = studentRepository;
    this.studentCourseRepository = studentCourseRepository;
  }


  public List<Student> searchStudentList() {

    //絞り込みをする。年齢が20-25の人のみを抽出する。
    //抽出したリストをコントローラーに返す。
    List<Student> students = studentRepository.search();
    List<Student> result = new ArrayList<>();
    for (Student student : students) {
      if (student.getAge() >= 20 && student.getAge() <= 25) {
        result.add(student);
      }
    }
    return result;
  }

  public List<StudentCourse> searchStudentCoursesList() {

    //絞り込み検索で「Java基礎」のコース情報のみを抽出する。
    //抽出リストをコントローラーに返す。
    List<StudentCourse> courses = studentCourseRepository.search();
    List<StudentCourse> result = new ArrayList<>();
    for (StudentCourse studentCourse : courses) {
      if ("Java基礎".equals(studentCourse.getCourseName())) {
        result.add(studentCourse);
      }
    }

    return result;

  }
}
