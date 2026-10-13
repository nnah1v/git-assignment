package raisetech.management.student.repository;

import java.util.List;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import raisetech.management.student.data.StudentCourse;

/**
 * 受講生のコース情報を扱うリポジトリ。
 *
 * 受講生に紐づくコース情報の検索や登録、更新、削除を行うクラス。
 */
@Mapper
public interface StudentCourseRepository {
  
  @Select("SELECT * FROM students_courses")
  List<StudentCourse> search();

  @Insert("INSERT INTO students_courses (student_id, course_name, start_date, end_date) "
      + "VALUES (#{studentId}, #{courseName}, #{startDate}, #{endDate})")
  @Options(useGeneratedKeys = true, keyProperty = "id")
  void registerStudentCourse(StudentCourse studentCourse);



}
