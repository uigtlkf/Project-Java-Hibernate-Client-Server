package server.main;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class HalfMapCompilator {
    private final int WIDTH = 10;
    private final int HEIGHT = 5;
    private final int NUMBER_OF_COMBINATIONS = 4;

    public GameMap compileHalfMaps(
            PlayerID firstPlayerID, ServerHalfMap firstMap,
            PlayerID secondPlayerID, ServerHalfMap secondMap) {

        Random random = new Random();
        int combination = random.nextInt(NUMBER_OF_COMBINATIONS);

        Map<PlayerID, ServerHalfMap> firstHalfMap = new HashMap<>();
        Map<PlayerID, ServerHalfMap> secondHalfMap = new HashMap<>();

        switch (combination) {
            case 0:
                secondMap = combine(secondMap, 0, HEIGHT);
                break;
            case 1:
                firstMap = combine(firstMap, 0, HEIGHT);
                break;
            case 2:
                secondMap = combine(secondMap, WIDTH, 0);
                break;
            case 3:
                firstMap = combine(firstMap, WIDTH, 0);
                break;
        }
        firstHalfMap.put(firstPlayerID, firstMap);
        secondHalfMap.put(secondPlayerID, secondMap);
        GameMap gameMap = new GameMap(firstHalfMap, secondHalfMap);
        return gameMap;
    }

    private ServerHalfMap combine(ServerHalfMap secondMap, int width, int height) {
        ServerHalfMap newHalfMap = new ServerHalfMap();
        for (Map.Entry<Position, NodeType> entry : secondMap.getPlayerHalfMap().entrySet()) {
            Position newPos = new Position(entry.getKey().getX() + width, entry.getKey().getY() + height);
            newHalfMap.getPlayerHalfMap().put(newPos, entry.getValue());
        }
        newHalfMap.setCastle(
                new Castle(
                        new Position(
                        secondMap.getCastle().getCastlePosition().getX() + width,
                            secondMap.getCastle().getCastlePosition().getY() + height
                        )
                )
        );
        newHalfMap.setTreasure(
                new Treasure(
                        new Position(
                                secondMap.getTreasure().getTreasurePosition().getX() + width,
                                secondMap.getTreasure().getTreasurePosition().getY() + height
                        )
                )
        );
        return newHalfMap;
    }
}
