

import java.util.ArrayList;
import java.util.Random;

/**
 * Class: Mutation
 *
 * @author brutchjw and rameydj <br>
 *         Purpose: Used to mutate a Chromosome either by click or automatically
 *         <br>
 *         For example:
 *
 *         <pre>
 *         Mutation example = new Mutation();
 *         </pre>
 *
 */
public class Mutation {

    /**
     * ensures: creates a Mutation Object so it's methods can be called
     */
    public Mutation() {

    } // Mutation

    /**
     * ensures: changes the bit of the 2DArray at the x and y value to the opposite
     * value in binary and returns the updated 2DArray
     *
     * @param chromoArray the 2DArray the gets changed
     * @param x           the x value of the bit
     * @param y           the y value of the bit
     * @return returns the updated 2DArray
     */
    public int[][] changeOnClick(int[][] chromoArray, int x, int y) {
        int iIndex = gridHelperI(y);
        int jIndex = gridHelperJ(x);
        if (chromoArray[iIndex][jIndex] == 1) {
            chromoArray[iIndex][jIndex] = 0;
        } else {
            chromoArray[iIndex][jIndex] = 1;
        }
        return chromoArray;
    } // changeOnClick

    /**
     * ensures: a helper method that takes a y value and returns the index of the
     * row of the bit
     *
     * @param y the y value that comes in
     * @return returns the index of the row
     */
    public int gridHelperI(int y) {
        int iIndex = y / 40;
        return iIndex;

    } // gridHelperI

    /**
     * ensures: a helper method that takes a x value and returns the index of the
     * column of the bit
     *
     * @param x the x value that comes in
     * @return returns the index of the column
     */
    public int gridHelperJ(int x) {
        int jIndex = x / 40;
        return jIndex;

    } // gridHelperJ

    /**
     * ensures: takes a 2DArray of ints and changes on average the same number of
     * bits as the mutationFactor
     *
     * @param mutationFactor the average number of bits that will change
     * @param chromoArray    the 2DArray that will change
     * @return returns the 2DArray that is changed
     */
    public int[][] manualMutation(int mutationFactor, int[][] chromoArray) {
        Random random = new Random();
        ArrayList<Integer> keys = new ArrayList<Integer>();
        for (int i = 0; i < mutationFactor; i++) {
            int key = random.nextInt(chromoArray.length * 10);
            if (!keys.contains(key)) {
                keys.add(key);
            } else {
                i--;
            }
        }
        for (int j = 0; j < chromoArray.length; j++) {
            for (int k = 0; k < chromoArray[0].length; k++) {
                int chance = random.nextInt(chromoArray.length * 10);
                for (int key : keys) {
                    if (key == chance) {
                        if (chromoArray[j][k] == 1) {
                            chromoArray[j][k] = 0;
                        } else {
                            chromoArray[j][k] = 1;
                        }
                    }
                }
            }
        }
        return chromoArray;
    } // manualMutation

    /**
     * ensures: calls manualMutation based on the incoming Chromosome and
     * mutationFactor
     *
     * @param chromosome     the Chromosome used to pass it's geneArray field into
     *                       manualMutation
     * @param mutationFactor the mutationFactor passed into manualMutation
     * @return returns the Chromosome that was changed
     */
    public Chromosome autoMutation(Chromosome chromosome, int mutationFactor) {
        chromosome.geneArray = manualMutation(mutationFactor, chromosome.geneArray);
        return chromosome;

    } // autoMutation
} // end Mutation
