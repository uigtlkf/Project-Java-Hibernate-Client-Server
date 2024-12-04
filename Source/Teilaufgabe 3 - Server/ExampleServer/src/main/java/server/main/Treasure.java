package server.main;

public class Treasure {
    private Position treasurePosition;

    public Treasure(Position treasurePosition) {
        this.treasurePosition = treasurePosition;
    }

    public Position getTreasurePosition() {
        return treasurePosition;
    }
}
