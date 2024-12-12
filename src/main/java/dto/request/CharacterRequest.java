package dto.request;

public record CharacterRequest(
        Integer id,
        String alias,
        String fullname,
        String alignment,
        String abilities,
        String age,
        String team,
        String created,
        String updated
) {}

