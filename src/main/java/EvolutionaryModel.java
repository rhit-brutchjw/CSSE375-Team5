

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;

public class EvolutionaryModel {
    private final Settings settings;
    private final Display display;
    private Fitness fit = new Fitness();
    public static Timer t;
    private Population population;

    public Population getPopulation() {return population;}

    public EvolutionaryModel(Settings settings, Display display) {
        this.settings = settings;
        this.display = display;
        t = new Timer(50, new ActionListener() {
            int curGen = 0;
            @Override
            public void actionPerformed(ActionEvent arg0) {
                initializePopulation();
                curGen++;
                calculateFitness();
                checkTermination(arg0);
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

            private void checkTermination(ActionEvent arg0) {
                if(population.maxFitAchieved() || settings.getMaxGenerations() == curGen) {
                    display.markFinished();
                    ((Timer) arg0.getSource()).stop();
                }
            }

            public void calculateFitness() {//refactoring
                for (Chromosome chromosome : population.getPopulation()) {
                    if (settings.getFitnessMethod() == 1) {
                        fit.matchingFitnessCalculation(settings.getTarget(), chromosome);
                    } else if (settings.getFitnessMethod() == 0) {
                        fit.simpleFitnessCalculation(chromosome);
                    } else if (settings.getFitnessMethod() == 2) {
                        fit.consecutiveFitnessCalculation(chromosome);
                    }
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
        });

    }
}
