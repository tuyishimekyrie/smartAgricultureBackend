package com.sensor.agri.smartagriculturebackend.security;

import lombok.*;

@Getter
@Setter
public class ApiResponse<T> {
    private String status;
    private String message;
    private String errorCode;
    private T data;
    private String details;

    //    // Constructors
    public ApiResponse(String status, String message, T data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }
    //
    public ApiResponse(String status, String message, String errorCode, String details) {
        this.status = status;
        this.message = message;
        this.errorCode = errorCode;
        this.details = details;
    }

    // Getters and setters
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public String getErrorCode() { return errorCode; }
    public void setErrorCode(String errorCode) { this.errorCode = errorCode; }

    public T getData() { return data; }
    public void setData(T data) { this.data = data; }

    public String getDetails() { return details; }
    public void setDetails(String details) { this.details = details; }

    // Helper methods for success and failure responses
    public static <T> ApiResponse<T> success(String message, T data) {
        return new ApiResponse<>("success", message, data);
    }

    public static <T> ApiResponse<T> failure(String message, String errorCode, String details) {
        return new ApiResponse<>("failure", message, errorCode, details);
    }
}
