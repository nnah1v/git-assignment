
package raisetech.management.student;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import raisetech.management.student.data.Student;
import raisetech.management.student.data.StudentCourse;
import raisetech.management.student.repository.StudentCourseRepository;
import raisetech.management.student.repository.StudentRepository;

@OpenAPIDefinition(info = @Info(title = "受講生管理システム", version = "1.0.0", description = "受講生情報と受講生コース情報の検索・登録・更新を行うAPIです。"))
@SpringBootApplication
public class Application {


	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);

	}
}
