

import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JComponent;

/**
 * Class: EvolutionComponent<br>
 * Extends: JComponent
 *
 * @author brutchjw and rameydj <br>
 *         Purpose: Used to display the best, worst, and average fitness scores
 *         of each generation of a population, as well as the average Hamming
 *         Distance between generations <br>
 *         For example:
 *
 *         <pre>
 *         EvolutionComponent example = new EvolutionComponent(population);
 *         </pre>
 */
public class EvolutionComponent extends JComponent {
    private Population pop;

    /**
     * ensures: initializes pop to populations
     *
     * @param population used to initialize the pop field
     */
    public EvolutionComponent(Population population) {
        super();
        this.pop = population;
    } // EvolutionComponent

    /**
     * ensures: updates the pop field to population
     *
     * @param population used to update the current pop field to population
     */
    public void updatePopulation(Population population) {
        this.pop = population;
    } // updatePopulation

    /**
     * ensures: draws the best, worst, and average fitness scores of each generation
     * of a population, as well as the average Hamming Distance between generations
     * by calling a Population Object's drawOn method
     */
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
