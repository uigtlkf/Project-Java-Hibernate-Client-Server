package client.main.Rules;

import client.main.Map.HalfMap;
import client.main.Map.NodeType;
import client.main.Map.Position;

import java.util.HashMap;
import java.util.Map;

public class CheckIslands extends MapRule {
    private Map<Position, NodeType> visited;

    /**
     * Checks the HalfMap on having "islands" - unreachable Grass or Mountain nodes, due to being surrounded by Water nodes.
     *
     * @param currentMap The HalfMap to check.
     * @return True if the HalfMap has no "islands", otherwise false.
     */
    @Override
    public boolean checkHalfMap(HalfMap currentMap) {
        visited=new HashMap<>();
        Map<Position, NodeType> map = currentMap.getMap();

        for (Map.Entry<Position, NodeType> entry : map.entrySet()) {
            Position position = entry.getKey();
            NodeType nodeType = entry.getValue();

            if (nodeType == NodeType.Grass || nodeType == NodeType.Mountain) {
                fillMap(map, position);
                break;
            }
        }

        for (Map.Entry<Position, NodeType> entry : map.entrySet()) {
            Position position = entry.getKey();
            NodeType nodeType = entry.getValue();
            if (nodeType == NodeType.Grass || nodeType == NodeType.Mountain) {
                if (!visited.containsKey(position)) {
                    return false;
                }
            }
        }
        return true;
    }

        private void fillMap(Map < Position, NodeType > map, Position position){
            if (!map.containsKey(position) || map.get(position) == NodeType.Water || visited.containsKey(position)) {
                return;
            }
            visited.put(position, map.get(position));

            fillMap(map, new Position(position.getX() + 1, position.getY()));
            fillMap(map, new Position(position.getX(), position.getY() + 1));
            fillMap(map, new Position(position.getX() - 1, position.getY()));
            fillMap(map, new Position(position.getX(), position.getY() - 1));


        }

    }

