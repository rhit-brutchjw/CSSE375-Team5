package components;

import logic.Chromosome;

import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JComponent;

public class ChromosomeComponent extends JComponent {
    private Chromosome chromosome;
    private int[][] genes;

    public ChromosomeComponent(Chromosome chrome, int[][] grid) {
        super();
        this.chromosome = chrome;
        this.genes = grid;
    } // ChromosomeComponent

    public void updateGrid(int[][] newGrid) {
        this.genes = newGrid;
    } // updateGrid
    public int[][] getGrid() { return this.genes;}
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
