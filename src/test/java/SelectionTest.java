import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SelectionTest {

    ArrayList<Chromosome> pop = new ArrayList<>();
    FileLoader fl = new FileLoader();

    @Test
    public void truncationTest() {
        Selection t = new TruncationSelection();
        ArrayList<Chromosome> pop = new ArrayList<>();
        Chromosome c1 = fl.load(new File("./genotypes/smile.txt"));
        Chromosome c2 = fl.load(new File("./genotypes/size_100.txt"));
        Chromosome c3 = fl.load(new File("./genotypes/arc_reactor.txt"));
        c1.setRank(1);
        c2.setRank(2);
        c3.setRank(3);
        pop.add(c1);
        pop.add(c2);
        pop.add(c3);

        assertFalse(t.performSelection(pop).isEmpty());
    }

    @Test
    public void rankedTest() {
        Selection t = new RankSelection();
        ArrayList<Chromosome> pop = new ArrayList<>();
        Chromosome c1 = fl.load(new File("./genotypes/smile.txt"));
        Chromosome c2 = fl.load(new File("./genotypes/size_100.txt"));
        Chromosome c3 = fl.load(new File("./genotypes/arc_reactor.txt"));
        pop.add(c1);
        pop.add(c2);
        pop.add(c3);

        assertFalse(t.performSelection(pop).isEmpty());
    }

    @Test
    public void RouletteTest() {
        Selection t = new RouletteSelection();
        ArrayList<Chromosome> pop = new ArrayList<>();
        Chromosome c1 = fl.load(new File("./genotypes/smile.txt"));
        Chromosome c2 = fl.load(new File("./genotypes/size_100.txt"));
        Chromosome c3 = fl.load(new File("./genotypes/arc_reactor.txt"));
        c1.setRank(1);
        c2.setRank(2);
        c3.setRank(3);
        pop.add(c1);
        pop.add(c2);
        pop.add(c3);

        assertFalse(t.performSelection(pop).isEmpty());
    }

    @Test
    public void WorstTest() {
        Selection t = new RankSelection();
        ArrayList<Chromosome> pop = new ArrayList<>();
        Chromosome c1 = fl.load(new File("./genotypes/smile.txt"));
        Chromosome c2 = fl.load(new File("./genotypes/size_100.txt"));
        Chromosome c3 = fl.load(new File("./genotypes/arc_reactor.txt"));
        pop.add(c1);
        pop.add(c2);
        pop.add(c3);

        assertFalse(t.performSelection(pop).isEmpty());
    }
    @Test
    public void DiversityTest() {
        Selection t = new RankSelection();
        ArrayList<Chromosome> pop = new ArrayList<>();
        Chromosome c1 = fl.load(new File("./genotypes/smile.txt"));
        Chromosome c2 = fl.load(new File("./genotypes/size_100.txt"));
        Chromosome c3 = fl.load(new File("./genotypes/arc_reactor.txt"));
        pop.add(c1);
        pop.add(c2);
        pop.add(c3);

        assertFalse(t.performSelection(pop).isEmpty());
    }
}
