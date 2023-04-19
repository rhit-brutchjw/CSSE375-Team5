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

    @Test
    void testGridHelperI1() {
        ChromosomeViewer cv = new ChromosomeViewer();
        Assertions.assertEquals(0, cv.gridHelperI(0));
    }
    @Test
    void testGridHelperI2() {
        ChromosomeViewer cv = new ChromosomeViewer();
        Assertions.assertEquals(0, cv.gridHelperI(39));
    }
    @Test
    void testGridHelperI3() {
        ChromosomeViewer cv = new ChromosomeViewer();
        Assertions.assertEquals(1, cv.gridHelperI(40));
    }
    @Test
    void testGridHelperI4() {
        ChromosomeViewer cv = new ChromosomeViewer();
        Assertions.assertEquals(9, cv.gridHelperI(399));
    }

    @Test
    void testGridHelperJ1() {
        ChromosomeViewer cv = new ChromosomeViewer();
        Assertions.assertEquals(0, cv.gridHelperJ(0));
    }
    @Test
    void testGridHelperJ2() {
        ChromosomeViewer cv = new ChromosomeViewer();
        Assertions.assertEquals(0, cv.gridHelperJ(39));
    }
    @Test
    void testGridHelperJ3() {
        ChromosomeViewer cv = new ChromosomeViewer();
        Assertions.assertEquals(1, cv.gridHelperJ(40));
    }
    @Test
    void testGridHelperJ4() {
        ChromosomeViewer cv = new ChromosomeViewer();
        Assertions.assertEquals(9, cv.gridHelperJ(399));
    }

    @Test
    void testFullClickChange1() {
        ChromosomeViewer cv = new ChromosomeViewer();
        int[][] array = new int[10][10];
        setupArrayForTest(array);
        Assertions.assertEquals(0, array[0][0]);
        array = cv.changeOnClick(array, 39, 15);
        Assertions.assertEquals(1, array[0][0]);
    }

    @Test
    void testFullClickChange2() {
        ChromosomeViewer cv = new ChromosomeViewer();
        int[][] array = new int[10][10];
        setupArrayForTest(array);
        Assertions.assertEquals(0, array[9][9]);
        array = cv.changeOnClick(array, 380, 378);
        Assertions.assertEquals(1, array[9][9]);
    }




    void setupArrayForTest(int[][] array) {
        for(int i = 0; i < array.length; i++) {
            for(int k = 0; k < array[0].length; k++) {
                array[i][k] = 0;
            }
        }
    }
}
