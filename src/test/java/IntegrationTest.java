import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class IntegrationTest {


    @Test
    void testSystemRunsCustomParams() throws InterruptedException {
        Settings settings = new Settings();
        MockGUI display = new MockGUI(settings);
        new EvolutionaryModel(settings, display);

        //set params here
        display.setCrossover(true);
        display.setPopulationSize(125);
        display.setGenomeLength(125);

        //end set params

        display.setMaxGen(2); // for testability
        display.startRun();
        Thread.sleep(2000);
        Assertions.assertTrue(display.isFinished());
    }
}
