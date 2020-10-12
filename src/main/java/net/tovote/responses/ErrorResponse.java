package net.tovote.responses;

import net.tovote.entities.User;
import org.springframework.http.ResponseEntity;

import java.util.Date;

public class ErrorResponse {

    private int error;
    private String message;
    private long timeStamp;

    public ErrorResponse(int error, String message) {
        this.error = error;
        this.message = message;
        this.timeStamp = System.currentTimeMillis();
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

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }
}
