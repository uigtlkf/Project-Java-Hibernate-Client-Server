package server.main.Rules;


import messagesbase.messagesfromclient.ETerrain;
import messagesbase.messagesfromclient.PlayerHalfMap;
import messagesbase.messagesfromclient.PlayerHalfMapNode;

import java.util.Collection;

public class CheckGrass extends MapRule {
    private final int MIN_GRASS_NUM;

    public CheckGrass(int MAX_GRASS_NUM){
        this.MIN_GRASS_NUM=MAX_GRASS_NUM;
    }

    /**
     * Checks the PlayerHalfMap on having enough Grass Terrains.
     *
     * @param currentMap The PlayerHalfMap to check.
     * @return True if the PlayerHalfMap has enough Grass Terrains, otherwise false.
     */
    @Override
    public boolean checkHalfMap(PlayerHalfMap currentMap) {
        int grassCount = 0;
        Collection<PlayerHalfMapNode> nodes = currentMap.getMapNodes();
        for (PlayerHalfMapNode node : nodes) {
            ETerrain terrains = node.getTerrain();
            if (terrains == ETerrain.Grass) {
                grassCount++;
            }
        }

        return grassCount >= MIN_GRASS_NUM;
    }
}
