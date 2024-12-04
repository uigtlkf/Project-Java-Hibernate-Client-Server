package server.main.Rules;

import messagesbase.messagesfromclient.ETerrain;
import messagesbase.messagesfromclient.PlayerHalfMap;
import messagesbase.messagesfromclient.PlayerHalfMapNode;

import java.util.HashSet;
import java.util.Set;

public class CheckIslands extends MapRule {
    private Set<PlayerHalfMapNode> visited;

    private final int NUM_ROWS;
    private final int NUM_COLS;

    public CheckIslands(int NUM_ROWS,int NUM_COLS){
        this.NUM_ROWS=NUM_ROWS;
        this.NUM_COLS=NUM_COLS;
    }

    /**
     * Checks the PlayerHalfMap on having "islands" - unreachable Grass or Mountain nodes, due to being surrounded by Water nodes.
     *
     * @param currentMap The PlayerHalfMap to check.
     * @return True if the PlayerHalfMap has no "islands", otherwise false.
     */
    @Override
    public boolean checkHalfMap(PlayerHalfMap currentMap) {
        visited = new HashSet<>();
        for (PlayerHalfMapNode node : currentMap.getMapNodes()) {
            if (!visited.contains(node)) {
                if (node.getTerrain() == ETerrain.Grass || node.getTerrain() == ETerrain.Mountain) {
                    if (!fillMap(currentMap, node)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    private boolean fillMap(PlayerHalfMap currentMap, PlayerHalfMapNode node) {
        if (!visited.contains(node)) {
            visited.add(node);

            int x = node.getX();
            int y = node.getY();

            boolean result = true;
            result &= (x > 0) ? fillMap(currentMap, getNodeAt(currentMap, x - 1, y)) : true;
            result &= (x < NUM_COLS) ? fillMap(currentMap, getNodeAt(currentMap, x + 1, y)) : true;
            result &= (y > 0) ? fillMap(currentMap, getNodeAt(currentMap, x, y - 1)) : true;
            result &= (y < NUM_ROWS) ? fillMap(currentMap, getNodeAt(currentMap, x, y + 1)) : true;

            return result;
        }
        return true;
    }

    private PlayerHalfMapNode getNodeAt(PlayerHalfMap currentMap, int x, int y) {
        for (PlayerHalfMapNode node : currentMap.getMapNodes()) {
            if (node.getX() == x && node.getY() == y) {
                return node;
            }
        }
        return null;
    }
}
