package components;

import logic.Population;

import java.awt.*;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import javax.swing.JComponent;

public class EvolutionComponent extends JComponent {
    private Population pop;

    public EvolutionComponent() {
        super();
    } // EvolutionComponent

    public void updatePopulation(Population population) {
        this.pop = population;
    } // updatePopulation
    public Population getPop() { return this.pop; }
    @Override
    protected void paintComponent(Graphics g) {

        Graphics2D g2 = (Graphics2D) g;
        try {
            g2.drawRect(100, 50, 1000, 400);
            g2.setColor(Color.GREEN);
            g2.fillRect(980, 320, 10, 10);
            g2.setColor(Color.RED);
            g2.fillRect(980, 345, 10, 10);
            g2.setColor(Color.YELLOW);
            g2.fillRect(980, 370, 10, 10);
            g2.setColor(Color.MAGENTA);
            g2.fillRect(980, 395, 10, 10);
            g2.setStroke(new BasicStroke(3f));
            for (int i = 0; i < pop.getGenomeLength() / 10 + 1; i++) {
                g.setColor(Color.BLACK);
                g.drawString(Integer.toString(i * 10), 80, 450 - (400 / (pop.getGenomeLength() / 10)) * i);
            }
            int helper = 0;
            for (int i = 0; i < pop.getCurrentGeneration() + 1; i++) {
                int a = pop.getMaxGenerations() / 10;
                if (i % a == 0) {
                    helper++;
                    g.setColor(Color.BLACK);
                    g.drawString(Integer.toString(i), (100 * helper), 470);
                }
            }
            for (int i = 0; i < pop.getCurrentGeneration() - 1; i++) {
                helper(g2, Color.GREEN, pop.getBestFit(), i);
                helper(g2, Color.RED, pop.getWorstFit(), i);
                helper(g2, Color.YELLOW, pop.getAvgFit(), i);
                helper(g2, Color.MAGENTA, pop.getHammDist(), i);
            }
        } catch (Exception e) {
            //do nothing
        }
    } // paintComponent

    private void helper(Graphics2D g2, Color c, ArrayList<Integer> list, int i) {
        g2.setColor(c);
        Shape l = new Line2D.Double(100 + (i) * (1000.0 / pop.getMaxGenerations()),
                450 - list.get(i) * (400.0 / (pop.getGenomeLength())), 100 + (i + 1) * (1000.0 / pop.getMaxGenerations()),
                450 - list.get(i + 1) * (400.0 / (pop.getGenomeLength())));
        g2.draw(l);
    }
} // end EvolutionComponent
