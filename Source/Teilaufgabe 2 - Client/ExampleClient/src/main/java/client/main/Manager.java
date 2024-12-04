package client.main;

import client.main.Exceptions.CustomGameConversionException;
import client.main.Exceptions.ServerConnectionException;
import client.main.Game.Game;
import client.main.Game.GameID;
import client.main.Map.HalfMap;
import client.main.Map.HalfMapCreation;
import client.main.Movement.Movement;
import client.main.Player.PlayerID;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Manager {
    private Communication communication;
    private Game game;
    private HalfMapCreation halfMapCreation;
    private Movement movement;
    private Visualization vis;
    private final long DELAY_MILLISECONDS = 400;

    private boolean sentMap = false;
    private static final Logger logger = Logger.getLogger(Manager.class.getName());

    public Manager(Communication communication) {
        this.communication = communication;
        this.halfMapCreation = new HalfMapCreation();
        this.movement = new Movement();
        this.game = new Game();
        this.vis = new Visualization(game);
    }

    /**
     * Starts the game by registering a player, creation a HalfMap and starting the game loop.
     */
    public void startGame() {
        try {
            //creation of Player
            PlayerID playerId = this.communication.sendPlayerRegistration("Danara", "Abushinova", "danaraa98");

            //creation of game loop
            int round = 0;
            while(true) {
                game.updateGame(this.communication.getLatestGame());
                round += 1;
                if(game.isMyTurn()) {
                    if(!sentMap) {
                        sendMap();
                    } else {
                        move();
                    }
                }
                if(game.hasEnded()) {
                    System.out.println("Game has ended");
                    break;
                }
                System.out.println("Round: " + round);
                try {
                    Thread.sleep(DELAY_MILLISECONDS);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } catch(ServerConnectionException | CustomGameConversionException e) {

            logger.log(Level.SEVERE, "Server connection exception occurred", e);
            System.out.println(e.getMessage());
        }
    }

    /**
     * Sends the next movement action in case both players positions are not null.
     */
    private void move() throws ServerConnectionException {
        if(game.getMyPlayer() != null && game.getOpponentPlayer() != null) {
            communication.sendMoveAction(movement.getNextAction(game.getMyPlayer(), game.getMergedMap()));
        }
//        movement.getNextAction(, game.getMergedMap());
    }

    /**
     * Creates a HalfMap and sends it to the server.
     */
    private void sendMap() throws ServerConnectionException {
        HalfMap createdHalfMap = halfMapCreation.createHalfMap();
        this.communication.sendPlayerHalfMap(createdHalfMap);
        sentMap = true;
    }
}
