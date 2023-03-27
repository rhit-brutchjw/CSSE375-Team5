

import java.util.ArrayList;
import java.util.Random;

public class Selection {

    public Selection() {

    } // Selection

    public ArrayList<Chromosome> truncation(ArrayList<Chromosome> population) {
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

    } // truncation

    public ArrayList<Chromosome> rouletteWheel(ArrayList<Chromosome> population) {
        Random r = new Random();
        ArrayList<Chromosome> wheel = new ArrayList<Chromosome>();
        ArrayList<Chromosome> output = new ArrayList<Chromosome>();
        for (Chromosome chromosome : population) {
            Chromosome temp = chromosome.clone();
            for (int i = 0; i < chromosome.getRank(); i++) {
                wheel.add(temp);
            }
        }
        for (int i = 0; i < population.size(); i++) {
            int spinSelection = r.nextInt(wheel.size());
            Chromosome toAdd = wheel.get(spinSelection);
            output.add(toAdd);
        }
        return output;

    } // rouletteWheel

    public ArrayList<Chromosome> rankedSelection(ArrayList<Chromosome> population) {
        Random r = new Random();
        ArrayList<Chromosome> output = new ArrayList<Chromosome>();
        for (int i = 0; i < population.size(); i++) {
            int random = r.nextInt(population.size());
            Chromosome temp = population.get(random);
            output.add(temp);
        }
        return output;
    } // rankedSelection
} // end Selection
