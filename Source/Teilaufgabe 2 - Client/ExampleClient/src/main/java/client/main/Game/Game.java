package client.main.Game;
import client.main.Map.MergedMap;
import client.main.Player.Player;
import client.main.Player.PlayerStatus;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class Game {
    private final PropertyChangeSupport changes = new PropertyChangeSupport(this);

    private Player myPlayer;
    private Player opponentPlayer;
    private MergedMap mergedMap;

    public Game(MergedMap map, Player myPlayer, Player opponentPlayer){
        this.mergedMap=map;
        this.myPlayer=myPlayer;
        this.opponentPlayer=opponentPlayer;
    }
    public Game() {}
    /**
     * Updates the game with new information.
     *
     * @param game The updated game containing new information.
     */
    public void updateGame(Game game) {
        Game oldGame = new Game(mergedMap, myPlayer, opponentPlayer);
        this.myPlayer = game.myPlayer;
        this.opponentPlayer = game.opponentPlayer;
        this.mergedMap = game.mergedMap;

        changes.firePropertyChange("game", oldGame, this);
    }

    public Player getMyPlayer() {
        return this.myPlayer;
    }
    public Player getOpponentPlayer() {
        return this.opponentPlayer;
    }
    public MergedMap getMergedMap(){return this.mergedMap;}

    public boolean isMyTurn() {
        return this.myPlayer.getStatus().equals(PlayerStatus.MustAct);
    }

    public boolean hasEnded() {
        return this.myPlayer.getStatus().equals(PlayerStatus.Won) || this.myPlayer.getStatus().equals(PlayerStatus.Lost);
    }
    @Override
    public String toString() {
        return "Game{" +
                "myPlayer=" + myPlayer +
                ", opponentPlayer=" + opponentPlayer +
                ", mergedMap=" + mergedMap +
                '}';
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        changes.addPropertyChangeListener(listener);
    }
}
