package server.main;
import java.util.Objects;

public class Player {
    private boolean isRegistered;
    private final PlayerData playerData;
    private final PlayerID playerID;
    private boolean hasTreasure;
    private Position position;
    private PlayerGameState state;

    public Player(PlayerID playerID, PlayerData playerData) {
        this.playerData = playerData;
        this.playerID = playerID;
        this.hasTreasure = false;
        this.isRegistered = true;
        this.state = PlayerGameState.MustWait;
    }

    public Player() {
        this.playerData = new PlayerData();
        this.playerID = new PlayerID("");
        this.isRegistered = false;
        this.state = PlayerGameState.MustWait;
    }

    public Player(boolean isRegistered, PlayerData playerData, PlayerID playerID, boolean hasTreasure, Position position, PlayerGameState state) {
        this.isRegistered = isRegistered;
        this.playerData = playerData;
        this.playerID = playerID;
        this.hasTreasure = hasTreasure;
        this.position = position;
        this.state = state;
    }

    public boolean isRegistered() {
        return isRegistered;
    }

    public PlayerID getPlayerID() {
        return playerID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return isRegistered == player.isRegistered && hasTreasure == player.hasTreasure && Objects.equals(playerData, player.playerData) && Objects.equals(playerID, player.playerID) && Objects.equals(position, player.position) && state == player.state;
    }

    @Override
    public int hashCode() {
        return Objects.hash(isRegistered, playerData, playerID, hasTreasure, position, state);
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public PlayerGameState getState() {
        return state;
    }

    public PlayerData getPlayerData() {
        return playerData;
    }

    public boolean hasTreasure() {
        return hasTreasure;
    }

    public Position getPosition() {
        return position;
    }

    public void setState(PlayerGameState state) {
        this.state = state;
    }
}
