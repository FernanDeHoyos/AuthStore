/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.store.store.errorConfig;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author fernan
 */
public class ErrorResponse {
     private String status;
    private String message;
    private List<FieldError> fieldErrors;

    public ErrorResponse(String status, String message) {
        this.status = status;
        this.message = message;
        this.fieldErrors = new ArrayList<>();
    }

    public void addFieldError(String field, String errorMessage) {
        this.fieldErrors.add(new FieldError(field, errorMessage));
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<FieldError> getFieldErrors() {
        return fieldErrors;
    }

    public void setFieldErrors(List<FieldError> fieldErrors) {
        this.fieldErrors = fieldErrors;
    }

    public static class FieldError {
        private String field;
        private String errorMessage;

        public FieldError(String field, String errorMessage) {
            this.field = field;
            this.errorMessage = errorMessage;
        }

        public String getField() {
            return field;
        }

        public void setField(String field) {
            this.field = field;
        }

        public String getErrorMessage() {
            return errorMessage;
        }

        public void setErrorMessage(String errorMessage) {
            this.errorMessage = errorMessage;
        }
    }
}
