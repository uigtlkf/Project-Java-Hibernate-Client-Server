package client.main.Rules;

import client.main.Map.HalfMap;
import client.main.Map.NodeType;


public class CheckGrass extends MapRule {
    private final int MIN_GRASS_NUM;

    public CheckGrass(int MAX_GRASS_NUM){
        this.MIN_GRASS_NUM=MAX_GRASS_NUM;
    }

    /**
     * Checks the HalfMap on having enough Grass Nodes.
     *
     * @param currentMap The HalfMap to check.
     * @return True if the HalfMap has enough Grass Nodes, otherwise false.
     */
    @Override
    public boolean checkHalfMap(HalfMap currentMap) {
        int grassCount = 0;
        for (NodeType nodeType : currentMap.getMap().values()) {
            if (nodeType == NodeType.Grass) {
                grassCount++;
            }
        }
        return grassCount >= MIN_GRASS_NUM;
    }
}
