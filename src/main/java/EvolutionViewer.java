

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;


import javax.swing.JComboBox;
import javax.swing.JFileChooser;

/**
 * Class: EvolutionViewer
 *
 * @author brutchjw and rameydj <br>
 *         Purpose: Used to display the best, worst, and average fitness scores
 *         of each generation of a population, as well as the average Hamming
 *         Distance between generations, and allows parameters that affect the
 *         Genetic Algorithm to be entered at runtime. Also displays the best
 *         Chromosome of each generation, and the entire population. <br>
 *         For example:
 *
 *         <pre>
 *         EvolutionViewer example = new EvolutionViewer();
 *         </pre>
 */
public class EvolutionViewer {
    public static final int FRAME_WIDTH = 1400;
    public static final int FRAME_HEIGHT = 600;
    public Chromosome target = new Chromosome(null, -1, -1);
    public JFileChooser chooser = new JFileChooser("genotypes");
    public JFrame evolutionGUI = new JFrame();
    public JFrame bestGUI = new JFrame();
    public JFrame allPopGUI = new JFrame();
    public JPanel wordPanel = new JPanel();
    public JPanel options = new JPanel();
    public JButton start = new JButton("Start");
    public JCheckBox crossover = new JCheckBox();
    public JLabel title = new JLabel("Fitness over Generations");
    public JLabel mutationText = new JLabel("Mutation Rate (N/pop)");
    public JLabel populationSizeText = new JLabel("Population Size");
    public JLabel generationsText = new JLabel("Generations");
    public JLabel genomeText = new JLabel("Genome Length");
    public JLabel selectionText = new JLabel("Selection");
    public JLabel fitnessText = new JLabel("Fitness Function");
    public JLabel elitismText = new JLabel("Elitism");
    public JLabel crossoverText = new JLabel("Crossover?");
    public JLabel mostFitLegend = new JLabel("Most Fit");
    public JLabel avgFitLegend = new JLabel("Average Fit");
    public JLabel leastFitLegend = new JLabel("Least Fit");
    public JLabel hammLegend = new JLabel("Hamming Distance");
    public JLabel highestFitnessRating = new JLabel("Highest Fitness Rating: ");
    public JTextField mutationRate = new JTextField();
    public JTextField populationSize = new JTextField();
    public JTextField generations = new JTextField();
    public JTextField genomeLength = new JTextField();
    public JTextField elitism = new JTextField();
    private String[] selectionOptions = { "Truncation", "Roulette", "Ranked" };
    public JComboBox<String> selection = new JComboBox<>(selectionOptions);
    private String[] fitnessOptions = { "Simple", "Matching", "Consecutive" };
    public JComboBox<String> fitness = new JComboBox<>(fitnessOptions);
    public Population population;
    public EvolutionComponent evComp = new EvolutionComponent(population);
    public Chromosome best;
    public FittestComponent bestFit;
    public PopulationComponent allPopComponent;
    public boolean isPaused = true;
    public boolean isStarted = false;
    public boolean willCrossover;
    public boolean isFinished = false;
    public boolean hasTarget = false;
    public int selectionMethod;
    public int fitnessMethod;
    public int mutationFactor = 1;
    public int maxGen;

