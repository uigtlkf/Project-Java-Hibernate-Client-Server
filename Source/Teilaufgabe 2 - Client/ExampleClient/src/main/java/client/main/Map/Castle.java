package client.main.Map;

import java.util.Optional;

public class Castle {
    private Optional<Position> position;

    public Castle() {
        this.position = Optional.empty();
    }

    public Castle(Position position) {
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

