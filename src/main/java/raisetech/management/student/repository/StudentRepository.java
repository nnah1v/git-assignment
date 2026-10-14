package raisetech.management.student.repository;

import java.util.List;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import raisetech.management.student.data.Student;

/**
 * 受講生情報を扱うリポジトリ。
 *
 * 受講生情報の検索や登録、更新、削除を行うクラスです。
 */
@Mapper
public interface StudentRepository {

  @Select("SELECT * FROM students WHERE is_deleted = false")
  List<Student> search();

  @Insert("INSERT INTO students (name,furigana,nickname,email,city,age,gender,remark,is_deleted)"
      + "VALUES (#{name}, #{furigana}, #{nickname}, #{email}, #{city}, #{age}, #{gender}, #{remark}, false)")
  @Options(useGeneratedKeys = true, keyProperty = "id")
  void registerStudent(Student student);

 @Select("SELECT * FROM students WHERE id = #{id}")
  Student searchStudent(String id);

  @Update("UPDATE students SET name = #{name}, furigana = #{furigana}, nickname = #{nickname},"
      + " email = #{email}, city = #{city}, age = #{age}, gender = #{gender}, remark = #{remark}, is_deleted = #{deleted} WHERE id = #{id}")
  void updateStudent(Student student);

}

