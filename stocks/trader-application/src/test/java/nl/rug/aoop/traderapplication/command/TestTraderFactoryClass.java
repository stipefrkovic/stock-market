package nl.rug.aoop.traderapplication.command;

import nl.rug.aoop.traderapplication.interactor.LocalStockManager;
import nl.rug.aoop.traderapplication.interactor.LocalTraderManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * TestTraderFactoryClass tests the TraderFactoryClass
 */
public class TestTraderFactoryClass {
    /**
     * Tested TraderFactory.
     */
    private TraderFactory traderFactory;
    /**
     * LocalStock manager used in registering commands.
     */
    private LocalStockManager stockManager;
    /**
     * LocalTraderManager used in registering commands.
     */
    private LocalTraderManager traderManager;

    /**
     * Method creates the trader factory before each test.
     */
    @BeforeEach
    void createTraderFactory() {
        stockManager = new LocalStockManager();
        traderManager = new LocalTraderManager();
        traderFactory = new TraderFactory(traderManager, stockManager);
    }

    /**
     * Method tests that the factory is correctly constructed
     */
    @Test
    void testConstructor() {
        assertNotNull(traderFactory.getLocalTraderManager());
        assertNotNull(traderFactory.getLocalStockManager());
    }

    /**
     * Method tests that the factory correctly creates a product when given a valid type.
     */
    @Test
    void testLegalCreate() {
        assertNotNull(traderFactory.create(String.valueOf(TraderFactory.Types.TRADER)));
    }

    /**
     * Method tests that the factory throws the correct exception when given an invalid type.
     */
    @Test
    void testIllegalCreate() {
        assertThrows(IllegalArgumentException.class, () -> traderFactory.create("wrong"));
    }
}
