import client.main.Map.*;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.RepeatedTest;

public class HalfMapUniquenessTest {
    @Test
    @RepeatedTest(5)
    public void RepeatedlyGenerateTwoHalfMaps_checkForUniqueness_shouldReturnTrue(){
        int count = 0;
        final int TOTAL_MIN = 5;

        HalfMapCreation creator = new HalfMapCreation();

        HalfMap firstHalfMap = creator.createHalfMap();
        HalfMap secondHalfMap = creator.createHalfMap();
        if (firstHalfMap != null && secondHalfMap != null && firstHalfMap.getMap().size() == secondHalfMap.getMap().size()) {
            for (Position position : firstHalfMap.getMap().keySet()) {
                NodeType thisNodeType = firstHalfMap.getMap().get(position);
                NodeType otherNodeType = secondHalfMap.getMap().get(position);

                if (thisNodeType != null && otherNodeType != null && !thisNodeType.equals(otherNodeType)) {
                    count++;
                }
            }
        }

        Assert.assertTrue(count > TOTAL_MIN);
    }
}
