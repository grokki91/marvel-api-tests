package dto.request;

public record CharacterRequest(
        String alias,
        String fullname,
        String alignment,
        String abilities,
        int age,
        String team
) {}

