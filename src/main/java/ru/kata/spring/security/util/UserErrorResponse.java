package ru.kata.spring.security.util;
// UserErrorResponse представляет объект, используемый для представления ошибки, связанной с пользователем.
// возврата информации об ошибках клиенту
public class UserErrorResponse {
    private String message;
    private long timestamp;

    public UserErrorResponse(String message, long timestamp) {
        this.message = message;
        this.timestamp = timestamp;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
