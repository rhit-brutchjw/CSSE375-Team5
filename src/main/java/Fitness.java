

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
        int[][] activeGenes = chromosome.getGenes();
        for (int i = 0; i < activeGenes.length; i++) {
            for (int j = 0; j < activeGenes[i].length; j++) {
                if (activeGenes[i][j] == 1) {
                    fitnessScore++;
                }
            }
        }
        chromosome.setRank(fitnessScore);
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
        int[][] indGenes = individual.getGenes();
        int[][] tarGenes = target.getGenes();
        for (int i = 0; i < indGenes.length; i++) {
            for (int j = 0; j < indGenes[i].length; j++) {
                if (tarGenes[i][j] == indGenes[i][j]) {
                    fitnessScore++;
                }
            }
        }
        individual.setRank(fitnessScore);
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
        int[][] indGenes = individual.getGenes();
        for (int i = 0; i < indGenes.length; i++) {
            for (int j = 0; j < indGenes[i].length; j++) {
                if (curScore == 0 && indGenes[i][j] == 1) {
                    curScore++;
                } else if (curScore > 0 && indGenes[i][j] == 1) {
                    curScore++;
                } else if (curScore > 0 && indGenes[i][j] == 0) {
                    if (curScore > bestScore) {
                        bestScore = curScore;
                    }
                    curScore = 0;
                }
            }
        }
        individual.setRank(bestScore);
    } // consecutiveFitnessCalculation
} // end Fitness
