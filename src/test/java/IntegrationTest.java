import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class IntegrationTest {
    //add indepth tests with real objects things like going through a full seelction rotation or building up pop.

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

    @Test
    void FullEvoModel1() throws InterruptedException {
        Settings set = new Settings();
        set.setPopulationSize(100);
        set.setGenomeLength(100);
        Display dis = new MockGUI(set);
        EvolutionaryModel evo = new EvolutionaryModel(set, dis);
        dis.startRun();
        Thread.sleep(2000);
        Population pop = evo.getPopulation();
        Chromosome c = pop.getGene(0);
        Assertions.assertTrue(c.getRank() > 0);

    }

    @Test
    public void simpleFitnessCalcGivenOnes_ReturnsScore100() {
        Fitness f = new Fitness();
        int[][] array = new int[10][10];
        Chromosome c = new Chromosome(array,0,0);
        testHelper1(c);
        f.simpleFitnessCalculation(c);
        Assertions.assertEquals(100,c.getRank());
    }

    @Test
    public void matchingFitnessCalcC1GivenSomeOnes_ReturnsScore97() {
        Fitness f = new Fitness();
        int[][] array = new int[10][10];
        int[][] array2 = new int[10][10];
        Chromosome c1 = new Chromosome(array, 0, 0);
        c1.updateGeneValue(1,1,1);
        c1.updateGeneValue(2,2,1);
        c1.updateGeneValue(3,3,1);
        Chromosome c2 = new Chromosome(array2, 0, 0);
        f.matchingFitnessCalculation(c2, c1);
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
