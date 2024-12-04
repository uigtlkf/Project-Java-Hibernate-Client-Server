package server.main;

import java.util.*;

public class InformationHider {
    public Game hideInformationInGameForPlayer(Game currentGame, PlayerID playerID) {
        return hidePlayerPositionForPlayer(hidePlayerIDForPlayer(currentGame, playerID), playerID);
    }

    private Game hidePlayerPositionForPlayer(Game currentGame, PlayerID playerID) {

        Player enemyPlayer = currentGame.getEnemyPlayerByID(playerID);
        Position hiddenPosition = new Position(0, 0);
        Player newEnemyPlayer = new Player(
                enemyPlayer.isRegistered(),
                enemyPlayer.getPlayerData(),
                enemyPlayer.getPlayerID(),
                enemyPlayer.hasTreasure(),
                hiddenPosition,
                enemyPlayer.getState()
        );
        Player firstPlayer = currentGame.getFirstPlayer();
        Player secondPlayer = currentGame.getSecondPlayer();
        if (enemyPlayer.getPlayerID().equals(firstPlayer.getPlayerID())) {
            firstPlayer = newEnemyPlayer;
        } else {
            secondPlayer = newEnemyPlayer;
        }
        return new Game(currentGame.getGameMap(), firstPlayer, secondPlayer, currentGame.getGameStateIdentifier());
    }

    private Game hidePlayerIDForPlayer(Game currentGame, PlayerID playerID) {
        Player newEnemyPlayer = new Player(
                currentGame.getEnemyPlayerByID(playerID).isRegistered(),
                currentGame.getEnemyPlayerByID(playerID).getPlayerData(),
                new PlayerID(UUID.randomUUID().toString()),
                currentGame.getEnemyPlayerByID(playerID).hasTreasure(),
                currentGame.getEnemyPlayerByID(playerID).getPosition(),
                currentGame.getEnemyPlayerByID(playerID).getState()
        );

        Player firstPlayer = currentGame.getFirstPlayer();
        Player secondPlayer = currentGame.getSecondPlayer();
        if(currentGame.getEnemyPlayerByID(playerID).getPlayerID().equals(firstPlayer.getPlayerID())) {
            firstPlayer = newEnemyPlayer;
        } else {
            secondPlayer = newEnemyPlayer;
        }
        GameMap map = currentGame.getGameMap();
        var halfMaps = new HashMap<>(map.getHalfMaps());
        if(halfMaps.containsKey(currentGame.getEnemyPlayerByID(playerID).getPlayerID())) {
            var halfMap = halfMaps.remove(currentGame.getEnemyPlayerByID(playerID).getPlayerID());
            halfMaps.put(newEnemyPlayer.getPlayerID(), halfMap);
        }
        return new Game(new GameMap(halfMaps), firstPlayer, secondPlayer, currentGame.getGameStateIdentifier());
    }
}
