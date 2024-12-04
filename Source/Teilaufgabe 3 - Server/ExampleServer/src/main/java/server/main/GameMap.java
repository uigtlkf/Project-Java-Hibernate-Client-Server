package server.main;

import java.util.HashMap;
import java.util.Map;

public class GameMap {
    private Map<PlayerID, ServerHalfMap> halfMaps;

    public GameMap() {
        halfMaps = new HashMap<>();
    }

    public GameMap(Map<PlayerID, ServerHalfMap> firstHalfMap, Map<PlayerID, ServerHalfMap> secondHalfMap) {
        halfMaps = new HashMap<>();
        halfMaps.putAll(firstHalfMap);
        halfMaps.putAll(secondHalfMap);
    }

    public GameMap(Map<PlayerID, ServerHalfMap> halfMaps) {
        this.halfMaps = halfMaps;
    }

    public void addMap(PlayerID playerID, ServerHalfMap halfMap) {
        if(isComplete()) {
            throw new IllegalArgumentException("Max. # of maps reached");
        }
        halfMaps.put(playerID, halfMap);
    }

    @Override
    public String toString() {
        return "GameMap{" +
                "halfMaps=" + halfMaps +
                '}';
    }

    public boolean isComplete() {
        return halfMaps.size() == 2;
    }

    public ServerHalfMap getHalfById(PlayerID playerID) {
        return this.halfMaps.getOrDefault(playerID, null);
    }

    public Map<PlayerID, ServerHalfMap> getHalfMaps() {
        return halfMaps;
    }
}
