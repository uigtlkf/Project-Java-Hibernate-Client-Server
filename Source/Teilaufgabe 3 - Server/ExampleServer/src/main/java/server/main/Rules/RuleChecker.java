package server.main.Rules;
import messagesbase.messagesfromclient.PlayerHalfMap;

import java.util.ArrayList;
import java.util.List;

public class RuleChecker {
    private List<MapRule> mapRules;

    public RuleChecker() {
        this.mapRules = new ArrayList<>();
    }

    public void addMapRule(MapRule mapRule) {
        mapRules.add(mapRule);
    }

    /**
     * Checks if the PlayerHalfMap is corresponding to all of the map rules.
     *
     * @param currentHalfMap the PlayerHalfMap to check.
     * @return True in case the PlayerHalfMap corresponds to all the map rules, otherwise false.
     */
    public boolean checkRules(PlayerHalfMap currentHalfMap) {
        for (MapRule rule : mapRules) {
            if (!rule.checkHalfMap(currentHalfMap)) {
                return false;
            }
        }
        return true;
    }
}
