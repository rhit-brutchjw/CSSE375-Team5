package operations;

import logic.Chromosome;

public class FitnessMatching implements FitnessStrategy{
    @Override
    public void doFitnessCalculation(Chromosome target, Chromosome individual) {
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
    }
}
