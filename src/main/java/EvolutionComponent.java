

import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JComponent;

public class EvolutionComponent extends JComponent {
    private Population pop;

    public EvolutionComponent(Population population) {
        super();
        this.pop = population;
    } // EvolutionComponent

    public void updatePopulation(Population population) {
        this.pop = population;
    } // updatePopulation

    @Override
    protected void paintComponent(Graphics g) {

        Graphics2D g2 = (Graphics2D) g;
        try {
            pop.drawOn(g2);
        } catch (ArithmeticException f) {

        } catch (NullPointerException e) {
        }

    } // paintComponent
} // end EvolutionComponent
