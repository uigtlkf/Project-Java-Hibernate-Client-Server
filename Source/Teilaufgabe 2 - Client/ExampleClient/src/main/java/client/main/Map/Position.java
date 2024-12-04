package client.main.Map;
import java.util.Objects;

public class Position {
    private int x;
    private int y;
    private boolean isMine;

    public Position(int x, int y){
        this.x=x;
        this.y=y;
        this.isMine=false;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Position position = (Position) obj;
        return x == position.x && y == position.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }


    @Override
    public String toString() {
        return "(x: "+ x +"; y: "+y+")";
    }


    public void setIsMine(boolean isMine) {
        this.isMine=isMine;
    }
    public boolean isMine(){
        return this.isMine;
    }

}
