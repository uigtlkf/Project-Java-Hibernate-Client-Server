package client.main.Rules;

import client.main.Map.HalfMap;
import client.main.Map.NodeType;
import client.main.Map.Position;

public class CheckWaterBorders extends MapRule {
    private final int MAX_RIGHT_WATER_NUM;
    private final int MAX_LEFT_WATER_NUM;
    private final int MAX_UPPER_WATER_NUM;
    private final int MAX_LOWER_WATER_NUM;

    private final int NUM_ROWS;
    private final int NUM_COLS;

    public CheckWaterBorders(int MAX_RIGHT_WATER_NUM, int MAX_LEFT_WATER_NUM, int MAX_UPPER_WATER_NUM, int MAX_LOWER_WATER_NUM,
    int NUM_ROWS,int NUM_COLS){
        this.MAX_RIGHT_WATER_NUM=MAX_RIGHT_WATER_NUM;
        this.MAX_LEFT_WATER_NUM=MAX_LEFT_WATER_NUM;
        this.MAX_UPPER_WATER_NUM=MAX_UPPER_WATER_NUM;
        this.MAX_LOWER_WATER_NUM=MAX_LOWER_WATER_NUM;
        this.NUM_ROWS=NUM_ROWS;
        this.NUM_COLS=NUM_COLS;
    }


    /**
     * Checks the borders of the HalfMap on having not more than specified amount of Water Nodes.
     *
     * @param currentMap The HalfMap to check.
     * @return True if the borders of HalfMap have not more than the specified amount of Water Nodes, otherwise false.
     */
    @Override
    public boolean checkHalfMap(HalfMap currentMap) {
        int upperWaterCount = 0;
        int lowerWaterCount = 0;
        int leftWaterCount = 0;
        int rightWaterCount = 0;
        for (int col = 0; col <= NUM_COLS; col++) {
            Position upperPosition = new Position(col, 0);
            NodeType upperNodeType = currentMap.getMap().get(upperPosition);
            if (upperNodeType == NodeType.Water) {
                upperWaterCount++;
            }
            Position lowerPosition = new Position(col, NUM_ROWS);
            NodeType lowerNodeType = currentMap.getMap().get(lowerPosition);
            if (lowerNodeType == NodeType.Water) {
                lowerWaterCount++;
            }
        }

        for (int row = 0; row <= NUM_ROWS; row++) {
            Position leftPosition = new Position(0, row);
            NodeType leftNodeType = currentMap.getMap().get(leftPosition);
            if (leftNodeType == NodeType.Water) {
                leftWaterCount++;
            }
            Position rightPosition = new Position(NUM_COLS, row);
            NodeType rightNodeType = currentMap.getMap().get(rightPosition);
            if (rightNodeType == NodeType.Water) {
                rightWaterCount++;
            }
        }
        return rightWaterCount <= MAX_RIGHT_WATER_NUM && leftWaterCount <= MAX_LEFT_WATER_NUM && lowerWaterCount <= MAX_LOWER_WATER_NUM && upperWaterCount <= MAX_UPPER_WATER_NUM;
    }
}
