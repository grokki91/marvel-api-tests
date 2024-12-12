package data;

import lombok.Data;

@Data
public class AuthData {
    private String username;
    private String password;
    private String confirmPassword;
    private String email;
    private String Gender;
    private String birthday;
}
