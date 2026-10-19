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

  @NotBlank
  private String furigana;

  @NotBlank
  private String nickname;

  @Email(message = "メールアドレスの形式が不正です")
  @NotBlank(message = "メールアドレスは必須です")
  private String email;

  @NotBlank
  private String city;

  @Min(value = 0, message = "年齢は0以上で入力してください。")
  private int age;

  @NotBlank
  private String gender;

  private String remark;
  private boolean isDeleted;
}
