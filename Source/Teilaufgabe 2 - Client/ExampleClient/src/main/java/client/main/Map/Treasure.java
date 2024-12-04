package client.main.Map;

import java.util.Optional;

public class Treasure {
    private Optional<Position> position;

    public boolean isKnown() {
        return true;
    }
    public Treasure() {
        this.position = Optional.empty();
    }

    public Treasure(Position position) {
        this.position = Optional.ofNullable(position);
    }

    public void setPosition(Position position) {
        this.position = Optional.ofNullable(position);
    }

    public Optional<Position> getPosition() {
        return position;
    }

    @Override
    public String toString() {

        if (position.isPresent()) {
            return "Position: (" + position + ")";
        } else {
            return "Position is not available.";
        }
    }
}
