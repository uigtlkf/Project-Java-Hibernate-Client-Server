package client.main.Game;

import java.util.Objects;

public class GameID {
    private String gameID;

    public GameID(String gameID){this.gameID=gameID;}

    public String getGameID() {
        return gameID;
    }

    @Override
    public String toString() {
        return gameID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GameID gameID1 = (GameID) o;
        return Objects.equals(gameID, gameID1.gameID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(gameID);
    }
}
