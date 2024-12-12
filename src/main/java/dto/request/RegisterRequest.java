package dto.request;

public record RegisterRequest(
        String username,
        String password,
        String email,
        String gender,
        String birthday
) {}

