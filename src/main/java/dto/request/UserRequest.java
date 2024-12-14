package dto.request;

public record UserRequest(
        Integer id,
        String username,
        String email,
        String gender,
        String birthday,
        String role,
        String created,
        String updated
) {}
