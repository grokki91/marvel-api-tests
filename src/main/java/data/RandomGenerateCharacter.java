package data;

import com.github.javafaker.Faker;
import dto.request.CharacterRequest;

public class RandomGenerateCharacter {
    private static final Faker character = new Faker();

    public static CharacterRequest createCharacter() {
        String[] alignments = {"good", "evil"};

        String alias = character.superhero().name();
        String fullName = character.name().fullName();
        String alignment = alignments[character.random().nextInt(alignments.length)];
        String abilities = character.superhero().power();
        int age = character.number().numberBetween(18, 10000);
        String team = character.superhero().descriptor();

        return new CharacterRequest(alias, fullName, alignment, abilities, age, team);
    }
}
