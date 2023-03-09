

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.Random;

/**
 * Class: Population
 *
 * @author brutchjw and rameydj <br>
 *         Purpose: Used to generate a population of random Chromosome Objects
 *         and handle selection, crossover, mutation, elitism, as well as draw
 *         the average, best, and worst fitness scores of each generation <br>
 *         For example:
 *
 *         <pre>
 *         Population examplePop = new Population(100, 100);
 *         </pre>
 *
 */
public class Population {
    public ArrayList<Chromosome> population;
    public Random r = new Random();
    public Selection select = new Selection();
    public Crossover cross = new Crossover();
    public Mutation mut = new Mutation();
    public int maxGenerations;
    public int currentGeneration;
    public ArrayList<Integer> bestFit = new ArrayList<Integer>();
    public ArrayList<Integer> avgFit = new ArrayList<Integer>();
    public ArrayList<Integer> worstFit = new ArrayList<Integer>();
    public ArrayList<Integer> hammDist = new ArrayList<Integer>();
    public int currentIndex = 0;
    public int popSize;
    public int genomeLength;
    public int elitismIndex = 0;

    /**
     * ensures: initializes popSize to popSize and genomeLength to genomeLength,
     * also generates a random population based on the popSize
     *
     * @param popSize      used to initialize popSize
     * @param genomeLength used to initialize popSize
     */
    public Population(int popSize, int genomeLength) {
        this.popSize = popSize;
        this.genomeLength = genomeLength;
        this.population = randomPopulation(popSize);
    } // Population

    /**
     * ensures: sets the elitismIndex to number
     *
     * @param number used to set elitismIndex
     */
    public void setElitism(int number) {
        elitismIndex = number;
    } // setElitism

    /**
     * ensures: adds the average fitness score of each generation to an ArrayList
     */
    public void addAvgFit() {
        int avg = 0;
        for (Chromosome chromosome : population) {
            avg += chromosome.rank;
        }
        avgFit.add(avg / population.size());
    } // addAvgFit

    /**
     * ensures: adds the best fitness score of each generation to an ArrayList
     */
    public void addBestFit() {
        int bestRank = 0;
        for (Chromosome chromosome : population) {
            if(chromosome.rank > bestRank) {
                bestRank = chromosome.rank;
            }
        }
        bestFit.add(bestRank);
    } // addBestFit

    /**
     * ensures: adds the worst fitness score of each generation to an ArrayList
     */
    public void addWorstFit() {
        worstFit.add(population.get(population.size() - 1).rank);
    } // addWorstFit

    /**
     * ensures: calculates and adds the average Hamming Distance of each generation
     * to an ArrayList
     */
    public void addHammDist() {
        int total = 0;
        int tracker = 0;
        for (int i = 0; i < population.size(); i++) {
            Chromosome current = population.get(i);
            for (int j = i + 1; j < population.size(); j++) {
                Chromosome check = population.get(j);
                int distance = 0;
                for (int k = 0; k < current.geneArray.length; k++) {
                    for (int l = 0; l < current.geneArray[k].length; l++) {
                        if (current.geneArray[k][l] != check.geneArray[k][l]) {
                            distance++;
                        }
                    }
                }
                total += distance;
                distance = 0;
                tracker++;
            }
        }
        hammDist.add(total / tracker);
    } // addHammDist

    /**
     * ensures: for all of the Chromosome objects inside the population, creates
     * deep clones of them, performs selection based on the passed in method, performs crossover if
     * applicable, mutates them, and then updates the population to the resulting
     * ArrayList
     *
     * @param mutFactor     the mutation factor that is passed to autoMutation
     * @param willCrossover the boolean that decides whether or not crossover should
     *                      occur
     */
    public void handleSelectionMutation(int mutFactor, boolean willCrossover, int selectionMethod) {
        // Deep clone population
        ArrayList<Chromosome> temp = new ArrayList<Chromosome>();
        for (Chromosome chromosome : this.population) {
            Chromosome copy = chromosome.clone();
            temp.add(copy);
        }
        // Elitism
        ArrayList<Chromosome> output = new ArrayList<Chromosome>();
        for (int i = 0; i < elitismIndex; i++) {
            Chromosome elite = this.population.get(i).clone();
            output.add(elite);
        }
        // Selection
        if(selectionMethod == 0) {
            temp = select.truncation(temp);
        } else if(selectionMethod == 1) {
            temp = select.rouletteWheel(temp);
        } else if(selectionMethod == 2) {
            temp = select.rankedSelection(temp);
        }
        // Crossover
        if (willCrossover) {
            temp = cross.performCross(temp);
        }
        // Mutation
        for (int i = elitismIndex; i < temp.size(); i++) {
            Chromosome toAdd = temp.get(i);
            output.add(mut.autoMutation(toAdd, mutFactor));
        }

        this.population = output;
    } // handleSelectionMutation

