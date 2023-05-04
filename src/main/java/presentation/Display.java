package presentation;

import logic.Chromosome;
import logic.Population;

public interface Display {

    void updateMostFit(Chromosome chromosome);
    void updatePopulation(Population population);
    void markFinished();

    void startRun();
}
