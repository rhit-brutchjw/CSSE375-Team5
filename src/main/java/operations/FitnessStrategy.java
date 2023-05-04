package operations;

import logic.Chromosome;

public interface FitnessStrategy {

    void doFitnessCalculation(Chromosome target, Chromosome individual);

}
