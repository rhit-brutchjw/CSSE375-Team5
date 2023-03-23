

import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JComponent;

/**
 * Class: FittestComponent <br>
 * Extends: JComponent
 *
 * @author brutchjw and rameydj <br>
 *         Purpose: Used to draw the best Chromosome of a generation <br>
 *         For example:
 *
 *         <pre>
 *         FittestComponent example = new FittestComponent(bestChromosome);
 *         </pre>
 *
 */
public class FittestComponent extends JComponent {
    public Chromosome best;

    /**
     * ensures: initializes best to bestFit
     *
     * @param bestFit used to initialize best
     */
    public FittestComponent(Chromosome bestFit) {
        super();
        this.best = bestFit;
    } // FittestComponent

    /**
     * ensures: updates best to newBest
     *
     * @param newBest used to update best
     */
    public void updateBest(Chromosome newBest) {
        this.best = newBest;
    } // updateBest

    /**
     * draws the best Chromosome by calling the Chromosome Object's drawOn method
     */
    @Override
    protected void paintComponent(Graphics g) {
    	int[][] bestGenes = best.getGenes();
        Graphics2D g2 = (Graphics2D) g;
        for (int i = 0; i < bestGenes.length; i++) {
            int y = 0;
            y += i * 40;
            for (int j = 0; j < bestGenes[i].length; j++) {
                best.drawOn(g2, j * 40, y, bestGenes[i][j], i * 10 + j);
            }

        }

    } // paintComponent
} // end FittestComponent
