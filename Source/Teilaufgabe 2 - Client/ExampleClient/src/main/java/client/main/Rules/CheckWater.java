package client.main.Rules;

import client.main.Map.HalfMap;
import client.main.Map.NodeType;


public class CheckWater extends MapRule {
    private final int MIN_WATER_NUM;


    public CheckWater(int MIN_WATER_NUM){
        this.MIN_WATER_NUM=MIN_WATER_NUM;
    }

    /**
     * Checks the HalfMap on having enough Water Nodes.
     *
     * @param currentMap The HalfMap to check.
     * @return True if the HalfMap has enough Water Nodes, otherwise false.
     */
    @Override
    public boolean checkHalfMap(HalfMap currentMap) {
        int waterCount = 0;
        for (NodeType nodeType : currentMap.getMap().values()) {
            if (nodeType == NodeType.Water) {
                waterCount++;
            }
        }
        return waterCount >= MIN_WATER_NUM;
    }
}
