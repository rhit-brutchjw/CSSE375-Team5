import java.util.ArrayList;
import java.util.Random;

public class RankSelection implements Selection {
    @Override
    public ArrayList<Chromosome> performSelection(ArrayList<Chromosome> population) {
        Random r = new Random();
        ArrayList<Chromosome> output = new ArrayList<Chromosome>();
        for (int i = 0; i < population.size(); i++) {
            int random = r.nextInt(population.size());
            Chromosome temp = population.get(random);
            output.add(temp);
        }
        return output;
    }
}
