package client.main.Movement;

import client.main.Exceptions.UnreachableMoveError;
import client.main.Map.Position;

public enum MoveAction {
    Left(-1, 0),
    Right(1, 0),
    Up(0, -1),
    Down(0, 1);

    private final int offsetX;
    private final int offsetY;

    MoveAction(int offsetX, int offsetY) {
        this.offsetX = offsetX;
        this.offsetY = offsetY;
    }

    public int getOffsetX() {
        return offsetX;
    }

    public int getOffsetY() {
        return offsetY;
    }

    /**
     * Determines the MoveAction required to move from one position to another.
     *
     * @param from The starting position.
     * @param to   The target position.
     * @return The MoveAction representing the movement direction from 'from' to 'to'.
     * @throws UnreachableMoveError If the target position cannot be reached from the starting position with a valid MoveAction.
     */
    protected static MoveAction getMoveAction(Position from, Position to) {
        int offsetX = to.getX() - from.getX();
        int offsetY = to.getY() - from.getY();

        for (MoveAction action : MoveAction.values()) {
            if (action.getOffsetX() == offsetX && action.getOffsetY() == offsetY) {
                return action;
            }
        }
        throw new UnreachableMoveError("Invalid move action");
    }
}

