package client.main.Movement;

import java.util.List;

public class Way {
    private List<MoveAction> way;

    public Way(List<MoveAction> way) {
        this.way = way;
    }

    public MoveAction getNextAction() {
        return way.get(0);
    }
    public void removeNextAction() {
        way.remove(0);
    }

    public void addAction(MoveAction moveAction) {
        way.add(moveAction);
    }

    public List<MoveAction> getActions() {
        return way;
    }
}
