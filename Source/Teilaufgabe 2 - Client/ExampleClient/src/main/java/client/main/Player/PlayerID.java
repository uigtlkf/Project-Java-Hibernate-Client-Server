package client.main.Player;

public class PlayerID {
    private String playerID;
    public PlayerID(String playerID){
        this.playerID=playerID;
    }
    @Override
    public String toString() {
        return playerID;
    }

    public String getPlayerID() {
        return playerID;
    }
}