package nl.rug.aoop.traderapplication;

import nl.rug.aoop.command.CommandHandler;
import nl.rug.aoop.networking.client.Client;
import nl.rug.aoop.traderapplication.bot.SimpleTradeStrategy;
import nl.rug.aoop.traderapplication.bot.TraderBot;
import nl.rug.aoop.traderapplication.bot.TraderBotManager;
import nl.rug.aoop.traderapplication.command.TraderFactory;
import nl.rug.aoop.traderapplication.interactor.LocalStockManager;
import nl.rug.aoop.traderapplication.interactor.LocalTrader;
import nl.rug.aoop.traderapplication.interactor.LocalTraderManager;
import nl.rug.aoop.traderapplication.interactor.StockInteractor;
import nl.rug.aoop.traderapplication.network.NetworkProducer;
import nl.rug.aoop.traderapplication.network.TraderMessageHandler;
import nl.rug.aoop.util.YamlLoader;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.file.Path;
import java.util.List;

/**
 * TraderInit class used to initialize the trader application.
 */
public class TraderInit {
    private TraderBotManager botManager;
    private LocalTraderManager localTraderManager;
    private LocalStockManager localStockManager;
    private TraderFactory traderFactory;

    /**
     * Initializes the application.
     */
    public void initialize() throws IOException {
        botManager = new TraderBotManager();
        localTraderManager = new LocalTraderManager();
        localStockManager = new LocalStockManager();
        traderFactory = new TraderFactory(localTraderManager, localStockManager);

        initializeTraderBots();
    }

    private void initializeTraderBots() throws IOException {
        YamlLoader tradersLoader = new YamlLoader(Path.of("yaml", "traders_ids.yaml"));
        List<TraderBot> traderBots = List.of(tradersLoader.load(TraderBot[].class));
        for (TraderBot traderBot : traderBots) {
            CommandHandler commandHandler = traderFactory.create(String.valueOf(TraderFactory.Types.TRADER));
            Integer port = null;
            try {
                port = Integer.parseInt(System.getenv("MESSAGE_QUEUE_PORT"));
            } catch (NumberFormatException e) {
                port = 8080;
            }
            Client client = new Client(new InetSocketAddress("localhost", port),
                    new TraderMessageHandler(commandHandler));
            new Thread(client).start();
            StockInteractor stockInteractor = new StockInteractor(client, new NetworkProducer(client));
            LocalTrader localTrader = new LocalTrader();
            localTrader.setId(traderBot.getId());
            localTraderManager.getLocalTraders().put(traderBot.getId(), localTrader);
            stockInteractor.setLocalTrader(localTrader);
            stockInteractor.setLocalStockManager(localStockManager);
            stockInteractor.registerTrader(traderBot.getId());
            traderBot.setStockInteractor(stockInteractor);
            traderBot.setTradeStrategy(new SimpleTradeStrategy());
            botManager.addBot(traderBot);
            new Thread(traderBot).start();
        }
    }
}
