

/**
 * Class: Fitness
 *
 * @author brutchjw and rameydj <br>
 *         Purpose: Used to perform a fitness calculation on a Chromosome <br>
 *         For example:
 *
 *         <pre>
 *         Fitness example = new Fitness();
 *         </pre>
 *
 */
public class Fitness {

    /**
     * ensures: creates a Fitness object so it's method can be called
     */
    public Fitness() {

    } // Fitness

    /**
     * ensures: performs a simple fitness calculation on a Chromosome and assigns it
     * a rank
     *
     * @param chromosome the Chromosome that undergoes a fitness calculation
     */
    public void simpleFitnessCalculation(Chromosome chromosome) {
        int fitnessScore = 0;
        for (int i = 0; i < chromosome.geneArray.length; i++) {
            for (int j = 0; j < chromosome.geneArray[i].length; j++) {
                if (chromosome.geneArray[i][j] == 1) {
                    fitnessScore++;
                }
            }
        }
        chromosome.rank = fitnessScore;
    } // simpleFitnessCalculation

    /**
     * ensures: performs a fitness calculation on a Chromosome to see how well it
     * matches a target Chromosome and assigns it a rank
     *
     * @param target     the Chromosome that is the target goal for the Genetic
     *                   Algorithm
     * @param individual the Chromosome that undergoes a fitness calculation
     */
    public void matchingFitnessCalculation(Chromosome target, Chromosome individual) {
        int fitnessScore = 0;
        for (int i = 0; i < individual.geneArray.length; i++) {
            for (int j = 0; j < individual.geneArray[i].length; j++) {
                if (target.geneArray[i][j] == individual.geneArray[i][j]) {
                    fitnessScore++;
                }
            }
        }
        individual.rank = fitnessScore;
    } // matchingFitnessCalculation

    /**
     * ensures: performs a fitness calculation on a Chromosome to see how many
     * consecutive 1's it has and assigns it a rank
     *
     * @param individual the Chromosome that undergoes a fitness calculation
     */
    public void consecutiveFitnessCalculation(Chromosome individual) {
        int bestScore = 0;
        int curScore = 0;
        for (int i = 0; i < individual.geneArray.length; i++) {
            for (int j = 0; j < individual.geneArray[i].length; j++) {
                if (curScore == 0 && individual.geneArray[i][j] == 1) {
                    curScore++;
                } else if (curScore > 0 && individual.geneArray[i][j] == 1) {
                    curScore++;
                } else if (curScore > 0 && individual.geneArray[i][j] == 0) {
                    if (curScore > bestScore) {
                        bestScore = curScore;
                    }
                    curScore = 0;
                }
            }
        }
        individual.rank = bestScore;
    } // consecutiveFitnessCalculation
} // end Fitness
