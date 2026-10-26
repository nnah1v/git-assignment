package raisetech.management.student.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import raisetech.management.student.controller.converter.StudentConverter;
import raisetech.management.student.data.Student;
import raisetech.management.student.data.StudentCourse;
import raisetech.management.student.domain.StudentDetail;
import raisetech.management.student.repository.StudentCourseRepository;
import raisetech.management.student.repository.StudentRepository;

@ExtendWith(MockitoExtension.class)
class StudentServiceTest {

  @Mock
  private StudentRepository studentRepository;

  @Mock
  private StudentCourseRepository studentCourseRepository;

  @Mock
  private StudentConverter converter;

  private StudentService sut;

  @BeforeEach
  void before() {
    sut = new StudentService(studentRepository, studentCourseRepository, converter);

  }


  @Test
  void 受講生詳細の一覧検索_リポジトリとコンバーターの処理が適切に呼び出せていること() {

    // 事前情報
    List<Student> studentList = new ArrayList<>();
    List<StudentCourse> studentCourseList = new ArrayList<>();
    when(studentRepository.search()).thenReturn(studentList);
    when(studentCourseRepository.searchStudentCourseList()).thenReturn(studentCourseList);

    // 実行
    sut.searchStudentList();

    // 検証
    verify(studentRepository, times(1)).search();
    verify(studentCourseRepository, times(1)).searchStudentCourseList();
    verify(converter, times(1)).convertStudentDetails(studentList, studentCourseList);


  }


  @Test
  void 受講生詳細の登録_受講生とコース情報が適切に登録されていること() {

    // 事前情報
    // 受講生情報を1件作成
    Student student = new Student();

    // 受講生コース情報を1件作成する
    StudentCourse studentCourse = new StudentCourse();

    // 受講生情報と受講生コース情報をまとめる
    StudentDetail studentDetail = new StudentDetail();
    studentDetail.setStudent(student);
    studentDetail.setStudentCourseList(List.of(studentCourse));

    // 実行
    // 受講生詳細の登録処理を呼び出す
    StudentDetail actual = sut.registerStudent(studentDetail);

    // 検証
    // 登録処理に渡した受講生詳細がそのまま返されること
    assertSame(studentDetail, actual);

    // 受講生登録処理が1回呼ばれたこと
    verify(studentRepository, times(1)).registerStudent(student);

    // コース登録処理が1回呼ばれたこと
    verify(studentCourseRepository, times(1)).registerStudentCourse(studentCourse);


  }

  @Test
  void 受講生詳細の登録_受講生IDとコース日付が正しく設定されること() {

    // 事前情報
    // 登録対象の受講生情報を作成し、テストIDを設定
    Student student = new Student();
    student.setId("1");

    // 登録対象のコース情報を作成し、受講生詳細に設定
    StudentCourse studentCourse = new StudentCourse();
    StudentDetail studentDetail = new StudentDetail(student, List.of(studentCourse));

    // 実行
    // 登録処理の実行前後の日付を取得する
    LocalDate before = LocalDate.now();
    sut.registerStudent(studentDetail);
    LocalDate after = LocalDate.now();

    // 検証
    // コース情報に受講生IDが設定されること
    assertEquals(student.getId(), studentCourse.getStudentId());

    // コース開始日が登録処理の実行日であること
    assertFalse(studentCourse.getStartDate().isBefore(before));
    assertFalse(studentCourse.getStartDate().isAfter(after));

    // コース終了日が開始日の1年後であること
    assertEquals(studentCourse.getStartDate().plusYears(1), studentCourse.getEndDate());

    // Repositoryの登録処理がそれぞれ1回呼ばれること
    verify(studentRepository, times(1)).registerStudent(student);
    verify(studentCourseRepository, times(1)).registerStudentCourse(studentCourse);
  }

  @Test
  void 受講生検索_指定IDの受講生とコース情報を取得できること() {
    // 事前情報
    // 検索対象の受講生情報を作成し、テスト用IDを設定
    Student student = new Student();
    student.setId("1");

    // 検索対象のコース情報を作成し、リストに設定
    StudentCourse studentCourse = new StudentCourse();
    List<StudentCourse> courseList = List.of(studentCourse);

    //Repositoryが返す検索結果を設定
    when(studentRepository.searchStudent("1")).thenReturn(student);
    when(studentCourseRepository.searchStudentCourse("1")).thenReturn(courseList);

    // 実行
    // 指定IDの受講生詳細を検索する
    StudentDetail actual = sut.searchStudent("1");

    // 検証
    // 受講生とコースが正しく返ること
    assertSame(student, actual.getStudent());
    assertEquals(courseList, actual.getStudentCourseList());

    // Repositoryの検索処理がそれぞれ1回呼ばれること
    verify(studentRepository, times(1)).searchStudent("1");
    verify(studentCourseRepository, times(1)).searchStudentCourse("1");
  }

  @Test
  void 受講生検索_存在しないIDの場合404エラーになること() {

    // 事前情報
    // 指定IDの受講生が存在しない検索結果を設定
    when(studentRepository.searchStudent("999")).thenReturn(null);

    // 実行
    // 存在しないIDで検索し、発生した例外を取得する
    ResponseStatusException exception = assertThrows(ResponseStatusException.class,
        () -> sut.searchStudent("999"));

    // 検証
    // 発生した例外のHTTPステータスが404であること
    assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());

    // 受講生が存在しない場合、コース検索が呼ばれないこと
    verifyNoInteractions(studentCourseRepository);
  }

  @Test
  void 受講生詳細の更新_受講生と受講生コース情報をそれぞれ更新() {

    // 事前情報
    // 更新対象の受講生情報を作成
    Student student = new Student();

    // 更新対象の受講生コースを作成
    StudentCourse studentCourse = new StudentCourse();

    // 受講生情報とコース情報を受講生詳細に設定
    StudentDetail studentDetail = new StudentDetail();
    studentDetail.setStudent(student);
    studentDetail.setStudentCourseList(List.of(studentCourse));

    // 実行
    // 受講生詳細の更新処理を呼び出す
    sut.updateStudent(studentDetail);

    // 検証
    // 受講生情報の更新が1回呼ばれること
    verify(studentRepository, times(1)).updateStudent(student);

    // コース情報の更新が1回呼ばれること
    verify(studentCourseRepository, times(1)).updateStudentCourse(studentCourse);


  }

}