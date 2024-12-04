package server.main.Rules;


import messagesbase.messagesfromclient.ETerrain;
import messagesbase.messagesfromclient.PlayerHalfMap;
import messagesbase.messagesfromclient.PlayerHalfMapNode;

import java.util.Collection;

public class CheckMountains extends MapRule {
    private final int MIN_MOUNTAINS_NUM;
    public CheckMountains(int MIN_MOUNTAINS_NUM){
        this.MIN_MOUNTAINS_NUM=MIN_MOUNTAINS_NUM;
    }

    /**
     * Checks the PlayerHalfMap on having enough Mountain Terrains.
     *
     * @param currentMap The PlayerHalfMap to check.
     * @return True if the PlayerHalfMap has enough Mountain Terrains, otherwise false.
     */
    @Override
    public boolean checkHalfMap(PlayerHalfMap currentMap) {
        int mountainsCount = 0;
        Collection<PlayerHalfMapNode> nodes = currentMap.getMapNodes();
        for (PlayerHalfMapNode node : nodes) {
            ETerrain terrain =node.getTerrain();
            if (terrain == ETerrain.Mountain) {
                mountainsCount++;
            }
        }

        return mountainsCount >= MIN_MOUNTAINS_NUM;
    }
}
