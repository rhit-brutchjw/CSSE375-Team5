
public class Fitness {

    public Fitness() {

    } // Fitness

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
