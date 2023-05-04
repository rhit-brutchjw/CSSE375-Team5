package operations;

import logic.Chromosome;

import java.util.ArrayList;
import java.util.Random;

public class DiversitySelection implements Selection {
	// Convoluted method to ensure chromosome diversity
	// Step 1: Start with the 1st & 2nd Chromosome
	// Step 2: Calculate an average "rank difference" between the 2nd and 1st Chromosome
	// Step 3: As the 3rd Chromosome comes in, calculate its average rank difference
	// between the 3rd and all the existing output Chromosomes.
	// Step 4: If the average rank diff is >= 0.9 * the list's avg rank diff, we add it in
	// Step 5: Calculate a new average rank diff by averaging the list's and the new addition's rank average.

	@Override
	public ArrayList<Chromosome> performSelection(ArrayList<Chromosome> population) {
        Random r = new Random();
        ArrayList<Chromosome> output = new ArrayList<Chromosome>();
        output.add(population.remove(0).clone());
        output.add(population.remove(0).clone());
        int outputAvgRank = (output.get(0).getRank() + output.get(1).getRank()) / 2;
        for (Chromosome chromosome : population) {
            Chromosome temp = chromosome.clone();
            if (temp.getRank() >= outputAvgRank) {
            	output.add(temp);
            	outputAvgRank = (output.size() * outputAvgRank + temp.getRank()) / (output.size() + 1);
            }
        }
        int counter = 0;
        while (output.size() < population.size() + 2) {
        	output.add(output.get(counter));
        	counter++;
        }
        return output;
	}

}
