package server.main;

import java.util.HashMap;
import java.util.Map;

public class GamesDatabase {
    private Map<GameID, Game> games;

    public GamesDatabase() {
        games = new HashMap<>();
    }

    public void add(GameID gameID, Game game) {
        games.put(gameID, game);
    }

    public Game getGame(GameID gameID) {
        return games.get(gameID);
    }
}
