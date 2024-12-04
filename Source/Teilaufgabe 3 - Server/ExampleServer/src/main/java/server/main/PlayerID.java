package server.main;

import java.util.Objects;

public class PlayerID {
    private final String playerID;


    public PlayerID(String playerID) {
        this.playerID = playerID;
    }

    public String getPlayerID() {
        return playerID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PlayerID playerID1 = (PlayerID) o;
        return Objects.equals(playerID, playerID1.playerID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(playerID);
    }
}
