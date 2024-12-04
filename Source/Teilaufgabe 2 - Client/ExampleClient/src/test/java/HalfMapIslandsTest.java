import client.main.Map.Castle;
import client.main.Map.HalfMap;
import client.main.Map.NodeType;
import client.main.Map.Position;
import client.main.Rules.CheckIslands;
import client.main.Rules.MapRule;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class HalfMapIslandsTest {
    @Test
    public void GivenHalfMapWithIslands_shouldReturnFalse(){

        Map<Position, NodeType> map=new HashMap<>();
        map.put(new Position(0,0), NodeType.Grass);
        map.put(new Position(0,1), NodeType.Water);
        map.put(new Position(0,2), NodeType.Grass);
        map.put(new Position(1,0), NodeType.Grass);
        map.put(new Position(2,0), NodeType.Grass);
        map.put(new Position(1,0), NodeType.Grass);
        map.put(new Position(1,1), NodeType.Water);
        map.put(new Position(1,2), NodeType.Grass);
        map.put(new Position(2,0), NodeType.Grass);
        map.put(new Position(2,1), NodeType.Water);
        map.put(new Position(2,2), NodeType.Grass);
        HalfMap halfMap=new HalfMap(map, new Castle());

        MapRule checker=new CheckIslands();
        Assert.assertFalse(checker.checkHalfMap(halfMap));
    }
}
