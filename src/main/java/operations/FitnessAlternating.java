package operations;

import logic.Chromosome;

public class FitnessAlternating implements FitnessStrategy{
    @Override
    public void doFitnessCalculation(Chromosome target, Chromosome individual) {
        int bestScore = 0;
        int curScore = 0;
        int[][] indGenes = individual.getGenes();
        int previous;
        if(indGenes[0][0] == 0) {
            previous = 1;
        } else {
            previous = 0;
        }
        for (int i = 0; i < indGenes.length; i++) {
            for (int j = 0; j < indGenes[i].length; j++) {
                if(curScore == 0 && indGenes[i][j] != previous) {
                    curScore++;
                } else if(curScore > 0 && indGenes[i][j] != previous) {
                    curScore++;
                } else if(curScore > 0 && indGenes[i][j] == previous) {
                    if (curScore > bestScore) {
                        bestScore = curScore;
                    }
                    curScore = 0;
                }
                previous = indGenes[i][j];
            }
        }
    }

}
