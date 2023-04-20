public class FitnessSimple implements FitnessStrategy{
    @Override
    public void doFitnessCalculation(Chromosome target, Chromosome individual) {
        int fitnessScore = 0;
        int[][] activeGenes = individual.getGenes();
        for (int i = 0; i < activeGenes.length; i++) {
            for (int j = 0; j < activeGenes[i].length; j++) {
                if (activeGenes[i][j] == 1) {
                    fitnessScore++;
                }
            }
        }
        individual.setRank(fitnessScore);
    }
}
