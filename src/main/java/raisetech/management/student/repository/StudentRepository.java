package raisetech.management.student.repository;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import raisetech.management.student.data.Student;

/**
 * 受講生情報を扱うRepositoryです。
 * 受講生情報の検索や登録、更新、削除を行うクラスです。
 */
@Mapper
public interface StudentRepository {

  /**
   * 受講生の全件検索を行います。
   *
   * @return 受講生一覧(全件)
   */
  List<Student> search();

  /**
   * 受講生を新規登録します。IDに関しては自動採番を行う。
   *
   * @param student 受講生
   */
  void registerStudent(Student student);

  /**
   * 受講生の検索を行います。
   *
   * @param id 受講生ID
   * @return 受講生
   */
  Student searchStudent(String id);

  /**
   * 受講生を更新します。
   *
   * @param student 受講生
   * @return
   */
  int updateStudent(Student student);

}

