package server.main.Rules;


import messagesbase.messagesfromclient.PlayerHalfMap;
import messagesbase.messagesfromclient.PlayerHalfMapNode;

public class CheckCastle extends MapRule {
    private final int CASTLE_NUM;

    public CheckCastle(int CASTLE_NUM){
        this.CASTLE_NUM=CASTLE_NUM;
    }

    /**
     * Checks the PlayerHalfMap on having exactly one Castle.
     *
     * @param currentMap The PlayerHalfMap to check.
     * @return True if the PlayerHalfMap has exactly one Castle, otherwise false.
     */
    @Override
    public boolean checkHalfMap(PlayerHalfMap currentMap) {
        int castleCount=0;
        for (PlayerHalfMapNode node : currentMap.getMapNodes()) {
            if (node.isFortPresent()) {
                castleCount++;
            }
        }
       return castleCount==CASTLE_NUM;
    }
}
