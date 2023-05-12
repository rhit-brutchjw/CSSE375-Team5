import javafx.application.Platform;
import logic.Chromosome;
import logic.EvolutionaryModel;
import logic.Population;
import logic.Settings;
import operations.*;
import org.awaitility.Awaitility;
import org.awaitility.Durations;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import presentation.Display;
import presentation.JavaFXLauncher;
import presentation.MockGUI;

public class IntegrationTest {
    //add indepth tests with real objects things like going through a full seelction rotation or building up pop.

//    @Test
//    void testSystemRunsCustomParams() throws InterruptedException {
//        Settings settings = new Settings();
//        MockGUI display = new MockGUI(settings);
//        new EvolutionaryModel(settings, display);
//
//        //set params here
//        display.setCrossover(true);
//        display.setPopulationSize(125);
//        display.setGenomeLength(125);
//        display.setMaxGen(2); // for testability
//        display.startRun();
//
//        Assertions.assertTrue(display.isFinished());
//    }


    @Test
    void testPopulationSetup() {
        Population pop = new Population(50, 100);
        Assertions.assertEquals(50, pop.getPopulation().size());
    }

    @Test
    void testPopulationChromosomes() {
        Population pop = new Population(50, 100);
        Chromosome c = pop.getGene(0);
        Assertions.assertEquals(100, c.getLength());
    }

    @Test
    void testPopulationFullRun() {
        Population pop = new Population(50, 100);
        Selection s = new RankSelection();
        pop.handleSelectionMutation(1, true, s);

        //Assertions.assertTrue(c.getRank() > 0);

    }

//    @Test
//    void FullEvoModel1() throws InterruptedException {
//        Settings set = new Settings();
//        set.setPopulationSize(100);
//        set.setGenomeLength(100);
//        Display dis = new MockGUI(set);
//        EvolutionaryModel evo = new EvolutionaryModel(set, dis);
//        dis.startRun();
//        Population pop = evo.getPopulation();
//        Chromosome c = pop.getGene(0);
//        Assertions.assertTrue(c.getRank() > 0);

//
//
//    }

    @Test
    public void simpleFitnessCalcGivenOnes_ReturnsScore100() {
        FitnessStrategy f = new FitnessSimple();
        int[][] array = new int[10][10];
        Chromosome c = new Chromosome(array,0,0);
        testHelper1(c);
        f.doFitnessCalculation(null, c);
        Assertions.assertEquals(100,c.getRank());
    }

    @Test
    public void matchingFitnessCalcC1GivenSomeOnes_ReturnsScore97() {
        FitnessStrategy f = new FitnessMatching();
        int[][] array = new int[10][10];
        int[][] array2 = new int[10][10];
        Chromosome c1 = new Chromosome(array, 0, 0);
        c1.updateGeneValue(1,1,1);
        c1.updateGeneValue(2,2,1);
        c1.updateGeneValue(3,3,1);
        Chromosome c2 = new Chromosome(array2, 0, 0);
        f.doFitnessCalculation(c2, c1);
        Assertions.assertEquals(97, c1.getRank());
    }



    private void testHelper1(Chromosome c) {
        for(int i = 0; i < 10; i++) {
            for(int j = 0; j < 10; j++) {
                c.updateGeneValue(j,i,1);
            }
        }
    }
}
