package client.main.Rules;

import client.main.Map.HalfMap;

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
     * Checks if the HalfMap is corresponding to all of the map rules.
     *
     * @param currentHalfMap the HalfMap to check.
     * @return True in case the HalfMap corresponds to all the map rules, otherwise false.
     */
    public boolean checkRules(HalfMap currentHalfMap) {
        for (MapRule rule : mapRules) {
            if (!rule.checkHalfMap(currentHalfMap)) {
                return false;
            }
        }
        return true;
    }
}
