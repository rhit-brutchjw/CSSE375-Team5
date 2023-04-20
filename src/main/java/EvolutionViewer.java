
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import javax.swing.JComboBox;
import javax.swing.JFileChooser;

public class EvolutionViewer implements Display {
	private static final int FRAME_WIDTH = 1400;
	private static final int FRAME_HEIGHT = 600;
	private JFileChooser chooser = new JFileChooser("genotypes");
	private JFrame evolutionGUI = new JFrame();
	private JFrame bestGUI = new JFrame();
	private JFrame allPopGUI = new JFrame();
	private JPanel wordPanel = new JPanel();
	private JPanel options = new JPanel();
	private JPanel numSettings = new JPanel();
	private JButton start = new JButton("Start");
	private JButton chromosomeMaker = new JButton("Create Chromosome for Matching");
	private JCheckBox crossover = new JCheckBox();
	private JLabel title = new JLabel("Fitness over Generations");
	private JLabel mutationText = new JLabel("Mutation Rate (N/pop)");
	private JLabel populationSizeText = new JLabel("Population Size");
	private JLabel generationsText = new JLabel("Generations");
	private JLabel genomeText = new JLabel("Genome Length");
	private JLabel selectionText = new JLabel("Selection");
	private JLabel fitnessText = new JLabel("Fitness Function");
	private JLabel elitismText = new JLabel("Elitism");
	private JLabel crossoverText = new JLabel("Crossover?");
	private JLabel mostFitLegend = new JLabel("Most Fit");
	private JLabel avgFitLegend = new JLabel("Average Fit");
	private JLabel leastFitLegend = new JLabel("Least Fit");
	private JLabel hammLegend = new JLabel("Hamming Distance");
	private JLabel highestFitnessRating = new JLabel("Highest Fitness Rating: ");
	private JTextField mutationRate = new JTextField();
	private JTextField populationSize = new JTextField();
	private JTextField generations = new JTextField();
	private JTextField genomeLength = new JTextField();
	private JTextField elitism = new JTextField();
	private String[] selectionOptions = { "Truncation", "Roulette", "Random", "Worst", "Diversify" };
	private JComboBox<String> selection = new JComboBox<>(selectionOptions);
	private String[] fitnessOptions = { "Simple", "Matching", "Consecutive" };
	private JComboBox<String> fitness = new JComboBox<>(fitnessOptions);
	private EvolutionComponent evComp = new EvolutionComponent();
	private FittestComponent bestFit;
	private PopulationComponent allPopComponent;
	private boolean isFinished = false;

	public EvolutionViewer(Settings settings) {
		evolutionGUI.setPreferredSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
		evolutionGUI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		evolutionGUI.setTitle("Evolution Viewer");
		bestGUI.setTitle("Active Best Chromosome");
		allPopGUI.setTitle("Active Population");
		
		setUpStartActionListener(settings);
		setUpChromosomeMakerListener();
		
		setUpJPanels();

		buildGUI();
	} // EvolutionViewer

	@Override
	public void updateMostFit(Chromosome chromosome) {
		bestFit.updateBest(chromosome);
		highestFitnessRating.setText("Highest Fitness Rating: " + chromosome.getRank());
		bestFit.repaint();
		bestGUI.repaint();
		bestGUI.pack();
		bestGUI.setVisible(true);
	}

