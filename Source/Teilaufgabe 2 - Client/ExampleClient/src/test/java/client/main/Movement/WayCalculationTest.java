package client.main.Movement;

import client.main.Exceptions.NoPossibleWayError;
import client.main.Map.*;
import org.junit.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class WayCalculationTest {
    @ParameterizedTest
    @MethodSource("getMapWithNoPossiblePath")
    public void givenMapWithNoPossiblePath_ShouldThrowAnError(MergedMap map, Position from, Position to) {

        WayCalculation calculation = new WayCalculation();
        assertThrows(NoPossibleWayError.class, () -> calculation.calculateWay(map, from, to));
    }

    private static Stream<Arguments> getMapWithNoPossiblePath() {

        Map<Position, Node> nodes = new HashMap<>();
        nodes.put(new Position(4, 4), new Node(NodeType.Grass, new Castle(), new Castle(), new Treasure()));
        nodes.put(new Position(5, 4), new Node(NodeType.Grass, new Castle(), new Castle(), new Treasure()));
        nodes.put(new Position(6, 4), new Node(NodeType.Grass, new Castle(), new Castle(), new Treasure()));
        nodes.put(new Position(7, 4), new Node(NodeType.Water, new Castle(), new Castle(), new Treasure()));
        nodes.put(new Position(8, 4), new Node(NodeType.Grass, new Castle(), new Castle(), new Treasure()));
        MergedMap map = new MergedMap(nodes);
        return Stream.of(Arguments.of(map, new Position(4, 4), new Position(8, 4)));
    }

    @Test
    //change to what we have and what we need to get
    public void testCalculateWay() {
        Map<Position, Node> nodes = new HashMap<>();
        nodes.put(new Position(0, 0), new Node(NodeType.Grass, new Castle(), new Castle(), new Treasure()));
        nodes.put(new Position(1, 0), new Node(NodeType.Grass, new Castle(), new Castle(), new Treasure()));
        nodes.put(new Position(2, 0), new Node(NodeType.Grass, new Castle(), new Castle(), new Treasure()));
        nodes.put(new Position(2, 1), new Node(NodeType.Grass, new Castle(), new Castle(), new Treasure()));
        nodes.put(new Position(2, 2), new Node(NodeType.Grass, new Castle(), new Castle(), new Treasure()));
        MergedMap map = new MergedMap(nodes);

        Position playerPosition = new Position(0, 0);
        Position destination = new Position(2, 2);

        WayCalculation wayCalculation = new WayCalculation();
        Way way = wayCalculation.calculateWay(map, playerPosition, destination);
        Way expectedWay = new Way(new ArrayList<>());

        expectedWay.addAction(MoveAction.Right);
        expectedWay.addAction(MoveAction.Right);
        expectedWay.addAction(MoveAction.Down);
        expectedWay.addAction(MoveAction.Down);

        assertEquals(expectedWay.getActions(), way.getActions());
    }
}
