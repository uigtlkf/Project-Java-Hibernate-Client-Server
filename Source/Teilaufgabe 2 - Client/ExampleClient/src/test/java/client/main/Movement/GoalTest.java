package client.main.Movement;
import client.main.Map.*;
import client.main.Player.Player;
import client.main.Player.PlayerID;
import client.main.Player.PlayerStatus;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class GoalTest {

    @Test
    public void testGetGoal_WhenOpponentCastleExists_ReturnsOpponentCastlePosition() {
        MergedMap currentMap = mock(MergedMap.class);

        Map<Position, Node> nodes = new HashMap<>();
        Position opponentCastlePosition = new Position(5, 5);
        nodes.put(opponentCastlePosition, new Node(NodeType.Grass, null, new Castle(), null));
        when(currentMap.getMap()).thenReturn(nodes);

        Player player = new Player("", "", "", PlayerStatus.MustAct, true, new PlayerID("555"));
        Set<Position> visited = Set.of(new Position(1, 1), new Position(2, 2), new Position(3, 3));
        Goal goal = new Goal();
        Position goalPosition = goal.getGoal(currentMap, player, visited);

        assertEquals(opponentCastlePosition, goalPosition);
    }
}