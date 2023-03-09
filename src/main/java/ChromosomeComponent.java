

import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JComponent;

/**
 * Class: ChromosomeComponent <br>
 * Extends: JComponent
 *
 * @author brutchjw & rameydj <br>
 *         Purpose: Used to draw the geneArray of a Chromosome object <br>
 *         For example:
 *
 *         <pre>
 *         ChromosomeComponent exampleComp = new ChromosomeComponent(exampleChromosome, exampleChromosome.geneArray);
 *         </pre>
 *
 */
public class ChromosomeComponent extends JComponent {
    private Chromosome chromosome;
    private int[][] genes;

    /**
     * ensures: initializes chromosome to chrome and genes to grid
     *
     * @param chrome initializes the chromosome field to chrome
     * @param grid   initializes the genes field to grid
     */
    public ChromosomeComponent(Chromosome chrome, int[][] grid) {
        super();
        this.chromosome = chrome;
        this.genes = grid;
    } // ChromosomeComponent

    /**
     * ensures: updates the genes field to newGrid
     *
     * @param newGrid updates the genes field to newGrid
     */
    public void updateGrid(int[][] newGrid) {
        this.genes = newGrid;
    } // updateGrid

    /**
     * ensures: calls the Chromosome's drawOn method to display the genes
     */
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        for (int i = 0; i < genes.length; i++) {
            int y = 0;
            y += i * 40;
            for (int j = 0; j < genes[i].length; j++) {
                chromosome.drawOn(g2, j * 40, y, genes[i][j], i * 10 + j);
            }

        }
    } // paintComponent
} // end ChromosomeComponent
