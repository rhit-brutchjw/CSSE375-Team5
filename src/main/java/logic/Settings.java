package logic;

import data.FileLoader;
import operations.*;

import java.io.File;
import java.util.HashMap;

public class Settings {
    private boolean crossover = false;
    private int mutationFactor = 1;
//    private int fitnessMethod = 0; // change to polymorphic solution
    private int maxGen = 500;
    private int genomeLength = 100;
    private int populationSize = 100;
    private int elitism = 1;
    private FileLoader fileLoader = new FileLoader();
    private Selection selectionMethod = new TruncationSelection();
    private Chromosome target = new Chromosome();
    private FitnessStrategy fitnessMethod = new FitnessSimple();
    private HashMap<Integer, FitnessStrategy> fitMap = new HashMap<Integer, FitnessStrategy>();

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

    public FitnessStrategy getFitnessMethod() {

        return fitnessMethod;
    }

    public void setFitnessMethod(int fitnessMethod) {
        initFitMap();
        this.fitnessMethod = fitMap.get(fitnessMethod);
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

    public void setSelectionMethod(Selection selectionMethod) {
    	this.selectionMethod = selectionMethod;
    }

    public int getElitism() {
        return elitism;
    }

    public void setElitism(int elitism) {
        this.elitism = elitism;
    }


    public void initFitMap() {
        fitMap.put(1, new FitnessMatching());
        fitMap.put(0, new FitnessSimple());
        fitMap.put(2, new FitnessConsecutive());
    }
    //need method to set target, and when setting target make it so that genomeLength = target.length(),
    //gets rid of need to error check
}
