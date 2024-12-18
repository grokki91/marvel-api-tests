package utils;

public enum ErrorMessages {
    INVALID_CREDENTIALS("Invalid credentials"),
    EMAIL_EXIST("User with EMAIL=%s already exists"),
    USERNAME_EXIST("User with USERNAME=%s already exists");

    private final String message;

    ErrorMessages(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public String getMessage(String arg) {
        return String.format(message, arg);
    }
}
