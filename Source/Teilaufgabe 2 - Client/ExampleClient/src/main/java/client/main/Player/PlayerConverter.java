package client.main.Player;

import messagesbase.messagesfromserver.EPlayerGameState;
import messagesbase.messagesfromserver.PlayerState;

public class PlayerConverter {

    public static Player convertToPlayer(PlayerState playerState) {
        PlayerStatus status = mapPlayerGameStateToPlayerStatus(playerState.getState());
        PlayerID playerID = new PlayerID(playerState.getUniquePlayerID());
        return new Player(
                playerState.getFirstName(),
                playerState.getLastName(),
                playerState.getUAccount(),
                status,
                playerState.hasCollectedTreasure(),
                playerID
        );
    }

    private static PlayerStatus mapPlayerGameStateToPlayerStatus(EPlayerGameState playerGameState) {
        switch (playerGameState) {
            case Won:
                return PlayerStatus.Won;
            case Lost:
                return PlayerStatus.Lost;
            case MustAct:
                return PlayerStatus.MustAct;
            case MustWait:
                return PlayerStatus.MustWait;
            default:
                throw new IllegalArgumentException("Unknown player game state: " + playerGameState);
        }
    }
}
