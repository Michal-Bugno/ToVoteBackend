package net.tovote.responses;

import net.tovote.entities.User;
import org.springframework.http.ResponseEntity;

public class ErrorResponse {

    private int error;
    private String message;

    public ErrorResponse(int error, String message) {
        this.error = error;
        this.message = message;
    }

    public int getError() {
        return error;
    }

    public void setError(int error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
