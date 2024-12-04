package client.main.Map;


public class MergedMapSplitter {

    private final int NUM_ROWS = 4;
    private final int NUM_COLS = 9;

    public void applyMapSplittingLogic(Castle myCastle, MergedMap mergedMap) {
        for (var entry : mergedMap.getMap().entrySet()) {
            if (mergedMap.getNUM_COLS() > 9) {
                // Horizontal splitting logic
                if (myCastle.getPosition().get().getX() <= NUM_COLS) {
                    // [c][]
                    boolean isMine = entry.getKey().getX() <= NUM_COLS;
                    entry.getKey().setIsMine(isMine);
                } else {
                    // [][c]
                    boolean isMine = entry.getKey().getX() > NUM_COLS;
                    entry.getKey().setIsMine(isMine);
                }
            } else {
                // Vertical splitting logic
                if (myCastle.getPosition().get().getY() <= NUM_ROWS) {
                    // [c]
                    // []
                    boolean isMine = entry.getKey().getY() <= NUM_ROWS;
                    entry.getKey().setIsMine(isMine);
                } else {
                    // []
                    // [c]
                    boolean isMine = entry.getKey().getY() > NUM_ROWS;
                    entry.getKey().setIsMine(isMine);
                }
            }
        }
    }
}
