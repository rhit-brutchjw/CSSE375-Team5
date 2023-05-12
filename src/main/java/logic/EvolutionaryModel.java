package logic;

import javafx.animation.AnimationTimer;
import presentation.Display;


public class EvolutionaryModel {
    private final Settings settings;
    private final Display display;


    private Population population;
    public static AnimationTimer t;
    public Population getPopulation() {return population;}

    public EvolutionaryModel(Settings settings, Display display) {
        population = new Population(settings.getPopulationSize(), settings.getGenomeLength());;
        this.settings = settings;
        this.display = display;
        t = new AnimationTimer() {
            int curGen = 0;
            @Override
            public void handle(long now) {
                initializePopulation();
                curGen++;
                calculateFitness();
                checkTermination();
                addGraphPoints();
//                reportToConsole();
                handleSelectionToMutation();
                updateDisplays();
            }
            private void updateDisplays() {
                display.updatePopulation(population);
                display.updateMostFit(population.getGene(0));
            }

            private void handleSelectionToMutation() {
                population.handleSelectionMutation(settings.getMutationFactor(),
                        settings.willPerformCrossover(), settings.getSelectionMethod());
            }

            private void reportToConsole() {
                System.out.println("--------Generation: " + curGen + "--------");
                System.out.println("Best Score: " + population.getGene(0).getRank());
                System.out.println("Approximate Average: " + population.getGene((population.getSize() / 2)).getRank());
                System.out.println("Worst Score: " + population.getGene(population.getSize() - 1).getRank());
                System.out.println("Average Hamming Distance : " + population.getAvgHamm(curGen - 1));
            }

            private void addGraphPoints() {
                population.addAvgFit();
                population.addBestFit();
                population.addWorstFit();
                population.addHammDist();
                population.setCurGen(curGen);
            }

            private void checkTermination() {
                if(population.maxFitAchieved() || settings.getMaxGenerations() == curGen) {
                    display.markFinished();
                    t.stop();

                }
            }

            public void calculateFitness() {//refactoring
                for (Chromosome chromosome : population.getPopulation()) {
                    settings.getFitnessMethod().doFitnessCalculation(settings.getTarget(), chromosome);
                }
                population.sortPopulation();
            }

            private void initializePopulation() {
                if (curGen == 0) {
                    population = new Population(settings.getPopulationSize(), settings.getGenomeLength());
                    population.setMaxGen(settings.getMaxGenerations());
                    population.setElitism(settings.getElitism());
                }
            }
        };


    }
}
