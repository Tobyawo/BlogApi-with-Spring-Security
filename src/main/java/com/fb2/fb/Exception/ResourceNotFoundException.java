package com.fb2.fb.Exception;

import com.fb2.fb.model.request.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {

    private transient ApiResponse apiResponse;


    public ResourceNotFoundException(String message) {
        super(message);
    }

    public ResourceNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public ApiResponse getApiResponse() {
        return apiResponse;
    }
}
