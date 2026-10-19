package raisetech.management.student.data;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Student {


  private String id;
  @NotBlank(message = "氏名は必須です")
  private String name;
  private String furigana;
  private String nickname;
  @Email(message = "メールアドレスの形式が不正です")
  @NotBlank(message = "メールアドレスが必須です")
  private String email;
  private String city;
  @Min(value = 0, message = "年齢は0以上で入力してください。")
  private int age;
  private String gender;
  private String remark;
  private boolean isDeleted;
}
