

import java.util.ArrayList;

/**
 * Class: Crossover
 *
 * @author brutchjw and rameydj <br>
 *         Purpose: Used to perform one point crossover between two Chromosomes
 *         inside of a Population's ArrayList of Chromosomes <br>
 *         For example:
 *
 *         <pre>
 *         Crossover example = new Crossover();
 *         </pre>
 *
 */
public class Crossover {

    /**
     * ensures: Creates a Crossover object so the performCross method can be
     * accessed
     */
    public Crossover() {
    } // Crossover

    /**
     * ensures: returns an ArrayList of Chromosomes after one point crossover has
     * happened
     *
     * @param pop the ArrayList of Chromosomes that undergoes one point crossover
     * @return returns the ArrayList of Chromosomes that has undergone one point
     *         crossover
     */
    public ArrayList<Chromosome> performCross(ArrayList<Chromosome> pop) {
        ArrayList<Chromosome> temp = new ArrayList<Chromosome>();
        for (int c = 0; c < pop.size(); c += 2) {
            Chromosome one = pop.get(c).clone();
            Chromosome two = pop.get(c + 1).clone();
            int[][] tempArray = one.geneArray;
            for (int i = 5; i < one.geneArray.length; i++) {
                for (int j = 0; j < one.geneArray[0].length; j++) {
                    tempArray[i][j] = one.geneArray[i][j];
                    one.geneArray[i][j] = two.geneArray[i][j];
                    two.geneArray[i][j] = tempArray[i][j];
                }
            }
            temp.add(one);
            temp.add(two);
        }
        return temp;
    } // performCross
} // end Crossover
