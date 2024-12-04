package server.main;

import java.util.Random;

public class GameCreator {

    private final int ID_LENGTH = 5;
    private static final String VALID_CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

    public GameID createGameID() {
        StringBuilder id = new StringBuilder();
        Random random = new Random();
        for(int i = 0; i < ID_LENGTH; i++) {
            int randomCharIndex = random.nextInt(VALID_CHARACTERS.length());
            id.append(VALID_CHARACTERS.charAt(randomCharIndex));
        }
        return new GameID(id.toString());
    }
}
