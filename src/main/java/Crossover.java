

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
            int[][] oneGenes = one.getGenes();
            Chromosome two = pop.get(c + 1).clone();
            int[][] twoGenes = two.getGenes();
            int tempInt;
            for (int i = 5; i < oneGenes.length; i++) {
                for (int j = 0; j < oneGenes[0].length; j++) {
                    tempInt = oneGenes[i][j];
                    one.updateGeneValue(i, j, twoGenes[i][j]);
                    two.updateGeneValue(i, j, tempInt);
                }
            }
            temp.add(one);
            temp.add(two);
        }
        return temp;
    } // performCross
} // end Crossover
