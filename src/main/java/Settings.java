import java.io.File;

public class Settings {
    private boolean crossover = false;
    private int mutationFactor = 1;
    private int fitnessMethod = 0; // change to polymorphic solution
    private int maxGen = 500;
    private int genomeLength = 100;
    private int populationSize = 100;
    private int elitism = 1;
    private FileLoader fileLoader = new FileLoader();
    private Selection selectionMethod = new TruncationSelection();
    private Chromosome target = new Chromosome();

    public int getPopulationSize() {
        return populationSize;
    }

    public void setPopulationSize(int populationSize) {
        this.populationSize = populationSize;
    }

    public int getGenomeLength() {
        return genomeLength;
    }

    public void setGenomeLength(int genomeLength) {
        this.genomeLength = genomeLength;
    }

    public int getMaxGenerations() {
        return maxGen;
    }

    public void setMaxGenerations(int maxGen) {
        this.maxGen = maxGen;
    }

    public int getFitnessMethod() {
        return fitnessMethod;
    }

    public void setFitnessMethod(int fitnessMethod) {
        this.fitnessMethod = fitnessMethod;
    }

    public Chromosome getTarget() {
        return target;
    }

    public void setTarget(File file) {
        this.target = fileLoader.load(file);
        this.genomeLength = target.getLength();
    }

    public int getMutationFactor() {
        return mutationFactor;
    }

    public void setMutationFactor(int mutationFactor) {
        this.mutationFactor = mutationFactor;
    }

    public boolean willPerformCrossover() {
        return crossover;
    }

    public void setCrossover(boolean crossover) {
        this.crossover = crossover;
    }

    public Selection getSelectionMethod() {
        return selectionMethod;
    }

    public void setSelectionMethod(int sIndex) {
    	switch (sIndex) {
    	case 0:
    		this.selectionMethod = new TruncationSelection();
    		break;
    	case 1:
    		this.selectionMethod = new RouletteSelection();
    		break;
    	case 2:
    		this.selectionMethod = new RandomSelection();
    		break;
    	case 3:
    		this.selectionMethod = new WorstSelection();
    		break;
    	case 4:
    		this.selectionMethod = new DiversitySelection();
    		break;
    	}
    }

    public int getElitism() {
        return elitism;
    }

    public void setElitism(int elitism) {
        this.elitism = elitism;
    }
    //need method to set target, and when setting target make it so that genomeLength = target.length(),
    //gets rid of need to error check
}
