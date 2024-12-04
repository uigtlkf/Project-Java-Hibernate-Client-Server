package server.main;

public class MapManager {
    public void saveHalfMap(Game currentGame, PlayerID playerID, ServerHalfMap halfMap) {
        TreasureKeeper treasureKeeper = new TreasureKeeper();
        treasureKeeper.hideTreasure(halfMap);

        currentGame.getGameMap().addMap(playerID, halfMap);

        if(currentGame.areBothMapsSent()) {
            HalfMapCompilator compilator = new HalfMapCompilator();
            // Compile maps
            GameMap fullMap = compilator.compileHalfMaps(
                    currentGame.getFirstPlayer().getPlayerID(),
                    currentGame.getHalfMapById(currentGame.getFirstPlayer().getPlayerID()),
                    currentGame.getSecondPlayer().getPlayerID(),
                    currentGame.getHalfMapById(currentGame.getSecondPlayer().getPlayerID())
            );
            currentGame.setGameMap(fullMap);
            // Position players
            positionPlayers(currentGame, fullMap.getHalfById(currentGame.getFirstPlayer().getPlayerID()), fullMap.getHalfById(currentGame.getSecondPlayer().getPlayerID()));
        } else {
            positionPlayer(currentGame.getFirstPlayer(), halfMap);
        }
    }

    private void positionPlayer(Player player, ServerHalfMap halfMap) {
        Position playerPosition = halfMap.getCastle().getCastlePosition();
        player.setPosition(playerPosition);
    }

    private void positionPlayers(Game game, ServerHalfMap firstPlayerHalf, ServerHalfMap secondPlayerHalf) {
        positionPlayer(game.getFirstPlayer(), firstPlayerHalf);
        positionPlayer(game.getSecondPlayer(), secondPlayerHalf);
    }
}
