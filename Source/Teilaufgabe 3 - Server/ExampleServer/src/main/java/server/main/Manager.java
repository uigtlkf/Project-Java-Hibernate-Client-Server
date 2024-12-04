package server.main;

import java.util.UUID;

public class Manager {
    private GamesDatabase gamesDatabase = new GamesDatabase();
    private InformationHider hider = new InformationHider();
    public GameID createGameID() {
        GameCreator creator = new GameCreator();
        GameID gameID = creator.createGameID();
        gamesDatabase.add(gameID, new Game());
        return gameID;
    }

    public PlayerID registerPlayer(GameID gameID, PlayerData playerData) {
        PlayerID playerID = new PlayerID(UUID.randomUUID().toString());
        Game currentGame = gamesDatabase.getGame(gameID);
        Player newPlayer = new Player(playerID, playerData);
        currentGame.addPlayer(newPlayer);
        return playerID;
    }

    public void saveHalfMap(GameID gameID, PlayerID playerID, ServerHalfMap halfMap) {
        Game currentGame = gamesDatabase.getGame(gameID);
        MapManager mapManager = new MapManager();
        mapManager.saveHalfMap(currentGame, playerID, halfMap);
        currentGame.changeGameState();
    }

    public Game getGame(GameID gameID, PlayerID playerID) {
        Game currentGame = gamesDatabase.getGame(gameID);
        return hider.hideInformationInGameForPlayer(currentGame, playerID);
    }

    public void processInvalidGameAction(GameID gameID, PlayerID exceptionCausePlayerID) {
        Game currentGame = gamesDatabase.getGame(gameID);
        currentGame.setGameState(exceptionCausePlayerID, PlayerGameState.Lost);
    }
}
