import components.ChromosomeComponent;
import logic.Chromosome;
import org.easymock.EasyMock;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


public class ChromosomeComponentTest {


    @Test
    void testCreateChromosomeComponentGetsGrid() {
        final Chromosome c = EasyMock.createMock(Chromosome.class);
        final int[][] grid = new int[8][8];
        ChromosomeComponent cc = new ChromosomeComponent(c, grid);
        Assertions.assertEquals(grid, cc.getGrid());
    }


    @Test
    void testUpdatePopulationShouldUpdate() {
        final Chromosome c = EasyMock.createMock(Chromosome.class);
        final int[][] grid = new int[10][10];
        ChromosomeComponent cc = new ChromosomeComponent(c, grid);
        final int[][] grid2 = new int[9][9];
        cc.updateGrid(grid2);
        Assertions.assertNotEquals(grid, cc.getGrid());
        Assertions.assertEquals(grid2, cc.getGrid());
    }
}
