package raisetech.management.student.service;


import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import raisetech.management.student.data.Student;
import raisetech.management.student.data.StudentCourse;
import raisetech.management.student.domain.StudentDetail;
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

    return studentRepository.search();
  }

  public List<StudentCourse> searchStudentCoursesList() {

    return studentCourseRepository.search();
  }

  @Transactional
  public void registerStudent(StudentDetail studentDetail) {

    // 受講生情報を登録する
    Student student = studentDetail.getStudent();
    studentRepository.registerStudent(student);

    // コース情報を登録する
    for (StudentCourse studentCourse : studentDetail.getStudentCourses()) {
      studentCourse.setStudentId(student.getId());
      studentCourse.setStartDate(LocalDate.now());
      studentCourse.setEndDate(LocalDate.now().plusYears(1));
      studentCourseRepository.registerStudentCourse(studentCourse);
    }
  }

  public StudentDetail searchStudent(int id){
    Student student = studentRepository.searchStudent(id);

    List<StudentCourse> studentCourses = studentCourseRepository.searchStudentCourses(id);

    StudentDetail studentDetail = new StudentDetail();

    studentDetail.setStudent(student);
    studentDetail.setStudentCourses(studentCourses);

    return studentDetail;

  }

  public void updateStudent(StudentDetail studentDetail){
    studentRepository.updateStudent(studentDetail.getStudent());
  }
}