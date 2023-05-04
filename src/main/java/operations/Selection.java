package operations;

import logic.Chromosome;

import java.util.ArrayList;

public interface Selection {

    ArrayList<Chromosome> performSelection(ArrayList<Chromosome> population);
}
