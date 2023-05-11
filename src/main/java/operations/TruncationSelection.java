package operations;

import logic.Chromosome;

import java.util.ArrayList;

public class TruncationSelection implements Selection {
    @Override
    public ArrayList<Chromosome> performSelection(ArrayList<Chromosome> population) {
        int popSize = population.size();
        for (int i = 0; i < popSize / 2; i++) {
            population.remove(population.size() - 1);
        }
        ArrayList<Chromosome> output = new ArrayList<Chromosome>();
        for (Chromosome chromosome : population) {
            Chromosome copy1 = chromosome.clone();
            Chromosome copy2 = chromosome.clone();
            output.add(copy1);
            output.add(copy2);
        }
        return output;
    }
}
