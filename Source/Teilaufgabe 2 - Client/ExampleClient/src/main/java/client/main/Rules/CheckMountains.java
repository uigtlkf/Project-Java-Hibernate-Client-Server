package client.main.Rules;

import client.main.Map.HalfMap;
import client.main.Map.NodeType;

public class CheckMountains extends MapRule {
    private final int MIN_MOUNTAINS_NUM;

    public CheckMountains(int MIN_MOUNTAINS_NUM){
        this.MIN_MOUNTAINS_NUM=MIN_MOUNTAINS_NUM;
    }

    /**
     * Checks the HalfMap on having enough Mountain Nodes.
     *
     * @param currentMap The HalfMap to check.
     * @return True if the HalfMap has enough Mountain Nodes, otherwise false.
     */
    @Override
    public boolean checkHalfMap(HalfMap currentMap) {
        int mountainsCount = 0;
        for (NodeType nodeType : currentMap.getMap().values()) {
            if (nodeType == NodeType.Mountain) {
                mountainsCount++;
            }
        }
        return mountainsCount >= MIN_MOUNTAINS_NUM;
    }
}
