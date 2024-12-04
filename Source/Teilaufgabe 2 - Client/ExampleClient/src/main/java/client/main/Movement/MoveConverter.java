package client.main.Movement;

import client.main.Player.PlayerID;
import messagesbase.messagesfromclient.EMove;
import messagesbase.messagesfromclient.PlayerMove;

public class MoveConverter {

    public static PlayerMove convertMoveAction(MoveAction nextAction, PlayerID playerID) {
        return PlayerMove.of(playerID.getPlayerID(), convertToEMove(nextAction));
    }

    private static EMove convertToEMove(MoveAction moveAction) {
        switch (moveAction) {
            case Up:
                return EMove.Up;
            case Left:
                return EMove.Left;
            case Right:
                return EMove.Right;
            case Down:
                return EMove.Down;
            default:
                throw new IllegalArgumentException("Unsupported MoveAction: " + moveAction);
        }
    }
}
