import java.util.ArrayList;

public class Crossover {
    public Crossover() {
    } // Crossover

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
