package client.main.Map;

import java.util.HashMap;
import java.util.Map;

public class HalfMap {
    private Map<Position, NodeType> map;
    private Castle castle;
    private final int NUM_ROWS = 4;
    private final int NUM_COLS = 9;

    public HalfMap(Map<Position, NodeType> map, Castle castle){
        this.map=map;
        this.castle=castle;
    }
    public HalfMap(Castle castle) {
        Map<Position, NodeType>map=new HashMap<>();
        this.map = map;
        this.castle = castle;
    }

    public HalfMap(Map<Position, NodeType> map) {
        this.map=map;
    }

    public int getNUM_COLS() {
        return NUM_COLS;
    }

    public int getNUM_ROWS() {
        return NUM_ROWS;
    }

    public Castle getCastle() {
        return castle;
    }

    public Map<Position, NodeType> getMap() {
        return map;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (this.getMap() != null) {
            Position castlePosition = this.getCastle().getPosition().orElse(null);

            for (int row = 0; row <= NUM_ROWS; row++) {
                for (int col = 0; col <= NUM_COLS; col++) {
                    Position position = new Position(col, row);
                    NodeType nodeType = this.getMap().get(position);

                    String backgroundColor = "\u001B[0m";
                    String content = "  ";
                    if (position.equals(castlePosition)) {
                        if (nodeType == NodeType.Grass) {
                            backgroundColor = "\u001B[42m";
                        } else {
                            backgroundColor = "\u001B[41m";
                        }
                        content = "C ";
                    }  else if (nodeType != null) {
                        if (nodeType == NodeType.Water) {
                            backgroundColor = "\u001B[44m";
                        } else if (nodeType == NodeType.Mountain) {
                            backgroundColor = "\u001B[47m";
                        } else if (nodeType == NodeType.Grass) {
                            backgroundColor = "\u001B[42m";
                        }
                    }

                    sb.append(backgroundColor).append(content).append("\u001B[0m");
                }
                sb.append("\n");
            }
        }else {
            sb.append("Game is null.");
        }
        return sb.toString();
    }

    public void setMap(Map<Position, NodeType> map) {
        this.map = map;
    }
}
