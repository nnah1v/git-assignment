package raisetech.management.student.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(ResponseStatusException.class)
  public ResponseEntity<String> handleException(ResponseStatusException ex){

    return ResponseEntity.status(ex.getStatusCode()).body(ex.getReason());
  }


  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<String> handleValidation(
      MethodArgumentNotValidException ex) {

    // 入力チェックで検出した最初のエラーメッセージを取得
    String message = ex.getBindingResult()
        .getFieldErrors()
        .stream()
        .findFirst()
        .map(error -> error.getDefaultMessage())
        .orElse("入力内容に誤りがあります。");

    // Postmanに400とエラーメッセージを返す
    return ResponseEntity.badRequest().body(message);
  }

}
