

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Population {
    private ArrayList<Chromosome> population;
    private Random r = new Random();
    private Crossover cross = new Crossover();
    //public Mutation mut = new Mutation();
    private int maxGenerations;
    private int currentGeneration;
    private ArrayList<Integer> bestFit = new ArrayList<Integer>();
    private ArrayList<Integer> avgFit = new ArrayList<Integer>();
    private ArrayList<Integer> worstFit = new ArrayList<Integer>();
    private ArrayList<Integer> hammDist = new ArrayList<Integer>();
    private int genomeLength;
    private int elitismIndex = 0;

    public Population(int popSize, int genomeLength) {
        this.genomeLength = genomeLength;
        this.population = randomPopulation(popSize);
    } // Population
    
    public Chromosome getGene(int index) { return population.get(index); }
    public int getSize() { return population.size(); }
    public int getAvgHamm(int index) { return hammDist.get(index); }
    public void setMaxGen(int maxGen) { this.maxGenerations = maxGen; }
    public int getCurGen() { return currentGeneration; }
    public void setCurGen(int curGen) { this.currentGeneration = curGen; } 
    public int getGenomeLength() { return genomeLength; }
    public void sortPopulation() { Collections.sort(population); }
    public ArrayList<Chromosome> getPopulation() { return population; }
    public void setElitism(int number) { elitismIndex = number; }

	public boolean maxFitAchieved() {
        for (Chromosome chromosome : population) {
            if (chromosome.getRank() == genomeLength) {
                return true;
            }
        }
        return false;
	}
    
    public void addAvgFit() {
        int avg = 0;
        for (Chromosome chromosome : population) {
            avg += chromosome.getRank();
        }
        avgFit.add(avg / population.size());
    } // addAvgFit

    public void addBestFit() {
        int bestRank = 0;
        for (Chromosome chromosome : population) {
            if(chromosome.getRank() > bestRank) {
                bestRank = chromosome.getRank();
            }
        }
        bestFit.add(bestRank);
    } // addBestFit

    public void addWorstFit() {
        worstFit.add(population.get(population.size() - 1).getRank());
    } // addWorstFit

    public void addHammDist() {
        int total = 0;
        int tracker = 0;
        for (int i = 0; i < population.size(); i++) {
            Chromosome current = population.get(i);
            for (int j = i + 1; j < population.size(); j++) {
                Chromosome check = population.get(j);
                int distance = 0;
                int[][] currentGeneArray = current.getGenes();
                int[][] compareGeneArray = check.getGenes();
                for (int k = 0; k < currentGeneArray.length; k++) {
                    for (int l = 0; l < currentGeneArray[k].length; l++) {
                        if (currentGeneArray[k][l] != compareGeneArray[k][l]) {
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

    public void handleSelectionMutation(int mutFactor, boolean willCrossover, Selection selectionMethod) {
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
        temp = selectionMethod.performSelection(temp);

        // Crossover
        if (willCrossover) {
            temp = cross.performCross(temp);
        }
        // Mutation
        for (int i = elitismIndex; i < temp.size(); i++) {
            output.add(temp.get(i).mutation(mutFactor, new Random()));
        }

        this.population = output;
    } // handleSelectionMutation

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
