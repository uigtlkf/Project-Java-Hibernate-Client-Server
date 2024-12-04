package client.main.Rules;

import client.main.Map.Castle;
import client.main.Map.HalfMap;

public class CheckCastle extends MapRule {

    /**
     * Checks the HalfMap on having exactly one Castle.
     *
     * @param currentMap The HalfMap to check.
     * @return True if the HalfMap has exactly one Castle, otherwise false.
     */
    @Override
    public boolean checkHalfMap(HalfMap currentMap) {
        Castle castle = currentMap.getCastle();
        return castle != null && castle.getPosition().isPresent();
    }
}
