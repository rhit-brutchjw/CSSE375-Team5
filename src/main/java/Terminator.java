/**
 * Class: Terminator
 *
 * @author brutchjw and rameydj <br>
 *         Purpose: Used to determine when the Genetic Algorithm should stop
 *         <br>
 *         For example:
 *
 *         <pre>
 *         Terminator example = new Terminator();
 *         </pre>
 *
 */
public class Terminator {

    /**
     * ensures: creates a Terminator Object so it's methods can be called
     */
    public Terminator() {
    } // Terminator

    /**
     * ensures: returns a boolean of whether or not a Chromosome inside the
     * Population has a perfect score
     *
     * @param population the Population used to check for a perfect scoring
     *                   Chromosome
     * @return returns true or false depending on if a Chromosome has a perfect
     *         score
     */
    public boolean mostFit(Population population) {
    	if (population.maxFitAchieved()) {
    		return true;
    	}
        return false;
    } // mostFit

    /**
     * ensures: returns a boolean of whether or not the Genetic Algorithm has
     * reached the max amount of Generations
     *
     * @param curGen the current generation that the Genetic Algorithm is on
     * @param maxGen the max amount of generations the Genetic Algorithm is allowed
     *               to have
     * @return returns true when curGen == maxGen, false otherwise
     */
    public boolean genCount(int curGen, int maxGen) {
        return curGen == maxGen;
    } // genCount
} // end Terminator
