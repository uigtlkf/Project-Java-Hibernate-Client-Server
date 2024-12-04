package client.main.Movement;

import client.main.Exceptions.NoPossibleWayError;
import client.main.Map.MergedMap;
import client.main.Map.Node;
import client.main.Map.Position;
import client.main.Map.NodeType;

import java.util.*;

import static client.main.Movement.MoveAction.getMoveAction;

public class WayCalculation {
    /**
     * Calculates the way from the player's current position to his destination.
     *
     * @param currentMap      The current merged map.
     * @param playerPosition  The position of the player.
     * @param destination     The destination which the player aims to reach.
     * @return                The calculated way to the player's destination.
     * @throws NoPossibleWayError If no valid way can be found from the current player's position to his destination.
     */
    public Way calculateWay(MergedMap currentMap, Position playerPosition, Position destination) {
        Map<Position, Position> pathMap = new HashMap<>();
        Queue<Position> processingQueue = new LinkedList<>();
        processingQueue.add(playerPosition);
        pathMap.put(playerPosition, null);

        while (!processingQueue.isEmpty()) {
            Position current = processingQueue.poll();

            if (current.equals(destination)) {
                return reconstructWay(pathMap, current);
            }

            for (MoveAction action : MoveAction.values()) {
                Position next = new Position(
                        current.getX() + action.getOffsetX(),
                        current.getY() + action.getOffsetY()
                );

                if (isValidMove(currentMap, next) && !pathMap.containsKey(next)) {
                    processingQueue.add(next);
                    pathMap.put(next, current);
                }
            }
        }
        throw new NoPossibleWayError("No way found");
    }
    /**
     * Reconstructs the way based on the path map and the current position of the player.
     *
     * @param pathMap The map containing the path from positions to their previous positions.
     * @param current The current position of the player.
     * @return The way representing the reconstructed path.
     */
    private Way reconstructWay(Map<Position, Position> pathMap, Position current) {
        List<MoveAction> actions = new ArrayList<>();
        while (pathMap.get(current) != null) {
            Position previous = pathMap.get(current);
            actions.add(getMoveAction(previous, current));
            current = previous;
        }
        Collections.reverse(actions);
        return new Way(actions);
    }
    /**
     * Checks whether a move to the given position on the current map is valid or not.
     *
     * @param currentMap The current merged map.
     * @param position   The position to be checked.
     * @return True if the move to the position is valid, otherwise false.
     */

    private boolean isValidMove(MergedMap currentMap, Position position) {
        Map<Position, Node> mergedMap = currentMap.getMap();
        return mergedMap.containsKey(position) && mergedMap.get(position).getNodeType() != NodeType.Water;
    }
}
