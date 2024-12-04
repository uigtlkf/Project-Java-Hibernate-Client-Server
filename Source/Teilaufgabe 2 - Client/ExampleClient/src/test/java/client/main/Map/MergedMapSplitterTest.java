package client.main.Map;

import org.junit.jupiter.api.Test;
import java.util.HashMap;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MergedMapSplitterTest {

    @Test
    public void testMapSplittingLogic() {
        MergedMapSplitter mergedMapSplitter = new MergedMapSplitter();
        Castle myCastle = new Castle(new Position(5, 3));
        Castle opponentCastle = new Castle(new Position(10, 6));

        Map<Position, Node> testMap = new HashMap<>();
        testMap.put(new Position(5, 3), new Node(NodeType.Grass, myCastle, null, null));
        testMap.put(new Position(10, 6), new Node(NodeType.Grass, opponentCastle, null, null));

        MergedMap mergedMap = new MergedMap(testMap);
        mergedMapSplitter.applyMapSplittingLogic(myCastle, mergedMap);

        assertTrue(mergedMap.getMap().get(new Position(5, 3)).getNodeType() == NodeType.Grass);
        assertTrue(mergedMap.getMap().get(new Position(10, 6)).getNodeType() == NodeType.Grass);

        assertTrue(mergedMap.getMap().keySet().stream().filter(p -> p.equals(new Position(5, 3))).findAny().get().isMine());
        assertTrue(!mergedMap.getMap().keySet().stream().filter(p -> p.equals(new Position(10, 6))).findAny().get().isMine());
    }
}