    /**
     * ensures: creates an ArrayList of random Chromosome Objects and returns the
     * result
     *
     * @param popSize the length of the ArrayList to be created
     * @return returns the resulting ArrayList of Chromosome Objects
     */
    private ArrayList<Chromosome> randomPopulation(int popSize) {

        ArrayList<Chromosome> output = new ArrayList<Chromosome>();
        for (int i = 0; i < popSize; i++) {
            int seed = r.nextInt();
            r.setSeed(seed);
            int rows = genomeLength / 10;
            int cols = 10;
            int[][] loadGene = new int[rows][cols];
            for (int j = 0; j < rows; j++) {
                for (int k = 0; k < cols; k++) {
                    if (r.nextBoolean()) {
                        loadGene[j][k] = 1;
                    } else {
                        loadGene[j][k] = 0;
                    }
                }
            }
            output.add(new Chromosome(loadGene, seed, -1));
        }
        return output;

    } // randomPopulation

    /**
     * ensures: draws the x and y axes, the graph's legend, the best, worst, and
     * average fitness scores of each generation, and the average Hamming Distance
     * of each generation
     *
     * @param g the Graphics2D Object used for drawing
     */
    public void drawOn(Graphics2D g) {
        g.drawRect(100, 50, 1000, 400);
        g.setColor(Color.GREEN);
        g.fillRect(980, 320, 10, 10);
        g.setColor(Color.RED);
        g.fillRect(980, 345, 10, 10);
        g.setColor(Color.YELLOW);
        g.fillRect(980, 370, 10, 10);
        g.setColor(Color.MAGENTA);
        g.fillRect(980, 395, 10, 10);
        g.setStroke(new BasicStroke(3f));
        for (int i = 0; i < genomeLength / 10 + 1; i++) {
            g.setColor(Color.BLACK);
            g.drawString(Integer.toString(i * 10), 80, 450 - (400 / (genomeLength / 10)) * i);
        }
        int helper = 0;
        for (int i = 0; i < currentGeneration + 1; i++) {
            int a = maxGenerations / 10;
            if (i % a == 0) {
                helper++;
                g.setColor(Color.BLACK);
                g.drawString(Integer.toString(i), (100 * helper), 470);
            }
        }
        for (int i = 0; i < currentGeneration - 1; i++) {
            g.setColor(Color.GREEN);
            Shape l = new Line2D.Double(100 + (i) * (1000.0 / maxGenerations),
                    450 - bestFit.get(i) * (400.0 / (genomeLength)), 100 + (i + 1) * (1000.0 / maxGenerations),
                    450 - bestFit.get(i + 1) * (400.0 / (genomeLength)));
            g.draw(l);
            g.setColor(Color.RED);
            Shape l1 = new Line2D.Double(100 + (i) * (1000.0 / maxGenerations),
                    450 - worstFit.get(i) * (400.0 / (genomeLength)), 100 + (i + 1) * (1000.0 / maxGenerations),
                    450 - worstFit.get(i + 1) * (400.0 / (genomeLength)));
            g.draw(l1);
            g.setColor(Color.YELLOW);
            Shape l2 = new Line2D.Double(100 + (i) * (1000.0 / maxGenerations),
                    450 - avgFit.get(i) * (400.0 / (genomeLength)), 100 + (i + 1) * (1000.0 / maxGenerations),
                    450 - avgFit.get(i + 1) * (400.0 / (genomeLength)));
            g.draw(l2);
            g.setColor(Color.MAGENTA);
            Shape l3 = new Line2D.Double(100 + (i) * (1000.0 / maxGenerations),
                    450 - hammDist.get(i) * (400.0 / (genomeLength)), 100 + (i + 1) * (1000.0 / maxGenerations),
                    450 - hammDist.get(i + 1) * (400.0 / (genomeLength)));
            g.draw(l3);
        }

    } // drawOn
} // end Population
