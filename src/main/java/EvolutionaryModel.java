

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collections;
import javax.swing.Timer;

/**
 * Class: EvolutionaryModel
 *
 * @author brutchjw and rameydj <br>
 *         Purpose: Used to run a Genetic Algorithm with various parameters
 *         given at runtime <br>
 *         For example:
 *
 *         <pre>
 *         EvolutionaryModel example = new EvolutionaryModel();
 *         </pre>
 *
 *
 */
public class EvolutionaryModel {
    private Fitness fit = new Fitness();
    private Terminator terminator = new Terminator();
    private static Timer t;
    private Population pop = null;
    private static EvolutionViewer evViewer;
    private int curGen;

    /**
     * ensures: Creates a Timer object which runs the Genetic Algorithm once per
     * timer delay, Creates an EvolutionViewer object which visualizes the results
     * of the Genetic Algorithm
     */
    public EvolutionaryModel() {
        evViewer = new EvolutionViewer();

        t = new Timer(50, new ActionListener() {
            int curGen = 0;

            @Override
            public void actionPerformed(ActionEvent arg0) {
                if (evViewer.isStarted()) {
                    if (curGen == 0) {
                        pop = evViewer.getPopulation();
                        pop.setMaxGen(evViewer.getMaxGen());
                    }
                    curGen++;
                    // Fitness Calculation
                    for (Chromosome chromosome : pop.getPopulation()) {
                        if (evViewer.getFitnessMethod() == 1) {
                            fit.matchingFitnessCalculation(evViewer.getTarget(), chromosome);
                        } else if (evViewer.getFitnessMethod() == 0) {
                            fit.simpleFitnessCalculation(chromosome);
                        } else if (evViewer.getFitnessMethod() == 2) {
                            fit.consecutiveFitnessCalculation(chromosome);
                        }
                    }
                    // Sorts populations
                    pop.sortPopulation();

                    // Terminate calculation
                    if (terminator.mostFit(pop) || terminator.genCount(evViewer.getMaxGen(), curGen)) {
                        evViewer.setFinished(true);
                        ((Timer) arg0.getSource()).stop();
                    }

                    // Adds points to ArrayList for graphing purposes
                    pop.addAvgFit();
                    pop.addBestFit();
                    pop.addWorstFit();
                    pop.addHammDist();
                    pop.setCurGen(curGen);

                    // Updates main GUI
                    evViewer.updateGUI(pop);

                    // Prints to console the best, average, worst, and Hamming Distance
                    System.out.println("--------Generation: " + curGen + "--------");
                    System.out.println("Best Score: " + pop.getGene(0).getRank());
                    System.out.println("Approximate Average: " + pop.getGene((pop.getSize() / 2)).getRank());
                    System.out.println("Worst Score: " + pop.getGene(pop.getSize() - 1).getRank());
                    System.out.println("Average Hamming Distance : " + pop.getAvgHamm(curGen - 1));

                    // Selection, Crossover (if applicable), then to Mutation
                    evViewer.handlePopSelectionMutation(pop);

                    // Updates additional displays
                    evViewer.updateFittest(pop.getGene(0));
                    evViewer.updateAllPop(pop);

                }
            }

        });

    } // EvolutionaryModel

    /**
     * ensures: pauses the timer, and thus the Genetic Algorithm
     */
    public static void pauseTimer() {
        t.stop();
    } // pauseTimer

    /**
     * ensures: starts the timer, and thus the Genetic Algorithm, also restarts the
     * process when the Genetic Algorithm has finished
     */
    public static void startTimer() {
        if (!evViewer.checkDisposal()) {
        	t.start();
        }
    } // startTimer
} // end EvolutionaryModel
