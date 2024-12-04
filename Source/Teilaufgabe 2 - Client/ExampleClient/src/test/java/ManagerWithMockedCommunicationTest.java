import client.main.Communication;
import client.main.Exceptions.CustomGameConversionException;
import client.main.Exceptions.ServerConnectionException;
import client.main.Game.Game;
import client.main.Manager;
import client.main.Map.MergedMap;
import client.main.Player.Player;
import client.main.Player.PlayerID;
import client.main.Player.PlayerStatus;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class ManagerWithMockedCommunicationTest {
    @Test
    public void givenCommunicationMocked_WhenManagerStarts_ThenCommunicationMethodsAreCalled() {
        Communication mocked = Mockito.mock(Communication.class);

        try {
            Mockito.when(mocked.sendPlayerRegistration(anyString(), anyString(), anyString())).thenReturn(new PlayerID("555"));
            Mockito.when(mocked.getLatestGame()).thenReturn(new Game(new MergedMap(),
                    new Player("", "", "", PlayerStatus.Lost, false, new PlayerID("555")),
                    new Player("", "", "", PlayerStatus.Won, false, new PlayerID("333"))));

            Manager manager = new Manager(mocked);
            manager.startGame();
            verify(mocked, times(1)).sendPlayerRegistration(anyString(), anyString(), anyString());
            verify(mocked, times(1)).getLatestGame();
        } catch (ServerConnectionException | CustomGameConversionException e) {
            Assert.fail("Unexpected server connection exception: " + e.getMessage());
        }
    }
}
