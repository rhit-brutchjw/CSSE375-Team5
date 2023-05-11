package operations;

import logic.Chromosome;

public class FitnessConsecutive implements FitnessStrategy{
    @Override
    public void doFitnessCalculation(Chromosome target, Chromosome individual) {
        int bestScore = 0;
        int curScore = 0;
        int[][] indGenes = individual.getGenes();
        for (int i = 0; i < indGenes.length; i++) {
            for (int j = 0; j < indGenes[i].length; j++) {
                if (curScore == 0 && indGenes[i][j] == 1) {
                    curScore++;
                } else if (curScore > 0 && indGenes[i][j] == 1) {

                    curScore++;
                    if (curScore > bestScore) {
                        bestScore = curScore;
                    }
                } else if (curScore > 0 && indGenes[i][j] == 0) {
                    System.out.println("curScore"+ curScore);
                    if (curScore > bestScore) {
                        bestScore = curScore;
                    }

                    curScore = 0;
                }
            }
        }
        individual.setRank(bestScore);
    }
}
