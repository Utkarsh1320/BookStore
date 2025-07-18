//package com.example.bookstore.exception;
//
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.HttpStatusCode;
//import org.springframework.http.ResponseEntity;
//import org.springframework.validation.FieldError;
//import org.springframework.web.bind.MethodArgumentNotValidException;
//import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.bind.annotation.ExceptionHandler; // Keep this import for custom handlers if needed
//import org.springframework.web.context.request.WebRequest;
//import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler; // Important import
//
//import java.time.LocalDateTime;
//import java.util.LinkedHashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.stream.Collectors;
//
//@ControllerAdvice // This annotation makes this class a global exception handler
//public class GlobalExceptionHandler extends ResponseEntityExceptionHandler { // Key change: Extend this class!
//
//    // This method overrides the default handling for @Valid failures (400 Bad Request)
//    @Override // Important: Use @Override when extending ResponseEntityExceptionHandler
//    protected ResponseEntity<Object> handleMethodArgumentNotValid(
//            MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
//
//        Map<String, Object> body = new LinkedHashMap<>();
//        body.put("timestamp", LocalDateTime.now());
//        body.put("status", status.value());
//        body.put("error", "Bad Request");
//        body.put("path", request.getDescription(false).replace("uri=", ""));
//
//        // Get all validation errors
//        List<String> errors = ex.getBindingResult()
//                .getFieldErrors()
//                .stream()
//                .map(error -> error.getField() + ": " + error.getDefaultMessage()) // Format: field: message
//                .collect(Collectors.toList());
//
//        body.put("errors", errors); // Add the list of specific field errors to the response
//
//        return new ResponseEntity<>(body, headers, status); // Use inherited headers and status
//    }
//
//    // You can add more specific @ExceptionHandler methods here for custom exceptions.
//    // For example, if you had a BookNotFoundException:
//    // @ExceptionHandler(BookNotFoundException.class)
//    // public ResponseEntity<Object> handleBookNotFoundException(BookNotFoundException ex, WebRequest request) {
//    //     Map<String, Object> body = new LinkedHashMap<>();
//    //     body.put("timestamp", LocalDateTime.now());
//    //     body.put("status", HttpStatus.NOT_FOUND.value());
//    //     body.put("error", "Not Found");
//    //     body.put("message", ex.getMessage());
//    //     body.put("path", request.getDescription(false).replace("uri=", ""));
//    //     return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
//    // }
//
//    // IMPORTANT: Avoid a generic @ExceptionHandler(Exception.class) that might be too broad
//    // and interfere with Springdoc's internal processes. If you must have one, ensure it
//    // is very robust and logs details without necessarily exposing them to the client.
//}