import java.util.ArrayList;
import java.util.Random;

public class RouletteSelection implements Selection {
    @Override
    public ArrayList<Chromosome> performSelection(ArrayList<Chromosome> population) {
        Random r = new Random();
        ArrayList<Chromosome> wheel = new ArrayList<Chromosome>();
        ArrayList<Chromosome> output = new ArrayList<Chromosome>();
        for (Chromosome chromosome : population) {
            Chromosome temp = chromosome.clone();
            for (int i = 0; i < chromosome.getRank(); i++) {
                wheel.add(temp);
            }
        }
        for (int i = 0; i < population.size(); i++) {
            int spinSelection = r.nextInt(wheel.size());
            Chromosome toAdd = wheel.get(spinSelection);
            output.add(toAdd);
        }
        return output;
    }
}
