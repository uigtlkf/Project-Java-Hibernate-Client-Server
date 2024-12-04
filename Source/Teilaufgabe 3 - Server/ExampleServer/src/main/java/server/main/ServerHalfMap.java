package server.main;

import java.util.HashMap;
import java.util.Map;

public class ServerHalfMap {
    private boolean isSent;
    private Map<Position, NodeType> playerHalfMap;
    private Castle castle;
    private Treasure treasure;

    public ServerHalfMap() {
        this.isSent = false;
        this.playerHalfMap = new HashMap<>();
    }

    public Map<Position, NodeType> getPlayerHalfMap() {
        return playerHalfMap;
    }

    public void setPlayerHalfMap(Map<Position, NodeType> playerHalfMap) {
        this.playerHalfMap = playerHalfMap;
        this.isSent = true;
    }

    public void setCastle(Castle castle) {
        this.castle = castle;
    }

    public void setTreasure(Treasure treasure) {
        this.treasure = treasure;
    }

    public boolean isSent() {
        return isSent;
    }

    public Castle getCastle() {
        return castle;
    }

    public Treasure getTreasure() {
        return treasure;
    }

    @Override
    public String toString() {
        return "ServerHalfMap{" +
                "isSent=" + isSent +
                ", playerHalfMap=" + playerHalfMap +
                ", castle=" + castle +
                ", treasure=" + treasure +
                '}';
    }
}
