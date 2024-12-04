package server.main;

import messagesbase.UniqueGameIdentifier;
import messagesbase.UniquePlayerIdentifier;
import messagesbase.messagesfromclient.PlayerHalfMap;
import server.exceptions.BasicValidationFailedException;
import server.exceptions.GameActionValidationFailedException;
import server.main.Rules.*;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class CredentialsValidator {

    private final int NUM_ROWS = 4;
    private final int NUM_COLS = 9;
    private final int MAX_PLAYERS = 2;
    private final int MAX_GAMES = 99;
    private final int TERRAINS_COUNT=50;
    private final int CASTLE_NUM = 1;
    private final int MIN_GRASS_NUM = 24;
    private final int MIN_MOUNTAINS_NUM = 5;
    private final int MIN_WATER_NUM=7;
    private final int MAX_RIGHT_WATER_NUM=2;
    private final int MAX_LEFT_WATER_NUM=2;
    private final int MAX_UPPER_WATER_NUM=4;
    private final int MAX_LOWER_WATER_NUM=4;

    private LinkedHashMap<UniqueGameIdentifier, List<UniquePlayerIdentifier>> credentials;
    private LinkedHashMap<UniqueGameIdentifier, List<UniquePlayerIdentifier>> playersWithMaps;
    private RuleChecker ruleChecker;

    public CredentialsValidator() {
        credentials = new LinkedHashMap<>();
        playersWithMaps = new LinkedHashMap<>();
        ruleChecker = new RuleChecker();


        MapRule mapRuleCastle = new CheckCastle(CASTLE_NUM);
        MapRule mapRuleGrass = new CheckGrass(MIN_GRASS_NUM);
        MapRule mapRuleIslands = new CheckIslands(NUM_ROWS, NUM_COLS);
        MapRule mapRuleMountains = new CheckMountains(MIN_MOUNTAINS_NUM);
        MapRule mapRuleWater = new CheckWater(MIN_WATER_NUM);
        MapRule mapRuleWaterBorders = new CheckWaterBorders(MAX_RIGHT_WATER_NUM,MAX_LEFT_WATER_NUM,MAX_UPPER_WATER_NUM,MAX_LOWER_WATER_NUM
                ,NUM_ROWS, NUM_COLS);

        ruleChecker.addMapRule(mapRuleCastle);
        ruleChecker.addMapRule(mapRuleGrass);
        ruleChecker.addMapRule(mapRuleIslands);
        ruleChecker.addMapRule(mapRuleMountains);
        ruleChecker.addMapRule(mapRuleWater);
        ruleChecker.addMapRule(mapRuleWaterBorders);
    }

    public boolean containsGameID(UniqueGameIdentifier gameID) {
        if(!credentials.containsKey(gameID)) {
            throw new BasicValidationFailedException("Unknown GameID", "There is no game with provided gameID=" + gameID.toString());
        }
        return true;
    }

    public void addGameID(UniqueGameIdentifier gameID) {
        if(credentials.size() >= MAX_GAMES) {
            UniqueGameIdentifier nextGameID = credentials.keySet().iterator().next();
            removeGameID(nextGameID);
        }
        credentials.put(gameID, new ArrayList<>());
        playersWithMaps.put(gameID, new ArrayList<>());
    }

    public void removeGameID(UniqueGameIdentifier gameID) {
        credentials.remove(gameID);
        playersWithMaps.remove(gameID);
    }

    public void addPlayerID(UniqueGameIdentifier gameID, UniquePlayerIdentifier playerID) {
        var players = credentials.get(gameID);
        if(players.size() == MAX_PLAYERS) {
            throw new BasicValidationFailedException("Max # of players", "Max of players reached for gameID=" + gameID.getUniqueGameID() + ", playerID=" + playerID.getUniquePlayerID());
        }
        players.add(playerID);
    }

    public boolean isNewPlayerAllowed(UniqueGameIdentifier gameID) {
        if(!(credentials.get(gameID).size() < MAX_PLAYERS)) {
            throw new BasicValidationFailedException("Max # of players", "Max of players reached for gameID=" + gameID.getUniqueGameID());
        }
        return true;
    }

    public boolean isPlayerRegistered(UniqueGameIdentifier gameID, UniquePlayerIdentifier playerID) {
        if(!credentials.get(gameID).contains(playerID)) {
            throw new BasicValidationFailedException("Player is not registered", "No player with playerID="+playerID.getUniquePlayerID() +" in game with gameID=" + gameID.getUniqueGameID());
        }
        return true;
    }

    private boolean isMapSent(UniqueGameIdentifier gameID, UniquePlayerIdentifier playerID) {
        if(playersWithMaps.get(gameID).contains(playerID)) {
            throw new GameActionValidationFailedException(gameID, playerID, "Map was already sent");
        }
        return true;
    }

    public boolean addNewMap(UniqueGameIdentifier gameID, PlayerHalfMap halfMap) {
        isPlayerRegistered(gameID, halfMap);
        areAllPlayersRegistered(gameID);
        isMapSent(gameID, halfMap);
        playersWithMaps.get(gameID).add(halfMap);
        return true;
    }

    private boolean areAllPlayersRegistered(UniqueGameIdentifier gameID) {
        if(credentials.get(gameID).size() != MAX_PLAYERS) {
            throw new BasicValidationFailedException("Game is not yet full", "Both players must be registered before sending the maps");
        }
        return true;
    }
    public boolean validateMap(UniqueGameIdentifier gameID,PlayerHalfMap map){
        if(map.getMapNodes().size()!=TERRAINS_COUNT || !checkRules(map)){
            throw new GameActionValidationFailedException(gameID, map, "PlayerHalfMap must correspond to the defined HalfMap creation rules");
        }
        return true;
    }

    private boolean checkRules(PlayerHalfMap map) {
        return ruleChecker.checkRules(map);
    }
}