	@Override
	public void updatePopulation(Population population) {
		// stuff from updateGUI method
		evolutionGUI.setAlwaysOnTop(true);
		if (isFinished) {
			start.setText("Restart");
		}
		if (population.getCurGen() == 1) {
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
		evComp.updatePopulation(population);
		evComp.repaint();
		evolutionGUI.repaint();
		evolutionGUI.pack();
		evolutionGUI.setVisible(true);

		// update allPop method stuff
		allPopComponent.updateAll(population.getPopulation());
		allPopComponent.repaint();
		allPopGUI.repaint();
		allPopGUI.pack();
		allPopGUI.setVisible(true);
	}

	@Override
	public void markFinished() {
		this.isFinished = true;
	}

	@Override
	public void startRun() {
		EvolutionaryModel.t.start();
	}
	
	public void setUpStartActionListener(Settings settings) {
		start.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				if (start.getText().equals("Restart")) {
					evolutionGUI.dispose();
					bestGUI.dispose();
					allPopGUI.dispose();
					Display display = new EvolutionViewer(settings);
			        EvolutionaryModel evm = new EvolutionaryModel(settings, display);
				}
				
		    	switch (selection.getSelectedIndex()) {
		    	case 0:
		    		settings.setSelectionMethod(new TruncationSelection());
		    		break;
		    	case 1:
		    		settings.setSelectionMethod(new RouletteSelection());
		    		break;
		    	case 2:
		    		settings.setSelectionMethod(new RandomSelection());
		    		break;
		    	case 3:
		    		settings.setSelectionMethod(new WorstSelection());
		    		break;
		    	case 4:
		    		settings.setSelectionMethod(new DiversitySelection());
		    		break;
		    	}
				
				if (fitness.getSelectedIndex() == 1) {
					int returnVal = chooser.showOpenDialog(evComp);
					if (returnVal == JFileChooser.APPROVE_OPTION) {
						File file = chooser.getSelectedFile();
						settings.setTarget(file);
					} else if (returnVal == JFileChooser.CANCEL_OPTION) {
						return;
					}
				} else {
					try {
						settings.setGenomeLength(Integer.parseInt(genomeLength.getText()));
					} catch (NumberFormatException n) {
					}
				}
				settings.setFitnessMethod(fitness.getSelectedIndex());
				settings.setCrossover(crossover.isSelected());
				try {
					if (Integer.parseInt(populationSize.getText()) % 10 == 0
							&& Integer.parseInt(genomeLength.getText()) % 10 == 0
							&& Integer.parseInt(elitism.getText()) >= 0) {
						settings.setPopulationSize(Integer.parseInt(populationSize.getText()));
						bestGUI.setPreferredSize(new Dimension(450, 5 * (settings.getGenomeLength() + 1)));
						bestGUI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
						bestGUI.setTitle("Best Fitness Score");

						if (settings.getPopulationSize() > settings.getGenomeLength()) {
							allPopGUI.setPreferredSize(new Dimension(600, 6 * settings.getPopulationSize()));
						} else {
							allPopGUI.setPreferredSize(new Dimension(600, 6 * settings.getGenomeLength()));
						}
						allPopGUI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
						allPopGUI.setTitle("All Population Viewer");
						bestFit = new FittestComponent();
						allPopComponent = new PopulationComponent();
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
							"Please enter an integer for population size, genome length, and/or elitism.", "Warning",
							2);
					return;
				}
				try {
					if (Integer.parseInt(elitism.getText()) >= Integer.parseInt(populationSize.getText())) {
						JOptionPane.showMessageDialog(null,
								"Please enter an elitism amount less than the population size.", "Warning", 2);
						return;
					}
					settings.setElitism(Integer.parseInt(elitism.getText()));
				} catch (NumberFormatException n) {
					JOptionPane.showMessageDialog(null, "Please enter an elitism amount less than the population size.",
							"Warning", 2);
					return;
				}
				try {
					if (Integer.parseInt(mutationRate.getText()) >= Integer.parseInt(populationSize.getText())) {
						JOptionPane.showMessageDialog(null,
								"Please enter a mutation rate less than the population size.", "Warning", 2);
						return;
					}
					settings.setMutationFactor(Integer.parseInt(mutationRate.getText()));
				} catch (NumberFormatException n) {
					JOptionPane.showMessageDialog(null, "Please enter an integer for mutation rate.", "Warning", 2);
					return;
				}
				try {
					settings.setMaxGenerations(Integer.parseInt(generations.getText()));
				} catch (NumberFormatException n) {
					JOptionPane.showMessageDialog(null, "Please enter an integer for number of generations.", "Warning",
							2);
				}
				startRun();
			}
		});
	}
	
	public void setUpChromosomeMakerListener() {
		chromosomeMaker.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
		        new ChromosomeViewer();
			}
		});
	}
	
	private void setUpJPanels() {
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
		options.add(selectionText);
		options.add(selection);
		options.add(fitnessText);
		options.add(fitness);
		options.add(chromosomeMaker);
		options.add(crossoverText);
		options.add(crossover);
		numSettings.setLayout(new BoxLayout(numSettings, BoxLayout.Y_AXIS));
		JPanel row1 = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		JPanel row2 = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		JPanel row3 = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		JPanel row4 = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		JPanel row5 = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		row1.add(mutationText);
		row1.add(mutationRate);
		row2.add(elitismText);
		row2.add(elitism);
		row3.add(genomeText);
		row3.add(genomeLength);
		row4.add(populationSizeText);
		row4.add(populationSize);
		row5.add(generationsText);
		row5.add(generations);
		numSettings.add(row1);
		numSettings.add(row2);
		numSettings.add(row3);
		numSettings.add(row4);
		numSettings.add(row5);
	}
	
	private void buildGUI() {
		wordPanel.add(title, BorderLayout.NORTH);
		evolutionGUI.add(evComp, BorderLayout.CENTER);
		evolutionGUI.add(options, BorderLayout.SOUTH);
		evolutionGUI.add(numSettings, BorderLayout.EAST);
		mostFitLegend.setBounds(1000, 325, 100, 50);
		avgFitLegend.setBounds(1000, 350, 100, 50);
		leastFitLegend.setBounds(1000, 375, 100, 50);
		hammLegend.setBounds(1000, 400, 120, 50);
		
		options.repaint();
		evolutionGUI.add(wordPanel, BorderLayout.NORTH);
		evolutionGUI.pack();
		evolutionGUI.setLocationRelativeTo(null);
		evolutionGUI.setVisible(true);
	}
} // end EvolutionViewer
