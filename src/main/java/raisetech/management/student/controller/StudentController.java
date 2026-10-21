package raisetech.management.student.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import raisetech.management.student.domain.StudentDetail;
import raisetech.management.student.service.StudentService;

/**
 * 受講生の検索や登録、更新のなどを行うREST APIとして受け付けるControllerです。
 */
@Tag(name = "受講生管理", description = "受講生情報の検索・登録・更新を行うAPI")
@Validated
@RestController
public class StudentController {

  private StudentService service;

  /**
   * コンストラクタ
   *
   * @param service 受講生サービス
   */
  @Autowired
  public StudentController(StudentService service) {
    this.service = service;
  }

  /**
   * 受講生詳細の一覧検索です。 全件検索を行うので、条件指定は行いません。
   *
   * @return 受講生詳細一覧(全件)
   */
  @Operation(
      //APIタイトル
      summary = "受講生一覧検索",
      //APIの詳しい説明
      description = "登録されている受講生の詳細情報と受講コースを全件取得します。",
      //APIを識別する名前
      operationId = "getStudentList",
      //Swagger UIでの分類
      tags = {"受講生検索"},
      //HTTPレスポンスの説明
      responses = {@ApiResponse(responseCode = "200", description = "受講生一覧の取得成功"),
          @ApiResponse(responseCode = "500", description = "サーバー内部エラー")})
  @GetMapping("/studentList")
  public List<StudentDetail> getStudentList() {
    return service.searchStudentList();
  }

  /**
   * 受講生詳細の検索です。 IDに紐づく任意の受講生の情報を取得します。
   *
   * @param id 受講生ID
   * @return 受講生詳細
   */
  @Operation(
      //APIのタイトル
      summary = "受講生検索",
      //IDを指定して検索するAPIの説明
      description = "受講生IDを指定して受講生詳細を取得します。",
      //APIを識別する名前
      operationId = "getStudent",
      //APIの分類
      tags = {"受講生検索"},
      //入力パラメーターの説明
      parameters = {
          @Parameter(name = "id", description = "検索対象の受講生ID（数字のみ）", required = true, example = "1")},
      //HTTPレスポンスの説明
      responses = {@ApiResponse(responseCode = "200", description = "受講生詳細の取得成功"),
          @ApiResponse(responseCode = "400", description = "受講生IDの入力値が不正"),
          @ApiResponse(responseCode = "404", description = "指定された受講生が存在しない"),
          @ApiResponse(responseCode = "500", description = "サーバー内部エラー")})
  @GetMapping("/student/{id}")
  public StudentDetail getStudent(@PathVariable @NotBlank @Pattern(regexp = "^\\d+$") String id) {
    return service.searchStudent(id);
  }

  /**
   * 受講生詳細の登録を行います。
   *
   * @param studentDetail 受講生詳細
   * @return 実行結果
   */
  @Operation(
      //APIのタイトル
      summary = "受講生登録",
      //登録APIの詳しい説明
      description = "受講生情報と受講コース情報を新規登録します。受講生IDと受講コースIDは自動採番されます。受講開始日は登録日、終了日は登録日の1年後に自動設定されます。",
      //APIを識別する名前
      operationId = "registerStudent",
      //APIの分類
      tags = {"受講生登録・更新"},

      //リクエスト本文の説明
      requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
          description = "登録する受講生情報と受講コース情報", required = true, content = @Content(schema = @Schema(implementation = StudentDetail.class))),

      //HTTPレスポンスの説明
      responses = {@ApiResponse(responseCode = "201", description = "受講生の登録成功"),
          @ApiResponse(responseCode = "400", description = "登録内容の入力値が不正"),
          @ApiResponse(responseCode = "500", description = "サーバー内部エラー")})
  @PostMapping("/registerStudent")
  public ResponseEntity<StudentDetail> registerStudent(
      @RequestBody @Valid StudentDetail studentDetail) {
    StudentDetail respomseStudentDetail = service.registerStudent(studentDetail);
    // 受講生の登録成功時に201 Createdを返す
    return ResponseEntity.status(201).body(respomseStudentDetail);
  }


  /**
   * 受講生詳細の更新を行います。 キャンセルフラグの更新もここで行います。(論理削除)
   *
   * @param studentDetail 受講生詳細
   * @return 実行結果
   */
  @Operation(
      //APIのタイトル
      summary = "受講生更新",
      //更新処理と論理削除の説明
      description = "既存の受講生情報と受講コース情報を更新します。キャンセルフラグの変更による論理削除にも対応します。",
      //APIを識別する名前
      operationId = "updateStudent",
      //APIの分類
      tags = {"受講生登録・更新"},
      //リクエスト本文の説明
      requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
          description = "更新する受講生情報と受講コース情報", required = true, content = @Content(schema = @Schema(implementation = StudentDetail.class))),
      //HTTPレスポンスの説明
      responses = {
          @ApiResponse(responseCode = "200", description = "受講生詳細の取得成功"),
          @ApiResponse(responseCode = "400", description = "受講生IDの入力値が不正"),
          @ApiResponse(responseCode = "404", description = "対象の受講生が存在しません。"),
          @ApiResponse(responseCode = "500", description = "サーバー内部エラー")
      })

  @PutMapping("/updateStudent")
  public ResponseEntity<String> updateStudent(@RequestBody @Valid StudentDetail studentDetail) {
    service.updateStudent(studentDetail);
    return ResponseEntity.ok("更新処理が成功しました。");
  }
}