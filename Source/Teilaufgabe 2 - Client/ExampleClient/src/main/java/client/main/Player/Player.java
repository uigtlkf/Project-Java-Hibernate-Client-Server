package client.main.Player;

import client.main.Map.Position;

public class Player {
    private Position playerPosition;
    private String firstname;
    private String lastname;
    private String nickname;
    private PlayerStatus status;
    private boolean collectedTreasure;
    private PlayerID playerID;

    public Player(String firstname, String lastname, String nickname, PlayerStatus status, boolean collectedTreasure, PlayerID playerID) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.nickname = nickname;
        this.status = status;
        this.collectedTreasure = collectedTreasure;
        this.playerID = playerID;
    }

    public Position getPlayerPosition(){
        return this.playerPosition;
    }


    public void setPlayerPosition(Position playerPosition) {

            this.playerPosition = playerPosition;

    }


    public PlayerStatus getStatus() {
        return status;
    }


    public boolean isCollectedTreasure() {
        return collectedTreasure;
    }

    @Override
    public String toString() {
        return "Position: " + this.playerPosition + ", Firstname: " + firstname + ", Lastname: " + lastname + ", Nickname: " + nickname + ", PlayerID: " + this.playerID;
    }


}
