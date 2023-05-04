import components.EvolutionComponent;
import logic.Population;
import org.easymock.EasyMock;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class EvolutionComponentTest {



    @Test
    void testUpdatePopulation1() {
        EvolutionComponent ec = new EvolutionComponent();
        Assertions.assertNull(ec.getPop());
        final Population newPop = EasyMock.createMock(Population.class);
        ec.updatePopulation(newPop);
        Assertions.assertNotNull(ec.getPop());
    }

    @Test
    void testUpdatePopulation2() {
        EvolutionComponent ec = new EvolutionComponent();
        Assertions.assertNull(ec.getPop());
        final Population newPop = EasyMock.createMock(Population.class);
        ec.updatePopulation(newPop);
        Assertions.assertNotNull(ec.getPop());
    }
}
