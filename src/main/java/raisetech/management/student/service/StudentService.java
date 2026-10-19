package raisetech.management.student.service;


import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import raisetech.management.student.controller.converter.StudentConverter;
import raisetech.management.student.data.Student;
import raisetech.management.student.data.StudentCourse;
import raisetech.management.student.domain.StudentDetail;
import raisetech.management.student.repository.StudentCourseRepository;
import raisetech.management.student.repository.StudentRepository;

/**
 * 受講生情報を取り扱うサービスです。
 * 受講生の検索や登録・更新処理を行います。
 */
@Service
public class StudentService {

  private StudentRepository studentRepository;
  private StudentCourseRepository studentCourseRepository;
  private StudentConverter converter;

  @Autowired
  public StudentService(StudentRepository studentRepository,
      StudentCourseRepository studentCourseRepository, StudentConverter converter) {
    this.studentRepository = studentRepository;
    this.studentCourseRepository = studentCourseRepository;
    this.converter = converter;
  }

  /**
   * 受講生詳細の一覧検索を行います。
   * 全件検索を行うので、条件指定は行いません。
   *
   * @return 受講生詳細一覧(全件)
   */
  public List<StudentDetail> searchStudentList() {
    List<Student> studentList = studentRepository.search();
    List<StudentCourse> studentCourseList = studentCourseRepository.searchStudentCorseList();
    return converter.convertStudentDetails(studentList, studentCourseList);
  }

  /**
   * 受講生詳細の登録を行います。
   * 受講生と受講生コース情報を個別に登録し、受講生コース情報には受講生情報を紐づける値とコース開始日、コース終了日を設定します。
   *
   * @param studentDetail 受講生詳細
   * @return 登録情報を付与した受講生詳細
   */
  @Transactional
  public StudentDetail registerStudent(StudentDetail studentDetail) {

    // 受講生情報を登録する
    Student student = studentDetail.getStudent();
    studentRepository.registerStudent(student);

    // コース情報を登録する
    studentDetail.getStudentCourseList().forEach(studentCourse -> {
      initStudentCourse(studentCourse, student);
      studentCourseRepository.registerStudentCourse(studentCourse);
    });
    return studentDetail;
  }

  /**
   * 受講生コース情報を登録する際の初期情報を設定する。
   *
   * @param studentCourse 受講生コース情報
   * @param student 受講生
   */
  private static void initStudentCourse(StudentCourse studentCourse, Student student) {
    LocalDate now = LocalDate.now();

    studentCourse.setStudentId(student.getId());
    studentCourse.setStartDate(now);
    studentCourse.setEndDate(now.plusYears(1));
  }

  /**
   * 受講生検索です。
   * IDに紐づく受講生情報を取得したあと、その受講生に紐づく受講生コース情報を取得して設定します。
   *
   * @param id 受講生ID
   * @return 受講生
   */
  public StudentDetail searchStudent(String id){
    Student student = studentRepository.searchStudent(id);
    List<StudentCourse> studentCourse = studentCourseRepository.searchStudentCourse(student.getId());
    return new StudentDetail(student, studentCourse);

  }

  /**
   * 受講生詳細の更新を行います。
   * 受講生と受講生コース情報をそれぞれ更新します。
   *
   * @param studentDetail 受講生詳細
   */
  @Transactional
  public void updateStudent(StudentDetail studentDetail) {
    studentRepository.updateStudent(studentDetail.getStudent());
    studentDetail.getStudentCourseList()
        .forEach(studentCourse -> studentCourseRepository.updateStudentCourse(studentCourse));
  }
}