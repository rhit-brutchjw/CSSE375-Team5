import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class SystemTest {

    @Test
    void testSystemRunsDefaultParams() throws InterruptedException {
        //needs changed due to algorithm using timer
        Settings settings = new Settings();
        MockGUI display = new MockGUI(settings);
        new EvolutionaryModel(settings, display);

        display.setMaxGen(2); // for testability
        display.startRun();
        Thread.sleep(2000);

        Assertions.assertTrue(display.isFinished());
    }

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

//    @Test
//    void testSystemCrashesIncorrectParams() {
//        Settings settings = new Settings();
//        MockGUI display = new MockGUI(settings);
//        new EvolutionaryModel(settings, display);
//
//        //set params here
//        display.setCrossover(true);
//        display.setPopulationSize(0);
//        display.setGenomeLength(125);
//        //end set params
//
//        boolean caught = false;
//        try {
//            display.startRun();
//        } catch (ArithmeticException a) {
//            a.printStackTrace();
//            caught = true;
//        }
//    }


}
