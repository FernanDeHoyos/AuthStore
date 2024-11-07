package com.store.store.errorConfig;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 *
 * @author fernan
 */
// Maneja la excepción de validación de parámetros en los controladores
@org.springframework.web.bind.annotation.ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        // Obtener el resultado de la validación
        BindingResult bindingResult = ex.getBindingResult();
        // Crear una respuesta de error personalizada
        ErrorResponse errorResponse = new ErrorResponse("VALIDATION_ERROR", "Campos no válidos");
        // Recorrer las violaciones y agregar mensajes de error al response
        for (FieldError violation : bindingResult.getFieldErrors()) {
            String field = violation.getField(); // El campo con error
            String message = violation.getDefaultMessage(); // El mensaje de validación
            errorResponse.addFieldError(field, message);
        }
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex) {
        // Crear respuesta de error personalizada
        ErrorResponse errorResponse = new ErrorResponse("VALIDATION_ERROR", "Error de formato de entrada");
        // Obtener el mensaje específico de la excepción para diagnosticar el error
        String specificMessage = ex.getMessage();
        System.out.println("Mensaje de excepción: " + specificMessage); // Para ver el mensaje completo en la consola
        // Verificar si el mensaje contiene información sobre el campo específico
        if (specificMessage != null && specificMessage.contains("age")) {
            errorResponse.addFieldError("age", "El campo 'age' debe ser un número válido.");
        } else {
            errorResponse.addFieldError("general", "Verifique el formato de los datos ingresados.");
        }
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DuplicateResourceException.class)
    public ResponseEntity<ErrorResponse> handleDuplicateResourceException(DuplicateResourceException ex) {
        ErrorResponse errorResponse = new ErrorResponse("DUPLICATE_ERROR", ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT); // 409 Conflict
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorResponse> handleBadCredentialsException(BadCredentialsException ex) {
        ErrorResponse errorResponse = new ErrorResponse("AUTHENTICATION_ERROR", "Credenciales invalidas");
        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT); // 409 Conflict
    }
}
