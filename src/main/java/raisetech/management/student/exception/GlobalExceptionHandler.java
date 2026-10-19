
package raisetech.management.student.exception;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import org.springframework.web.server.ResponseStatusException;

@RestControllerAdvice
public class GlobalExceptionHandler {

  // 存在しない受講生などのHTTPエラーを処理する
  @ExceptionHandler(ResponseStatusException.class)
  public ResponseEntity<String> handleException(
      ResponseStatusException ex) {

    return ResponseEntity.status(ex.getStatusCode())
        .body(ex.getReason());
  }

  // 登録・更新時の入力チェックエラーを処理する
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<String> handleValidation(
      MethodArgumentNotValidException ex) {

    // 最初の入力エラーメッセージを取得する
    String message = ex.getBindingResult()
        .getFieldErrors()
        .stream()
        .findFirst()
        .map(error -> error.getDefaultMessage())
        .orElse("入力内容に誤りがあります。");

    return ResponseEntity.badRequest().body(message);
  }

  // メソッド引数の制約違反を処理する
  @ExceptionHandler(ConstraintViolationException.class)
  public ResponseEntity<String> handleConstraintViolation(
      ConstraintViolationException ex) {

    // 最初の制約違反メッセージを取得する
    String message = ex.getConstraintViolations()
        .stream()
        .findFirst()
        .map(violation -> violation.getMessage())
        .orElse("入力内容に誤りがあります。");

    return ResponseEntity.badRequest().body(message);
  }

  // Spring MVCのメソッド入力チェックエラーを処理する
  @ExceptionHandler(HandlerMethodValidationException.class)
  public ResponseEntity<String> handleMethodValidation(
      HandlerMethodValidationException ex) {

    // 単独検索などの入力エラーを400で返す
    return ResponseEntity.badRequest()
        .body("入力内容に誤りがあります。");
  }
}
