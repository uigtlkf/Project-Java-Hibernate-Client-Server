package server.main.Rules;
import messagesbase.messagesfromclient.PlayerHalfMap;

public abstract class MapRule {

    public abstract boolean checkHalfMap(PlayerHalfMap currentMap);
}
