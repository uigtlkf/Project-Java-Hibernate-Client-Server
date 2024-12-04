package client.main.Movement;

import client.main.Exceptions.UnreachableMoveError;
import client.main.Map.Position;
import client.main.Movement.MoveAction;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class MoveActionTest {
    @Test
    public void givenValidDestination_shouldNotThrowUnreachableMoveError() {
        Position from = new Position(5, 5);
        Position validDestination = new Position(6, 5);
        MoveAction moveAction = MoveAction.getMoveAction(from, validDestination);
        Assertions.assertEquals(MoveAction.Right, moveAction);
    }

    @ParameterizedTest
    @MethodSource("getUnreachableDestinations")
    public void givenUnreachableDestination_shouldThrowUnreachableMoveError(Position from, Position unreachableDestination) {
        assertThrows(UnreachableMoveError.class, () -> MoveAction.getMoveAction(from, unreachableDestination));
    }

    private static Stream<Arguments> getUnreachableDestinations() {
        return Stream.of(
                Arguments.of(new Position(5, 5), new Position(6, 6)),
                Arguments.of(new Position(5, 5), new Position(4, 6)),
                Arguments.of(new Position(5, 5), new Position(4, 4)),
                Arguments.of(new Position(5, 5), new Position(3, 3)),
                Arguments.of(new Position(5, 5), new Position(2, 6))
        );
    }

}
