

import java.awt.Dimension;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class ChromosomeViewer {
    private static final int FRAME_WIDTH = 418;
    private static final int FRAME_HEIGHT = 500;
    private static final String DEFAULT_FILE_PATH = "genotypes/size_100.txt";
    private static final String DEFAULT_FOLDER_PATH = "genotypes";
    private JFileChooser chooser;
    private JFrame frame;
    private JPanel chromosomePanel;
    private JButton mutateButton;
    private JButton closeButton;
    private JButton saveButton;
    private JButton loadButton;
    private JTextField mutationFactor;

    private FileLoader fileLoader;
    private File currentFile;

    private JLabel fileTitle;

    private Chromosome chromosome;
    private int[][] genes;
    private ChromosomeComponent chromosomeComponent;

    public ChromosomeViewer() {
        frame = new JFrame();
        chromosomePanel = new JPanel();
        mutateButton = new JButton("Mutate");
        closeButton = new JButton("Close");
        saveButton = new JButton("Save");
        loadButton = new JButton("Load");

        mutationFactor = new JTextField();
        fileTitle = new JLabel();
        fileTitle.setHorizontalAlignment(JLabel.CENTER);


        chooser = new JFileChooser(DEFAULT_FOLDER_PATH);
        fileLoader = new FileLoader();
        currentFile = new File(DEFAULT_FILE_PATH);
        chromosome = fileLoader.load(currentFile);
        int[][] genes = chromosome.getGenes();
        chromosomeComponent = new ChromosomeComponent(chromosome, genes);

    	setUpFrame();
    	setUpChromosomePanel();

    	chromosomeComponent.addMouseListener(new MouseMutateListener());

    	setUpActionListeners();

    	update(genes);
    } // ChromosomeViewer

    private void setUpFrame() {
        frame.setPreferredSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Chromosome Creator");
        frame.add(chromosomeComponent);
        frame.add(chromosomePanel, BorderLayout.SOUTH);
        frame.add(fileTitle, BorderLayout.NORTH);
    }

    private void setUpChromosomePanel() {
        Dimension d = new Dimension(100, 20);
        mutationFactor.setPreferredSize(d);
        chromosomePanel.add(closeButton);
        chromosomePanel.add(saveButton);
        chromosomePanel.add(loadButton);
        chromosomePanel.add(mutateButton);
        chromosomePanel.add(mutationFactor);
    }

    private void setUpActionListeners() {
    	closeButton.addActionListener(new CloseListener());
        loadButton.addActionListener(new LoadListener());
        saveButton.addActionListener(new SaveListener());
        mutateButton.addActionListener(new MutateListener());
    }
    
    public void update(int[][] chromosome) {
    	fileTitle.setText(currentFile.getName());
        chromosomeComponent.updateGrid(chromosome);
        chromosomeComponent.repaint();
        frame.repaint();
        frame.pack();
        frame.setVisible(true);
    }

    private class MouseMutateListener extends MouseAdapter {
        private final int cellSize = 40;

    	@Override
        public void mouseClicked(MouseEvent e) {
            int x = e.getX();
            int y = e.getY();
            if (x < 400 && x > 0 && y < genes.length * cellSize && y > 0) {
                int iIndex = y / cellSize;
                int jIndex = x / cellSize;
                genes[iIndex][jIndex] ^= 1;
                fileTitle.setText(currentFile.getName() + " (mutated)");
                update(genes);
            }
        }
    }

    private class CloseListener implements ActionListener {

    	@Override
    	public void actionPerformed(ActionEvent arg0) {
    		frame.dispose();
    	}
    }

    private class SaveListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent arg0) {
            int returnVal = chooser.showSaveDialog(chromosomeComponent);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File fileToSave = chooser.getSelectedFile();
                fileLoader.save(fileToSave, chromosome.getGenes());
            }
        }
    }

    private class LoadListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            int returnVal = chooser.showOpenDialog(chromosomeComponent);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                currentFile = chooser.getSelectedFile();
                chromosome = fileLoader.load(currentFile);
                genes = chromosome.getGenes();
                fileTitle.setText(currentFile.getName());
                update(genes);
            }
        }
    }

    private class MutateListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent arg0) {
            try {
            	int intMutationFactor = Integer.parseInt(mutationFactor.getText());
            	genes = chromosome.mutation(intMutationFactor, new Random()).getGenes();
            } catch (NumberFormatException e) {
                System.out.println("Failed! Please enter an integer.");
            }
            fileTitle.setText(currentFile.getName() + " (mutated)");
            update(genes);
        }
    }
} // end ChromosomeViewer
