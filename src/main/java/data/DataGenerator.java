package data;

import com.github.javafaker.Faker;
import dto.request.RegisterRequest;

import java.text.SimpleDateFormat;

public class DataGenerator {
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private static final Faker user = new Faker();

    public static RegisterRequest createUser() {
        String username = user.name().username();
        String password = user.internet().password();
        String gender = user.demographic().sex();
        String email = user.internet().emailAddress();
        String birthday = dateFormat.format(user.date().birthday(1, 90));

        return new RegisterRequest(username, password, email, gender, birthday);
    }

    public static RegisterRequest createUserExceptEmail(String email) {
        String username = user.name().username();
        String password = user.internet().password();
        String gender = user.demographic().sex();
        String birthday = dateFormat.format(user.date().birthday(1, 90));

        return new RegisterRequest(username, password, email, gender, birthday);
    }

    public static RegisterRequest createUserExceptUsername(String username) {
        String password = user.internet().password();
        String gender = user.demographic().sex();
        String birthday = dateFormat.format(user.date().birthday(1, 90));
        String email = user.internet().emailAddress();

        return new RegisterRequest(username, password, email, gender, birthday);
    }
}
