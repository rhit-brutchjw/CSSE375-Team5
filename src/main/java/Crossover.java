import java.util.ArrayList;

public class Crossover {
    public Crossover() {
    } // Crossover

    /*
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
*/
    public ArrayList<Chromosome> performCross(ArrayList<Chromosome> population) {
        ArrayList<Chromosome> result = new ArrayList<>();
        for (Chromosome parent1 : population) {
            Chromosome parent2 = population.get(population.indexOf(parent1) + 1);
            Chromosome child1 = parent1.clone();
            Chromosome child2 = parent2.clone();
            for (int i = 5; i < child1.getGenes().length; i++) {
                for (int j = 0; j < child1.getGenes()[0].length; j++) {
                    int tempGeneValue = child1.getGenes()[i][j];
                    child1.updateGeneValue(i, j, child2.getGenes()[i][j]);
                    child2.updateGeneValue(i, j, tempGeneValue);
                }
            }
            result.add(child1);
            result.add(child2);
        }
        return result;
    }
} // end Crossover
