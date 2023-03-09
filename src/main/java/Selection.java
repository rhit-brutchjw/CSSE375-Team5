

import java.util.ArrayList;
import java.util.Random;

/**
 * Class: Selection
 *
 * @author brutchjw and rameydj <br>
 *         Purpose: Used to select the next generation of Chromosomes depending
 *         on the method of selection <br>
 *         For example:
 *
 *         <pre>
 *         Selection example = new Selection();
 *         </pre>
 *
 */
public class Selection {

    /**
     * ensures: creats a Selection Object so it's methods can be called
     */
    public Selection() {

    } // Selection

    /**
     * ensures: removes the bottom half of the population, and returns the result
     *
     * @param population the ArrayList of Chromosomes that gets cut in half
     * @return returns the ArrayList of Chromosomes after the bottom half has been
     *         removed
     */
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

    /**
     * ensures: creates a Roulette Wheel using an ArrayList of Chromosomes where the
     * higher the Chromosome's rank, the more representation it has in the Roulette
     * Wheel, it then selects from the wheel randomly and returns an ArrayList of
     * Chromosomes the same size as the initial incoming population
     *
     * @param population the ArrayList of Chromosomes that is used to fill the
     *                   Roulette Wheel
     * @return returns the resulting ArrayList of Chromosomes that were selected
     *         from the wheel
     */
    public ArrayList<Chromosome> rouletteWheel(ArrayList<Chromosome> population) {
        Random r = new Random();
        ArrayList<Chromosome> wheel = new ArrayList<Chromosome>();
        ArrayList<Chromosome> output = new ArrayList<Chromosome>();
        for (Chromosome chromosome : population) {
            Chromosome temp = chromosome.clone();
            for (int i = 0; i < chromosome.rank; i++) {
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

    /**
     * ensures: randomly selects Chromosomes from the incoming population and
     * returns the resulting ArrayList, which is the same size as the incoming
     * population
     *
     * @param population the initial population used to select from
     * @return returns the resulting ArrayList of Chromosomes after selection is
     *         done
     */
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
