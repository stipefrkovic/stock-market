package nl.rug.aoop.stockapplication.command;

import lombok.extern.slf4j.Slf4j;
import nl.rug.aoop.command.Command;
import nl.rug.aoop.networking.Communicator;
import nl.rug.aoop.stockapplication.stock.PeriodicUpdater;

import java.util.Map;

import static java.util.Objects.requireNonNull;


/**
 * Class RegisterTraderCommand that implements Command. It registers a Trader in the contained PeriodicUpdater..
 */
@Slf4j
public class RegisterTraderCommand implements Command {
    /**
     * PeriodicUpdater that should register the Trader.
     */
    private final PeriodicUpdater periodicUpdater;

    /**
     * Constructor for RegisterTraderCommand.
     * @param periodicUpdater PeriodicUpdater that should register the Trader.
     */
    public RegisterTraderCommand(PeriodicUpdater periodicUpdater) {
        this.periodicUpdater = requireNonNull(periodicUpdater);
    }

    /**
     * Method executes the Command with given Map of Objects available to use.
     *
     * @param options Map(String, Object) with Objects for the Command to use.
     */
    @Override
    public void execute(Map<String, Object> options) {
        String traderId = (String) options.get("Body");
        Communicator communicator = (Communicator) options.get("Communicator");
        periodicUpdater.registerTrader(traderId, communicator);
        log.info("Registering trader.");
    }
}
