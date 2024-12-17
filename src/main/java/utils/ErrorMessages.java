package utils;

import java.util.Objects;

public enum ErrorMessages {
    INVALID_CREDENTIALS("Invalid credentials"),
    USERNAME_EXIST("User with email=%s already exist");

    private final String message;

    ErrorMessages(String message) {
        this.message = message;
    }

    public String getMessage(Objects... arg) {
        return String.format(message, arg);
    }
}
