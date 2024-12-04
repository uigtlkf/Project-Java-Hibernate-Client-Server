package server.main.Rules;

import messagesbase.messagesfromclient.ETerrain;
import messagesbase.messagesfromclient.PlayerHalfMap;
import messagesbase.messagesfromclient.PlayerHalfMapNode;
import java.util.Collection;

public class CheckWaterBorders extends MapRule {
    private final int MAX_RIGHT_WATER_NUM;
    private final int MAX_LEFT_WATER_NUM;
    private final int MAX_UPPER_WATER_NUM;
    private final int MAX_LOWER_WATER_NUM;

    private final int NUM_ROWS;
    private final int NUM_COLS;

    public CheckWaterBorders(int MAX_RIGHT_WATER_NUM, int MAX_LEFT_WATER_NUM, int MAX_UPPER_WATER_NUM, int MAX_LOWER_WATER_NUM, int NUM_ROWS,int NUM_COLS){
        this.MAX_RIGHT_WATER_NUM=MAX_RIGHT_WATER_NUM;
        this.MAX_LEFT_WATER_NUM=MAX_LEFT_WATER_NUM;
        this.MAX_UPPER_WATER_NUM=MAX_UPPER_WATER_NUM;
        this.MAX_LOWER_WATER_NUM=MAX_LOWER_WATER_NUM;
        this.NUM_ROWS=NUM_ROWS;
        this.NUM_COLS=NUM_COLS;
    }


    /**
     * Checks the borders of the HalfMap on having not more than specified amount of Water Terrains.
     *
     * @param currentMap The PlayerHalfMap to check.
     * @return True if the borders of PlayerHalfMap have not more than the specified amount of Water Terrains, otherwise false.
     */
    @Override
    public boolean checkHalfMap(PlayerHalfMap currentMap) {
        Collection<PlayerHalfMapNode> nodes = currentMap.getMapNodes();
        int upperWaterCount = 0, lowerWaterCount = 0, leftWaterCount = 0, rightWaterCount = 0;

        for (PlayerHalfMapNode node : nodes) {
            int x = node.getX();
            int y = node.getY();
            ETerrain terrain = node.getTerrain();

            if (y == 0 && terrain == ETerrain.Water) {
                upperWaterCount++;
            }
            if (y == NUM_ROWS && terrain == ETerrain.Water) {
                lowerWaterCount++;
            }
            if (x == 0 && terrain == ETerrain.Water) {
                leftWaterCount++;
            }
            if (x == NUM_COLS && terrain == ETerrain.Water) {
                rightWaterCount++;
            }
        }


        return (upperWaterCount <= MAX_UPPER_WATER_NUM &&
                lowerWaterCount <= MAX_LOWER_WATER_NUM &&
                leftWaterCount <= MAX_LEFT_WATER_NUM &&
                rightWaterCount <= MAX_RIGHT_WATER_NUM);
    }
}
