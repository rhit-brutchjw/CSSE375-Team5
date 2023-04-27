import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SelectionTest {

    ArrayList<Chromosome> pop = new ArrayList<>();

    @Test
    public void truncationTest() {
        Selection t = new TruncationSelection();
        ArrayList<Chromosome> pop = new ArrayList<>();
        pop.add(new Chromosome());


        assertFalse(t.performSelection(pop).isEmpty());
    }
}
