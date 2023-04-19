import org.easymock.EasyMock.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.awt.*;


public class ChromosomeViewerTest {

    @Test
    void testChangeOnClick1() {
        int[][] arrayToClick = new int[10][10];
        setupArrayForTest(arrayToClick);
        Assertions.assertEquals(0, arrayToClick[3][3]);
        ChromosomeViewer cv = new ChromosomeViewer();
        cv.changeOnClick(arrayToClick, 120, 120);
        Assertions.assertEquals(1, arrayToClick[3][3]);
    }
    @Test
    void testChangeOnClick2() {
        int[][] arrayToClick = new int[10][10];
        setupArrayForTest(arrayToClick);
        Assertions.assertEquals(0, arrayToClick[0][0]);
        ChromosomeViewer cv = new ChromosomeViewer();
        cv.changeOnClick(arrayToClick, 0, 0);
        Assertions.assertEquals(1, arrayToClick[0][0]);
    }
    @Test
    void testChangeOnClick3() {
        int[][] arrayToClick = new int[10][10];
        setupArrayForTest(arrayToClick);
        Assertions.assertEquals(0, arrayToClick[9][9]);
        ChromosomeViewer cv = new ChromosomeViewer();
        cv.changeOnClick(arrayToClick, 399, 399);
        Assertions.assertEquals(1, arrayToClick[9][9]);
    }


    void setupArrayForTest(int[][] array) {
        for(int i = 0; i < array.length; i++) {
            for(int k = 0; k < array[0].length; k++) {
                array[i][k] = 0;
            }
        }
    }
}
