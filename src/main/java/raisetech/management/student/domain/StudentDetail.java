package raisetech.management.student.domain;

import java.util.List;
import lombok.Getter;
import lombok.Setter;
import raisetech.management.student.data.Student;
import raisetech.management.student.data.StudentCourse;


@Getter
@Setter
public class StudentDetail {

  private Student student;
  private List<StudentCourse> studentCourses;

}
