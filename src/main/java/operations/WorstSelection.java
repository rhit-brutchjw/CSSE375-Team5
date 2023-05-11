package operations;

import logic.Chromosome;

import java.util.ArrayList;

public class WorstSelection implements Selection {

	@Override
	public ArrayList<Chromosome> performSelection(ArrayList<Chromosome> population) {
        int popSize = population.size();
        ArrayList<Chromosome> output = new ArrayList<>();
        Chromosome forCopy = population.remove(popSize - 1);
        for (int i = 0; i < popSize; i++) {
            output.add(forCopy.clone());
        }
        return output;
	}

}
