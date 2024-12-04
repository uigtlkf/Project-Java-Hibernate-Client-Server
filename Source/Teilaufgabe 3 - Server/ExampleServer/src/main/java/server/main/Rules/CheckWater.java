package server.main.Rules;


import messagesbase.messagesfromclient.ETerrain;
import messagesbase.messagesfromclient.PlayerHalfMap;
import messagesbase.messagesfromclient.PlayerHalfMapNode;

import java.util.Collection;

public class CheckWater extends MapRule {
    private final int MIN_WATER_NUM;

    public CheckWater(int MIN_WATER_NUM){
        this.MIN_WATER_NUM=MIN_WATER_NUM;
    }

    /**
     * Checks the HalfMap on having enough Water Terrains.
     *
     * @param currentMap The PlayerHalfMap to check.
     * @return True if the PlayerHalfMap has enough Water Terrains, otherwise false.
     */
    @Override
    public boolean checkHalfMap(PlayerHalfMap currentMap) {
        int waterCount = 0;
        Collection<PlayerHalfMapNode> nodes = currentMap.getMapNodes();
        for (PlayerHalfMapNode node : nodes) {
            ETerrain terrain = node.getTerrain();
            if (terrain == ETerrain.Water) {
                waterCount++;
            }
        }

        return waterCount >= MIN_WATER_NUM;
    }
}
