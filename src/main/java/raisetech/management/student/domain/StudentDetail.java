package raisetech.management.student.domain;

import jakarta.validation.Valid;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import raisetech.management.student.data.Student;
import raisetech.management.student.data.StudentCourse;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StudentDetail {

  @Valid
  private Student student;

  private List<@Valid StudentCourse> studentCourseList;

}
