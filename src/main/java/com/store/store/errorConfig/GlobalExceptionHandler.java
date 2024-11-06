package com.store.store.errorConfig;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 *
 * @author fernan
 */
@org.springframework.web.bind.annotation.ControllerAdvice
public class GlobalExceptionHandler {
    // Maneja la excepción de validación de parámetros en los controladores

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

        // Mensaje de error específico
        errorResponse.addFieldError("age", "El campo debe ser un número");

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DuplicateResourceException.class)
    public ResponseEntity<ErrorResponse> handleDuplicateResourceException(DuplicateResourceException ex) {
        ErrorResponse errorResponse = new ErrorResponse("DUPLICATE_ERROR", ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT); // 409 Conflict
    }
}