    /**
     *
     */
    public EvolutionViewer() {
        evolutionGUI.setPreferredSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
        evolutionGUI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        evolutionGUI.setTitle("Evolution Viewer");
        start.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                if (!isStarted) {
                    selectionMethod = selection.getSelectedIndex();
                    if (fitness.getSelectedIndex() == 1) {
                        int returnVal = chooser.showOpenDialog(evComp);
                        if (returnVal == JFileChooser.APPROVE_OPTION) {
                            File file = chooser.getSelectedFile();
                            target.geneArray = target.load(file);
                            hasTarget = true;
                            fitnessMethod = fitness.getSelectedIndex();
                        } else if (returnVal == JFileChooser.CANCEL_OPTION) {
                            return;
                        }
                    } else {
                        fitnessMethod = fitness.getSelectedIndex();
                    }
                    willCrossover = crossover.isSelected();

                    try {
                        if (hasTarget && Integer.parseInt(genomeLength.getText()) != target.getLength()) {
                            JOptionPane.showMessageDialog(null,
                                    "Please make genome length match the target genome length of: "
                                            + Integer.toString(target.getLength()),
                                    "Warning", 2);
                            return;
                        } else if (Integer.parseInt(populationSize.getText()) % 10 == 0
                                && Integer.parseInt(genomeLength.getText()) % 10 == 0
                                && Integer.parseInt(elitism.getText()) >= 0) {
                            population = new Population(Integer.parseInt(populationSize.getText()),
                                    Integer.parseInt(genomeLength.getText()));
                            population.setElitism(Integer.parseInt(elitism.getText()));
                            evComp.updatePopulation(population);
                            bestGUI.setPreferredSize(new Dimension(450, 5 * population.genomeLength));
                            bestGUI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                            bestGUI.setTitle("Best Fitness Score");
                            if (population.popSize > population.genomeLength) {
                                allPopGUI.setPreferredSize(new Dimension(600, 6 * population.popSize));
                            } else {
                                allPopGUI.setPreferredSize(new Dimension(600, 6 * population.genomeLength));
                            }
                            allPopGUI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                            allPopGUI.setTitle("All Population Viewer");
                            best = population.population.get(0);
                            bestFit = new FittestComponent(best);
                            allPopComponent = new PopulationComponent(population.population);
                        } else if (Integer.parseInt(populationSize.getText()) % 10 != 0) {
                            JOptionPane.showMessageDialog(null, "Please enter a multiple of 10 for population size.",
                                    "Warning", 2);
                            return;
                        } else if (Integer.parseInt(genomeLength.getText()) % 10 != 0) {
                            JOptionPane.showMessageDialog(null, "Please enter a multiple of 10 for genome length.",
                                    "Warning", 2);
                            return;
                        }
                    } catch (NumberFormatException n) {
                        JOptionPane.showMessageDialog(null,
                                "Please enter an integer for population size, genome length, and/or elitism.",
                                "Warning", 2);
                        return;
                    }
                    try {
                        if (Integer.parseInt(elitism.getText()) >= Integer.parseInt(populationSize.getText())) {
                            JOptionPane.showMessageDialog(null,
                                    "Please enter an elitism amount less than the population size.", "Warning", 2);
                            return;
                        }
                    } catch (NumberFormatException n) {
                    }
                    try {
                        if (Integer.parseInt(mutationRate.getText()) < population.population.size()) {
                            mutationFactor = Integer.parseInt(mutationRate.getText());
                        } else {
                            JOptionPane.showMessageDialog(null,
                                    "Please enter a mutation rate less than the population size.", "Warning", 2);
                            return;
                        }
                    } catch (NumberFormatException n) {
                        JOptionPane.showMessageDialog(null, "Please enter an integer for mutation rate.", "Warning", 2);
                        return;
                    }
                    try {
                        maxGen = Integer.parseInt(generations.getText());
                        isStarted = true;
                    } catch (NumberFormatException n) {
                        JOptionPane.showMessageDialog(null, "Please enter an integer for number of generations.",
                                "Warning", 2);
                        return;
                    }
                }
                if (isPaused == false && !isFinished) {
                    EvolutionaryModel.pauseTimer();
                    System.out.println("Timer is stopped");
                    start.setText("Resume");
                    isPaused = true;
                } else if (isPaused == true) {
                    EvolutionaryModel.startTimer();
                    isPaused = false;
                    start.setText("Pause");
                } else if (isFinished) {
                    start.setText("Start");
                    isStarted = false;
                    EvolutionaryModel.startTimer();
                    System.out.println("");
                }
            }

        });
        Dimension d = new Dimension(70, 20);
        Dimension d1 = new Dimension(20, 20);
        Dimension d2 = new Dimension(90, 20);
        Dimension m = new Dimension(121, 20);
        mutationRate.setPreferredSize(d);
        populationSize.setPreferredSize(d);
        generations.setPreferredSize(d);
        genomeLength.setPreferredSize(d);
        crossover.setPreferredSize(d1);
        selection.setPreferredSize(d2);
        elitism.setPreferredSize(d2);
        mutationText.setPreferredSize(m);
        populationSizeText.setPreferredSize(d2);
        generationsText.setPreferredSize(d);
        genomeText.setPreferredSize(d2);
        crossoverText.setPreferredSize(d);
        selectionText.setPreferredSize(new Dimension(53, 20));
        fitnessText.setPreferredSize(new Dimension(100, 20));
        elitismText.setPreferredSize(new Dimension(40, 20));
        evolutionGUI.add(mostFitLegend);
        evolutionGUI.add(avgFitLegend);
        evolutionGUI.add(leastFitLegend);
        evolutionGUI.add(hammLegend);
        options.add(start);
        options.add(mutationText);
        options.add(mutationRate);
        options.add(selectionText);
        options.add(selection);
        options.add(fitnessText);
        options.add(fitness);
        options.add(elitismText);
        options.add(elitism);
        options.add(genomeText);
        options.add(genomeLength);
        options.add(populationSizeText);
        options.add(populationSize);
        options.add(generationsText);
        options.add(generations);
        options.add(crossoverText);
        options.add(crossover);
        wordPanel.add(title, BorderLayout.NORTH);
        evolutionGUI.add(evComp, BorderLayout.CENTER);
        evolutionGUI.add(options, BorderLayout.SOUTH);
        mostFitLegend.setBounds(1000, 325, 100, 50);
        avgFitLegend.setBounds(1000, 350, 100, 50);
        leastFitLegend.setBounds(1000, 375, 100, 50);
        hammLegend.setBounds(1000, 400, 120, 50);

        options.repaint();
        evolutionGUI.add(wordPanel, BorderLayout.NORTH);
        evolutionGUI.pack();
        evolutionGUI.setLocationRelativeTo(null);
        evolutionGUI.setVisible(true);

    } // EvolutionViewer

    /**
     * ensures: updates the frame once per generation, and if the Genetic Algorithm
     * has started, displays the best Chromosome and the entire population
     *
     * @param pop used to update the population of EvolutionComponent
     */
    public void updateGUI(Population pop) {
        if (isStarted) {
            evolutionGUI.setAlwaysOnTop(true);
        }
        if (isFinished) {
            start.setText("Restart");
        }
        if (pop.currentGeneration == 1) {
            bestGUI.add(highestFitnessRating, BorderLayout.SOUTH);
            highestFitnessRating.setBounds(250, 480, 150, 20);
            allPopGUI.add(allPopComponent, BorderLayout.CENTER);
            allPopGUI.pack();
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            GraphicsDevice defaultScreen = ge.getDefaultScreenDevice();
            Rectangle rect = defaultScreen.getDefaultConfiguration().getBounds();
            System.out.println(defaultScreen.getDefaultConfiguration().getBounds());
            int x = (int) rect.getMaxX() - allPopGUI.getWidth();
            int y = 0;
            allPopGUI.setLocation(x, y);
            allPopGUI.setVisible(true);
            bestGUI.add(bestFit, BorderLayout.CENTER);
            bestGUI.pack();
            bestGUI.setVisible(true);
        }
        evComp.updatePopulation(pop);
        evComp.repaint();
        evolutionGUI.repaint();
        evolutionGUI.pack();
        evolutionGUI.setVisible(true);

    } // updateGUI

    /**
     * ensures: updates the display for the best Chromosome each generation
     *
     * @param best the current best Chromosome that is used to update the
     *             FittestComponent
     */
    public void updateFittest(Chromosome best) {
        bestFit.updateBest(best);
        highestFitnessRating.setText("Highest Fitness Rating: " + best.rank);
        bestFit.repaint();
        bestGUI.repaint();
        bestGUI.pack();
        bestGUI.setVisible(true);
    } // updateFittest

    /**
     * ensures: updates the display for the entire population each generation
     *
     * @param pop the Population that is used to update the PopulationComponent
     */
    public void updateAllPop(Population pop) {
        allPopComponent.updateAll(pop.population);
        allPopComponent.repaint();
        allPopGUI.repaint();
        allPopGUI.pack();
        allPopGUI.setVisible(true);
    } // updateAllPop
} // end EvolutionViewer
