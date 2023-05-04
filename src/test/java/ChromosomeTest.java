//import org.easymock.EasyMock;

import logic.Chromosome;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.awt.*;

public class ChromosomeTest {



    @Test
    void testSquareColorChoose1() {
        Chromosome c = new Chromosome();
        Assertions.assertEquals(Color.BLACK, c.getRectColorBasedOnCellValue(0));
    }
    @Test
    void testSquareColorChoose2() {
        Chromosome c = new Chromosome();
        Assertions.assertEquals(Color.GREEN, c.getRectColorBasedOnCellValue(1));
    }
    @Test
    void testTextColorChoose1() {
        Chromosome c = new Chromosome();
        Assertions.assertEquals(Color.WHITE, c.getTextColorBasedOnCellValue(0));
    }
    @Test
    void testTextColorChoose2() {
        Chromosome c = new Chromosome();
        Assertions.assertEquals(Color.BLACK, c.getTextColorBasedOnCellValue(1));
    }

    @Test
    void testTextColorChoose3() {
        Chromosome c = new Chromosome();
        Assertions.assertEquals(Color.BLACK, c.getTextColorBasedOnCellValue(5));
    }

    @Test
    void testCloneChromosome() {
        int[][] genes = new int[10][10];
        int seed = 2;
        int rank = 3;
        Chromosome c1 = new Chromosome(genes, seed, rank);

        Chromosome clone = c1.clone();

        Assertions.assertEquals(c1.getRank(), clone.getRank());
        Assertions.assertEquals(c1.getLength(), clone.getLength());

    }
}
