package components;

import logic.Chromosome;

import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JComponent;

public class FittestComponent extends JComponent {
    private Chromosome best;

    public FittestComponent() {
        super();
    } // FittestComponent

    public void updateBest(Chromosome newBest) {
        this.best = newBest;
    } // updateBest

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
