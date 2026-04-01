package test;

import main.TrainConsistManagementApp;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class TrainConsistManagementAppTest {

    @Test
    public void testTrainConsistManagementAppInstantiation() {
        TrainConsistManagementApp app = new TrainConsistManagementApp();
        assertNotNull(app);
    }
}