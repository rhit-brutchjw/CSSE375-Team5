import java.io.File;

public class MockGUI implements Display {

    private boolean finished;
    private Population population;
    private Chromosome bestChromosome;
    private Settings settings;

    public MockGUI(Settings settings) {
        this.settings = settings;
    }


    public void setCrossover(boolean crossover) {
        settings.setCrossover(crossover);
    }

    public void setMutation(int mutation) {
        settings.setMutationFactor(mutation);
    }

    public void setFitnessMethod(int fitness) {
        settings.setFitnessMethod(fitness);
    }

    public void setMaxGen(int maxGen) {
        settings.setMaxGenerations(maxGen);
    }

    public void setGenomeLength(int genomeLength) {
        settings.setGenomeLength(genomeLength);
    }

    public void setPopulationSize(int size) {
        settings.setPopulationSize(size);
    }

    public void setElitism(int elitism) {
        settings.setElitism(elitism);
    }

    public void setSelectionMethod(Selection method) {
        settings.setSelectionMethod(method);
    }

    public void setTarget(File file) {
        settings.setTarget(file);
    }

    @Override
    public void updateMostFit(Chromosome chromosome) {
        this.bestChromosome = chromosome;
    }

    @Override
    public void updatePopulation(Population population) {
        this.population = population;
    }

    @Override
    public void markFinished() {
        finished = true;
    }

    public boolean isFinished() {
        return this.finished;
    }

    @Override
    public void startRun() {
        EvolutionaryModel.t.start();
    }


}
