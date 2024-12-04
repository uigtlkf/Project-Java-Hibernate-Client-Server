package server.main;

public enum PlayerGameState {
    Won,
    Lost,
    MustAct,
    MustWait;

    public PlayerGameState opposite() {
        if(this.equals(Won)) {
            return Lost;
        } else if(this.equals(Lost)) {
            return Won;
        } else if(this.equals(MustAct)) {
            return MustWait;
        } else if(this.equals(MustWait)) {
            return MustAct;
        } else {
            throw new IllegalArgumentException("Unsupported player game state");
        }
    }
}
