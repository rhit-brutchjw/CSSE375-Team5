package components;

import logic.Chromosome;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import javax.swing.JComponent;

public class PopulationComponent extends JComponent {
    private ArrayList<Chromosome> curPop;

    public PopulationComponent() {
        super();
    } // PopulationComponent

    public void updateAll(ArrayList<Chromosome> newPop) {
        this.curPop = newPop;
    } // updateAll

    @Override
    protected void paintComponent(Graphics g) {
        int x = 0;
        int y = 0;
        Graphics2D g2 = (Graphics2D) g;
        for (int i = 0; i < this.curPop.size() / 10; i++) {
            for (int l = 0; l < 10; l++) {
                Chromosome curChrome = this.curPop.get(i * 10 + l);
                int[][] curChromeGenes = curChrome.getGenes();
                int newRow = (i) * 5 * (curChromeGenes.length);
                int newCol = l * 50;
                x = newCol;
                y = newRow;
                for (int k = 0; k < curChromeGenes.length; k++) {
                    for (int j = 0; j < curChromeGenes[0].length; j++) {
                        curChrome.drawSmallOn(g2, x, y, curChromeGenes[k][j]);
                        x += 4;
                    }
                    x -= 40;
                    y += 4;
                }
            }
        }

    } // paintComponent
} // end PopulationComponent
