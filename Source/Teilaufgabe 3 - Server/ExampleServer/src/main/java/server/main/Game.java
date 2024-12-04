package server.main;

import java.util.Random;
import java.util.UUID;

public class Game {
    private GameMap gameMap;
    private Player firstPlayer;
    private Player secondPlayer;
    private String gameStateIdentifier;

    public Game() {
        firstPlayer = new Player();
        secondPlayer = new Player();
        gameMap = new GameMap();
        this.gameStateIdentifier = UUID.randomUUID().toString();
    }

    public Game(GameMap gameMap, Player firstPlayer, Player secondPlayer, String gameStateIdentifier) {
        this.gameMap = gameMap;
        this.firstPlayer = firstPlayer;
        this.secondPlayer = secondPlayer;
        this.gameStateIdentifier = gameStateIdentifier;
    }

    public void addPlayer(Player player) {
        this.gameStateIdentifier = UUID.randomUUID().toString();

        if(!firstPlayer.isRegistered()) {
            firstPlayer = player;
        } else if(!secondPlayer.isRegistered()) {
            secondPlayer = player;
        } else {
            throw new IllegalArgumentException("Two players were registered, but tried to add third one");
        }

        if(secondPlayer.isRegistered()) {
            changeGameState();
        }
    }

    public void changeGameState() {
        this.gameStateIdentifier = UUID.randomUUID().toString();
        if(secondPlayer.getState().equals(firstPlayer.getState())) {
            Random r = new Random();
            boolean firstPlayerActs = r.nextBoolean();
            setGameState(firstPlayer.getPlayerID(),  firstPlayerActs ? PlayerGameState.MustAct : PlayerGameState.MustWait);
        } else {
            setGameState(firstPlayer.getPlayerID(), secondPlayer.getState());
        }
    }

    public Player getEnemyPlayerByID(PlayerID playerID) {
        if(firstPlayer.getPlayerID().equals(playerID)) {
            return secondPlayer;
        } else if(secondPlayer.getPlayerID().equals(playerID)) {
            return firstPlayer;
        } else {
            throw new IllegalArgumentException("No player with provided playerID");
        }
    }

    public Player getPlayerByID(PlayerID playerID) {
        if(firstPlayer.getPlayerID().equals(playerID)) {
            return firstPlayer;
        } else if(secondPlayer.getPlayerID().equals(playerID)) {
            return secondPlayer;
        } else {
            throw new IllegalArgumentException("No player with provided playerID");
        }
    }

    public boolean areBothMapsSent() {
        return gameMap.isComplete();
    }

    public Player getFirstPlayer() {
        return firstPlayer;
    }

    public Player getSecondPlayer() {
        return secondPlayer;
    }

    public void setGameMap(GameMap gameMap) {
        this.gameMap = gameMap;
    }

    public GameMap getGameMap() {
        return this.gameMap;
    }

    public ServerHalfMap getHalfMapById(PlayerID playerID) {
        return this.gameMap.getHalfById(playerID);
    }

    public String getGameStateIdentifier() {
        return gameStateIdentifier;
    }

    public void setGameState(PlayerID playerID, PlayerGameState state) {
        getPlayerByID(playerID).setState(state);
        getEnemyPlayerByID(playerID).setState(state.opposite());
    }
}
