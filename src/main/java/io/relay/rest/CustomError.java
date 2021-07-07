package io.relay.rest;

import java.time.Instant;
import org.springframework.http.HttpStatus;

public class CustomError {

    private HttpStatus status;
    private Instant timestamp;
    private String message;

    private CustomError() {
        this.timestamp = Instant.now();
    }

    public CustomError(HttpStatus status, String message) {
        this();
        this.status = status;
        this.message = message;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
