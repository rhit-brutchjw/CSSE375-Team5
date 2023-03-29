

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
import javax.swing.event.MouseInputListener;

public class ChromosomeViewer {
    private static final int FRAME_WIDTH = 600;
    private static final int FRAME_HEIGHT = 600;
    private JFileChooser chooser = new JFileChooser("genotypes");
    private File currentFile = new File("genotypes\\size_100.txt");
    private JFrame frame = new JFrame();
    private Chromosome chromosome = new Chromosome();
    private int[][] genes = chromosome.load(currentFile);
    private JLabel fileTitle = new JLabel(currentFile.getName());
    private JPanel chromosomePanel = new JPanel();
    private JButton mutate = new JButton("Mutate");
    private JButton save = new JButton("Save");
    private JButton load = new JButton("Load");
    private JTextField mutationFactor = new JTextField();
    private ChromosomeComponent chromosomeComponent = new ChromosomeComponent(chromosome, genes);

    public static void main(String[] args) {
        new ChromosomeViewer();
    } // main

    public ChromosomeViewer() {
        frame.setPreferredSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Dimension d = new Dimension(100, 20);
        mutationFactor.setPreferredSize(d);
        chromosomePanel.add(save);
        chromosomePanel.add(load);
        chromosomePanel.add(mutate);
        chromosomePanel.add(mutationFactor);
        chromosomeComponent.addMouseListener(new MouseMutateListener());
        load.addActionListener(new LoadListener());
        save.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                int returnVal = chooser.showSaveDialog(chromosomeComponent);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    File fileToSave = chooser.getSelectedFile();
                    chromosome.save(fileToSave, genes);
                }
            }
        });
        mutate.addActionListener(new ActionListener() {

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
        });
        frame.add(chromosomeComponent);
        frame.add(chromosomePanel, BorderLayout.SOUTH);
        frame.add(fileTitle, BorderLayout.NORTH);
        update(genes);
    } // ChromosomeViewer
    
    public void update(int[][] chromosome) {
        chromosomeComponent.updateGrid(chromosome);
        chromosomeComponent.repaint();
        frame.repaint();
        frame.pack();
        frame.setVisible(true);
    } // update

    private class MouseMutateListener extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            if (e.getX() < 400 && e.getX() > 0 && e.getY() < genes.length * 40 && e.getY() > 0) {
                genes = changeOnClick(genes, e.getX(), e.getY());
                fileTitle.setText(currentFile.getName() + " (mutated)");
                update(genes);
            }
        }

        public int[][] changeOnClick(int[][] chromoArray, int x, int y) {
            int iIndex = gridHelperI(y);
            int jIndex = gridHelperJ(x);
            if (chromoArray[iIndex][jIndex] == 1) {
                chromoArray[iIndex][jIndex] = 0;
            } else {
                chromoArray[iIndex][jIndex] = 1;
            }
            return chromoArray;
        } // changeOnClick
        
        public int gridHelperI(int y) {
            int iIndex = y / 40;
            return iIndex;

        } // gridHelperI
        
        public int gridHelperJ(int x) {
            int jIndex = x / 40;
            return jIndex;

        } // gridHelperJ
    }

    public class LoadListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            int returnVal = chooser.showOpenDialog(chromosomeComponent);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                currentFile = chooser.getSelectedFile();
                genes = chromosome.load(currentFile);
                fileTitle.setText(currentFile.getName());
                update(genes);
            }
        }
    }
} // end ChromosomeViewer
