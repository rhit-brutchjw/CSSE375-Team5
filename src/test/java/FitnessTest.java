import org.easymock.EasyMock;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class FitnessTest {

    @Test
    public void simpleFitnessCalcGiven10Ones_ReturnsScore10() {
        FitnessStrategy f = new FitnessSimple();
        int[][] array = new int[10][10];
        Chromosome c = new Chromosome(array,0,0);
        testHelper2(c);
        f.doFitnessCalculation(null,c);
        Assertions.assertEquals(10,c.getRank());
    }
    @Test
    public void simpleFitnessCalcGivenZeros_ReturnsScore0() {
        FitnessStrategy f = new FitnessSimple();
        int[][] array = new int[10][10];
        Chromosome c = new Chromosome(array,0,0);
        f.doFitnessCalculation(null,c);
        Assertions.assertEquals(0,c.getRank());
    }
    @Test
    public void simpleFitnessCalcGivenOnes_ReturnsScore100() {
        FitnessStrategy f = new FitnessSimple();
        int[][] array = new int[10][10];
        Chromosome c = new Chromosome(array,0,0);
        testHelper1(c);
        f.doFitnessCalculation(null,c);
        Assertions.assertEquals(100,c.getRank());
    }

    @Test
    public void matchingFitnessCalcGivenZeros_ReturnsScore100() {
        FitnessStrategy f = new FitnessMatching();
        int[][] array = new int[10][10];
        int[][] array2 = new int[10][10];
        Chromosome c1 = new Chromosome(array, 0, 0);
        Chromosome c2 = new Chromosome(array2, 0, 0);
        f.doFitnessCalculation(c2, c1);
        Assertions.assertEquals(100, c1.getRank());
    }
    @Test
    public void matchingFitnessCalcC1GivenAllOnes_ReturnsScore0() {
        FitnessStrategy f = new FitnessMatching();
        int[][] array = new int[10][10];
        int[][] array2 = new int[10][10];
        Chromosome c1 = new Chromosome(array, 0, 0);
        testHelper1(c1);
        Chromosome c2 = new Chromosome(array2, 0, 0);
        f.doFitnessCalculation(c2, c1);
        Assertions.assertEquals(0, c1.getRank());
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

    @Test
    public void consecutiveFitnessCalcGivenAllZeros_ReturnsZero() {
        FitnessStrategy f = new FitnessConsecutive();
        int[][] array = new int[10][10];
        Chromosome c1 = new Chromosome(array, 0, 0);
        f.doFitnessCalculation(null,c1);
        Assertions.assertEquals(0, c1.getRank());
    }

    @Test
    public void consecutiveFitnessCalcGivenAllTenOnes_Returns10() {
        FitnessStrategy f = new FitnessConsecutive();
        int[][] array = new int[10][10];
        Chromosome c1 = new Chromosome(array, 0, 0);
        testHelper2(c1);

        f.doFitnessCalculation(null, c1);
        Assertions.assertEquals(10, c1.getRank());
    }
    @Test
    public void consecutiveFitnessCalcGivenAllOnes_Returns100() {
        FitnessStrategy f = new FitnessConsecutive();
        int[][] array = new int[10][10];
        Chromosome c = new Chromosome(array, 0, 0);
        testHelper1(c);

        f.doFitnessCalculation(null, c);

        Assertions.assertEquals(100, c.getRank());
    }



    private void testHelper1(Chromosome c) {
        for(int i = 0; i < 10; i++) {
            for(int j = 0; j < 10; j++) {
                c.updateGeneValue(i,j,1);
            }
        }
    }

    private void testHelper2(Chromosome c) {
        for(int i = 0; i < 10; i++) {
            c.updateGeneValue(0,i,1);
        }
    }
}
