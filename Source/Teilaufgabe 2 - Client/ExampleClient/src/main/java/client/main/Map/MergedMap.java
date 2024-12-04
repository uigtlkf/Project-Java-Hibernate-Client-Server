package client.main.Map;

import java.util.HashMap;
import java.util.Map;

public class MergedMap {
    private int NUM_ROWS=4;
    private int NUM_COLS=9;
    private final int NUM_ROWS_extended=9;
    private final int NUM_COLS_extended=19;

    private Map<Position, Node> mergedMap;

    public MergedMap() {
        this.mergedMap = new HashMap<>();
    }

    public MergedMap(Map<Position, Node> mergedMap) {
        this.mergedMap = mergedMap;
    }

    public Map<Position, Node> getMap() {
        return mergedMap;
    }

    public int getNUM_ROWS() {
        return NUM_ROWS;
    }

    public int getNUM_COLS() {
        return NUM_COLS;
    }


    public boolean isXExtended(){
        if(getMap().get(new Position(NUM_COLS_extended,0))!=null){
            return true;
        }
        else{
            return false;
        }
    }
    public boolean isYExtended(){
        if(getMap().get(new Position(0,NUM_ROWS_extended))!=null){
            return true;
        }
        else{
            return false;
        }
    }
    /**
     * Checks and updates the number of columns (NUM_COLS) or rows (NUM_ROWS) based on the extended map dimensions.
     * If the map is extended along the X-axis, NUM_COLS is updated; otherwise, if extended along the Y-axis, NUM_ROWS is updated.
     */
    public void checkExtensionType(){
        if(isXExtended()){
            NUM_COLS=NUM_COLS_extended;
        }
        else if(isYExtended()){
            NUM_ROWS=NUM_ROWS_extended;
        }
    }

}
