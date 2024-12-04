package server.main;

public class Castle {
    private Position castlePosition;

    public Castle(Position position) {
        this.castlePosition = position;
    }

    public Position getCastlePosition() {
        return castlePosition;
    }

    @Override
    public String toString() {
        return "Castle{" +
                "castlePosition=" + castlePosition +
                '}';
    }
}
