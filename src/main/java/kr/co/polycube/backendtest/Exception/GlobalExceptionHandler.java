package kr.co.polycube.backendtest.Exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleUserNotFoundException(UserNotFoundException ex) {
        ErrorCode errorCode = ex.getErrorCode();

        Map<String, String> response = new HashMap<>();
        response.put("error", errorCode.getCode());
        response.put("message", errorCode.getMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleBadRequestException(MethodArgumentNotValidException e) {
        Map<String, String> map = new HashMap<>();
        map.put("reason", e.getMessage());
        return ResponseEntity.badRequest().body(map);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleResourceNotFoundException(ResourceNotFoundException e) {
        Map<String, String> map = new HashMap<>();
        map.put("code", e.getErrorCode().getCode());
        map.put("message", e.getErrorCode().getMessage());
        map.put("details", e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(map);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleGenericException(Exception e) {
        Map<String, String> map = new HashMap<>();
        map.put("error", "Internal Server Error");
        map.put("message", e.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(map);
    }

}
