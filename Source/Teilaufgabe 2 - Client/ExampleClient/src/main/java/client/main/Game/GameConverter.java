package client.main.Game;

import client.main.Exceptions.CustomGameConversionException;
import client.main.Manager;
import client.main.Map.MapConverter;
import client.main.Map.MergedMap;
import client.main.Map.Position;
import client.main.Player.Player;
import client.main.Player.PlayerConverter;
import client.main.Player.PlayerID;
import messagesbase.messagesfromserver.EPlayerPositionState;
import messagesbase.messagesfromserver.FullMap;
import messagesbase.messagesfromserver.GameState;

import java.util.logging.Level;
import java.util.logging.Logger;

public class GameConverter {
    private static final Logger logger = Logger.getLogger(Manager.class.getName());

    public static Game convertToGame(GameState gameState, PlayerID myPlayerID) throws CustomGameConversionException {
        try {
            FullMap fullMap = gameState.getMap();
            var playerStates = gameState.getPlayers();

            MergedMap map = MapConverter.convertToMergedMap(fullMap);

            Player myPlayer = PlayerConverter.convertToPlayer(playerStates.stream().filter(p -> p.getUniquePlayerID().equals(myPlayerID.getPlayerID())).findFirst().get());
            Player opponentPlayer = null;
            if (playerStates.size() == 2) {
                opponentPlayer = PlayerConverter.convertToPlayer(playerStates.stream().filter(p -> !(p.getUniquePlayerID().equals(myPlayerID.getPlayerID()))).findFirst().get());
                var myPlayerPosition = fullMap.getMapNodes().stream().filter(n -> n.getPlayerPositionState().representsMyPlayer()).findFirst();
                if (myPlayerPosition.isPresent()) {
                    myPlayer.setPlayerPosition(new Position(myPlayerPosition.get().getX(), myPlayerPosition.get().getY()));

                    var opponentPlayerPosition = fullMap.getMapNodes().stream().filter(n -> n.getPlayerPositionState().equals(EPlayerPositionState.EnemyPlayerPosition) || n.getPlayerPositionState().equals(EPlayerPositionState.BothPlayerPosition)).findFirst();
                    if (opponentPlayerPosition.isPresent()) {
                        opponentPlayer.setPlayerPosition(new Position(opponentPlayerPosition.get().getX(), opponentPlayerPosition.get().getY()));
                    }
                }
            }
            return new Game(map, myPlayer, opponentPlayer);
        }
        catch(Exception e){
            logger.log(Level.SEVERE, "Error during conversion: " + e.getMessage(), e);
            throw new CustomGameConversionException("Error occurred during game conversion", e);
        }
    }
}
