package raisetech.management.student.repository;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import raisetech.management.student.data.StudentCourse;

/**
 * 受講生のコース情報を扱うRepositoryです。
 * 受講生に紐づくコース情報の検索や登録、更新、削除を行うクラス。
 */
@Mapper
public interface StudentCourseRepository {

  /**
   * 受講生のコース情報の全件検索を行います。
   *
   * @return 受講生コース一覧(全件)
   */
  List<StudentCourse> searchStudentCourseList();

  /**
   * 受講生コース情報を登録します。IDに関しては自動採番を行う。
   *
   * @param studentCourse 受講生コース情報
   */
  void registerStudentCourse(StudentCourse studentCourse);

  /**
   * 受講生IDに紐づく受講生のコース情報を検索をします。
   *
   * @param studentId 受講生ID
   * @return 受講生IDに紐づく受講生のコース情報
   */
  List<StudentCourse> searchStudentCourse(String studentId);


  /**
   * 受講生コース情報のコース名を更新します。
   *
   * @param studentCourse 受講生コース情報
   */
  void updateStudentCourse(StudentCourse studentCourse);

}
