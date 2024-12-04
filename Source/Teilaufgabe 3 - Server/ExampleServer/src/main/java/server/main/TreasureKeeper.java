package server.main;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class TreasureKeeper {
    public void hideTreasure(ServerHalfMap halfMap) {
        List<Position> grassPositions = new ArrayList<>();
        Map<Position, NodeType> mapNodes = halfMap.getPlayerHalfMap();

        for (Map.Entry<Position, NodeType> entry : mapNodes.entrySet()) {
            if (entry.getValue() == NodeType.Grass) {
                grassPositions.add(entry.getKey());
            }
        }

        if (!grassPositions.isEmpty()) {
            Random random = new Random();
            Position treasurePosition = grassPositions.get(random.nextInt(grassPositions.size()));

            Treasure treasure = new Treasure(treasurePosition);
            halfMap.setTreasure(treasure);
        } else {
            throw new IllegalArgumentException("Map without any grass fields was given");
        }
    }
}
