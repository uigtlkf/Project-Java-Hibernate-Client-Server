package client.main.Movement;

import client.main.Map.*;
import client.main.Player.Player;
import client.main.Player.PlayerID;
import client.main.Player.PlayerStatus;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

import java.util.*;
import java.util.stream.Stream;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
public class MovementTest {

    @ParameterizedTest
    @MethodSource("getPlayerAndMap")
    public void givenMountainWithPlayerSurroundedByGrass_shouldThrowNoUnvisitedException(Player player, MergedMap map) {
        Movement movement = new Movement();
        Assert.assertEquals(0, movement.getVisited().size());
        assertThrows(IllegalArgumentException.class, () -> movement.getNextAction(player, map));
    }

    @ParameterizedTest
    @MethodSource("getPlayerAndMap_ifPlayerHasNotCollectedTreasure")
    public void testPlayerGoesTowardsTreasure(Player player, MergedMap map) {
        Movement movement = new Movement();
        MoveAction nextAction = movement.getNextAction(player, map);
        assertEquals(MoveAction.Right, nextAction);
    }


    @ParameterizedTest
    @MethodSource("getPlayerAndMap_ifPlayerHasCollectedHisTreasure")
    public void testPlayerGoesTowardsOpponentsCastle_InCaseAlreadyGotHisTreasure(Player player, MergedMap map) {
        Movement movement = new Movement();
        MoveAction nextAction = movement.getNextAction(player, map);
        assertEquals(MoveAction.Right, nextAction);
    }
    private static Stream<Arguments> getPlayerAndMap() {
        Player player = new Player("", "", "", PlayerStatus.MustAct, true, new PlayerID("555"));
        player.setPlayerPosition(new Position(5, 5));
        Map<Position, Node> nodes = new HashMap<>();

        nodes.put(new Position(4, 4), new Node(NodeType.Grass, new Castle(), new Castle(), new Treasure()));
        nodes.put(new Position(5, 4), new Node(NodeType.Grass, new Castle(), new Castle(), new Treasure()));
        nodes.put(new Position(6, 4), new Node(NodeType.Grass, new Castle(), new Castle(), new Treasure()));

        nodes.put(new Position(4, 5), new Node(NodeType.Grass, new Castle(), new Castle(), new Treasure()));
        nodes.put(new Position(5, 5), new Node(NodeType.Mountain, new Castle(), new Castle(), new Treasure()));
        nodes.put(new Position(6, 5), new Node(NodeType.Grass, new Castle(), new Castle(), new Treasure(new Position(6,5))));

        nodes.put(new Position(4, 6), new Node(NodeType.Grass, new Castle(), new Castle(), new Treasure()));
        nodes.put(new Position(5, 6), new Node(NodeType.Grass, new Castle(), new Castle(), new Treasure()));
        nodes.put(new Position(6, 6), new Node(NodeType.Grass, new Castle(), new Castle(), new Treasure()));

        return Stream.of(Arguments.of(player, new MergedMap(nodes)));
    }
    private static Stream<Arguments> getPlayerAndMap_ifPlayerHasNotCollectedTreasure() {
        Player player = new Player("", "", "", PlayerStatus.MustAct, false, new PlayerID("555"));
        player.setPlayerPosition(new Position(5, 5));
        Map<Position, Node> nodes = new HashMap<>();

        nodes.put(new Position(4, 4), new Node(NodeType.Grass, new Castle(), new Castle(), new Treasure()));
        nodes.put(new Position(5, 4), new Node(NodeType.Grass, new Castle(), new Castle(), new Treasure()));
        nodes.put(new Position(6, 4), new Node(NodeType.Grass, new Castle(), new Castle(), new Treasure()));

        nodes.put(new Position(4, 5), new Node(NodeType.Grass, new Castle(), new Castle(), new Treasure()));
        nodes.put(new Position(5, 5), new Node(NodeType.Mountain, new Castle(), new Castle(), new Treasure()));
        nodes.put(new Position(6, 5), new Node(NodeType.Grass, new Castle(), new Castle(), new Treasure(new Position(6,5))));

        nodes.put(new Position(4, 6), new Node(NodeType.Grass, new Castle(), new Castle(), new Treasure()));
        nodes.put(new Position(5, 6), new Node(NodeType.Grass, new Castle(), new Castle(), new Treasure()));
        nodes.put(new Position(6, 6), new Node(NodeType.Grass, new Castle(), new Castle(), new Treasure()));

        return Stream.of(Arguments.of(player, new MergedMap(nodes)));
    }
    private static Stream<Arguments> getPlayerAndMap_ifPlayerHasCollectedHisTreasure() {
        Player player = new Player("", "", "", PlayerStatus.MustAct, true, new PlayerID("555"));
        player.setPlayerPosition(new Position(5, 5));
        Map<Position, Node> nodes = new HashMap<>();

        nodes.put(new Position(4, 4), new Node(NodeType.Grass, new Castle(), new Castle(), new Treasure()));
        nodes.put(new Position(5, 4), new Node(NodeType.Grass, new Castle(), new Castle(), new Treasure()));
        nodes.put(new Position(6, 4), new Node(NodeType.Grass, new Castle(), new Castle(), new Treasure()));

        nodes.put(new Position(4, 5), new Node(NodeType.Grass, new Castle(), new Castle(), new Treasure()));
        nodes.put(new Position(5, 5), new Node(NodeType.Mountain, new Castle(), new Castle(), new Treasure()));
        nodes.put(new Position(6, 5), new Node(NodeType.Grass, new Castle(), new Castle(new Position(6, 5)), new Treasure()));

        nodes.put(new Position(4, 6), new Node(NodeType.Grass, new Castle(), new Castle(), new Treasure()));
        nodes.put(new Position(5, 6), new Node(NodeType.Grass, new Castle(), new Castle(), new Treasure()));
        nodes.put(new Position(6, 6), new Node(NodeType.Grass, new Castle(), new Castle(), new Treasure()));

        return Stream.of(Arguments.of(player, new MergedMap(nodes)));
    }

}
