package client.main.Movement;

import client.main.Map.*;
import client.main.Player.Player;

import java.util.*;

public class Goal {
    private Optional<Position> goal = Optional.empty();
    private boolean previousTreasureState = false;

    /**
     * Determines the goal position for the player based on the current map, player's treasure state, and visited positions.
     *
     * @param currentMap The current merged map.
     * @param player     The player whose goal needs to be determined.
     * @param visited    The set of visited positions by the player.
     * @return The goal position for the player.
     */
    public Position getGoal(MergedMap currentMap, Player player, Set<Position> visited) {
        Position playerPosition = player.getPlayerPosition();
        if (player.isCollectedTreasure()) {
            for(Position position: visited){
                if(currentMap.getMap().get(position)!=null){
                    if(currentMap.getMap().get(position).getOpponentCastle()!=null){
                        Position opponentCastlePosition=currentMap.getMap().get(position).getOpponentCastle().getPosition().orElse(null);
                        if(opponentCastlePosition!=null){
                            return opponentCastlePosition;
                        }
                    }
                }
            }
        } else if(!(player.isCollectedTreasure())){
            for(Position position: visited){
                if(currentMap.getMap().get(position)!=null){
                    if(currentMap.getMap().get(position).getMyTreasure()!=null) {
                        Position myTreasurePosition = currentMap.getMap().get(position).getMyTreasure().getPosition().orElse(null);
                        if (myTreasurePosition != null) {
                            return myTreasurePosition;
                        }
                    }
                }
            }
        }

        if (!goal.isPresent() || playerPosition.equals(goal.get()) || previousTreasureState != player.isCollectedTreasure()) {
            goal = Optional.of(getNewRandomGoal(currentMap, player, visited));
        }
        previousTreasureState = player.isCollectedTreasure();
        return goal.get();
    }

    /**
     * Returns a new random goal position that hasn't been visited by the player yet.
     *
     * @param currentMap The current merged map.
     * @param player     The player whose new goal needs to be determined.
     * @param visited    The set of visited positions by the player.
     * @return A new random goal position for the player.
     * @throws IllegalArgumentException If no unvisited positions left on the map.
     */
    private Position getNewRandomGoal(MergedMap currentMap, Player player, Set<Position> visited) {
        Map<Position, Node> nodes = currentMap.getMap();
        List<Position> unvisitedPositions = getUnvisitedNonWaterPositions(currentMap, visited, player.isCollectedTreasure());

        if (!unvisitedPositions.isEmpty()) {
            return getRandomPosition(unvisitedPositions);
        } else {
           throw new IllegalArgumentException("No unvisited fields left");
        }
    }
    /**
     * Retrieves unvisited positions that are not water and match the treasure state of the player.
     *
     * @param currentMap The current merged map.
     * @param visited    The set of visited positions by the player.
     * @param hasTreasure A boolean determining whether the player has collected treasure.
     * @return A list of unvisited non-water positions based on the player's treasure state.
     */
    private List<Position> getUnvisitedNonWaterPositions(MergedMap currentMap, Set<Position> visited, boolean hasTreasure) {
        return currentMap.getMap().entrySet().stream()
                .filter(entry -> entry.getValue().getNodeType() != NodeType.Water && !visited.contains(entry.getKey()) && entry.getKey().isMine() != hasTreasure)
                .map(Map.Entry::getKey)
                .toList();
    }
    /**
     * Returns a random position from the provided list of positions.
     *
     * @param positions A list of positions from which a random position needs to be selected.
     * @return A randomly selected position from the list.
     */
    private Position getRandomPosition(List<Position> positions) {
        Random random = new Random();
        int randomIndex = random.nextInt(positions.size());
        return positions.get(randomIndex);
    }
}
