package utils;

public enum ErrorMessages {
    INVALID_CREDENTIALS("Invalid credentials"),
    EMAIL_EXIST("User with EMAIL=%s already exists"),
    USERNAME_EXIST("User with USERNAME=%s already exists");

    private String message;

    ErrorMessages(String message) {
        this.message = message;
    }

    public String getMessage() {
        return String.format(message);
    }

    public String getMessage(String arg) {
        return String.format(message, arg);
    }
}
