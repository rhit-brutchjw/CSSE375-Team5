import data.FileLoader;
import logic.Chromosome;
import logic.Settings;
import operations.FitnessSimple;
import operations.RankSelection;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import presentation.MockGUI;

import java.io.File;

public class SettingsTest {

    @Test
    void testCrossoverSet() {
        Settings settings = new Settings();
        MockGUI display = new MockGUI(settings);
        display.setCrossover(true);
        Assertions.assertTrue(settings.willPerformCrossover());
    }

    @Test
    void testMutationSet() {
        Settings settings = new Settings();
        MockGUI display = new MockGUI(settings);
        display.setMutation(2);
        Assertions.assertEquals(2, settings.getMutationFactor());
    }

    @Test
    void testFitnessSet() {
        Settings settings = new Settings();
        MockGUI display = new MockGUI(settings);
        display.setFitnessMethod(0);
        Assertions.assertInstanceOf(FitnessSimple.class, settings.getFitnessMethod());
    }

    @Test
    void testMaxGenSet() {
        Settings settings = new Settings();
        MockGUI display = new MockGUI(settings);
        display.setMaxGen(500);
        Assertions.assertEquals(500, settings.getMaxGenerations());
    }

    @Test
    void testGenomeLengthSet() {
        Settings settings = new Settings();
        MockGUI display = new MockGUI(settings);
        display.setGenomeLength(100);
        Assertions.assertEquals(100, settings.getGenomeLength());
    }

    @Test
    void testPopulationSizeSet() {
        Settings settings = new Settings();
        MockGUI display = new MockGUI(settings);
        display.setPopulationSize(100);
        Assertions.assertEquals(100, settings.getPopulationSize());
    }

    @Test
    void testElitismSet() {
        Settings settings = new Settings();
        MockGUI display = new MockGUI(settings);
        display.setElitism(2);
        Assertions.assertEquals(2, settings.getElitism());
    }

    @Test
    void testSelectionMethodSet() {
        Settings settings = new Settings();
        MockGUI display = new MockGUI(settings);
        display.setSelectionMethod(4);
        Assertions.assertInstanceOf(RankSelection.class, settings.getSelectionMethod());
    }

    @Test
    void testTargetSet() {
        Settings settings = new Settings();
        MockGUI display = new MockGUI(settings);
        FileLoader fl = new FileLoader();
        display.setTarget(new File("./genotypes/smile.txt"));
        Chromosome c = fl.load(new File("./genotypes/smile.txt"));

        Assertions.assertEquals(0, c.compareTo(settings.getTarget()));

    }
}
