package client.main.Movement;

import client.main.Map.MergedMap;
import client.main.Map.NodeType;
import client.main.Map.Position;
import client.main.Player.Player;

import java.util.*;

public class Movement {
    private Goal goal;
    private WayCalculation wayCalc;
    private Set<Position> visited;
    private Position myPreviousPosition;
    private Way currentWay;

    public Movement() {
        goal = new Goal();
        wayCalc = new WayCalculation();
        visited = new HashSet<>();
    }
    /**
     * Returns the next MoveAction for the player based on the current player's position and merged map.
     *
     * @param player     The player for which the next MoveAction to be determined.
     * @param currentMap The current merged map.
     * @return The next MoveAction for the player.
     */
    public MoveAction getNextAction(Player player, MergedMap currentMap) {
        Position playerPosition = player.getPlayerPosition();
        visit(playerPosition, currentMap);
        if(myPreviousPosition == null || !myPreviousPosition.equals(playerPosition)) {
            Position destination = goal.getGoal(currentMap, player, visited);
            currentWay = wayCalc.calculateWay(currentMap, playerPosition, destination);
        }
        myPreviousPosition = playerPosition;
        return currentWay.getNextAction();
    }
    /**
     * Marks the specified position as visited and expands the visited positions based on certain conditions.
     *
     * @param position    The position to be marked as visited.
     * @param currentMap  The current merged map.
     */
    private void visit(Position position, MergedMap currentMap) {
        visited.add(position);
        if(currentMap.getMap().get(position).getNodeType() == NodeType.Mountain) {
            for (MoveAction action : MoveAction.values()) {
                Position next = new Position(
                        position.getX() + action.getOffsetX(),
                        position.getY() + action.getOffsetY()
                );
                visited.add(next);
            }
            visited.add(new Position(position.getX() + 1, position.getY() + 1));
            visited.add(new Position(position.getX() - 1, position.getY() - 1));
            visited.add(new Position(position.getX() - 1, position.getY() + 1));
            visited.add(new Position(position.getX() + 1, position.getY() - 1));
        }
    }


    public Set<Position> getVisited() {
        return visited;
    }


}
