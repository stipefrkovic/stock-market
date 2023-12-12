package nl.rug.aoop.stockapplication;

import nl.rug.aoop.command.CommandHandler;
import nl.rug.aoop.initialization.SimpleViewFactory;
import nl.rug.aoop.messagequeue.queue.OrderedMessageQueue;
import nl.rug.aoop.networking.MessageHandler;
import nl.rug.aoop.networking.server.Server;
import nl.rug.aoop.stockapplication.command.SimpleConsumer;
import nl.rug.aoop.stockapplication.command.StockFactory;
import nl.rug.aoop.stockapplication.command.StockPoller;
import nl.rug.aoop.stockapplication.network.ServerMessageHandler;
import nl.rug.aoop.stockapplication.stock.*;
import nl.rug.aoop.stockapplication.view.StockExchangeData;

import java.io.IOException;

/**
 * StockInit class used to initialize the stock application.
 */
public class StockInit {
    /**
     * Stock application stock manager.
     */
    private StockManager stockManager;
    /**
     * Stock application trader manager.
     */
    private TraderManager traderManager;
    /**
     * Stock application order manager.
     */
    private OrderManager orderManager;
    /**
     * Stock application stock exchange.
     */
    private StockExchange stockExchange;
    /**
     * Initializes the application.
     */

    public void initialize() {
        stockManager = new StockManager();
        stockManager.loadStocks();
        traderManager = new TraderManager();
        traderManager.loadTraders();
        orderManager = new OrderManager();
        OrderMatcherHandler orderMatcherHandler = new OrderMatcherHandler();
        orderMatcherHandler.registerOrderMatcher("LimitOrder", new LimitOrderMatcher());
        stockExchange = new StockExchange(stockManager, traderManager, orderManager, orderMatcherHandler);

        initializeServer();
        initializeView();
    }

    /**
     * Method initializes the server.
     */
    private void initializeServer() {
        OrderedMessageQueue messageQueue = new OrderedMessageQueue();
        PeriodicUpdater periodicUpdater = new PeriodicUpdater(stockExchange);
        StockFactory stockFactory = new StockFactory(stockExchange, messageQueue, periodicUpdater);
        CommandHandler stockCommandHandler = stockFactory.create(StockFactory.Types.STOCK.toString());
        SimpleConsumer simpleConsumer = new SimpleConsumer(messageQueue);
        StockPoller stockPoller = new StockPoller(simpleConsumer, stockCommandHandler);

        CommandHandler networkCommandHandler = stockFactory.create(StockFactory.Types.NETWORK.toString());
        MessageHandler networkMessageHandler = new ServerMessageHandler(networkCommandHandler);

        Integer port = null;
        try {
            port = Integer.parseInt(System.getenv("MESSAGE_QUEUE_PORT"));
        } catch (NumberFormatException e) {
            port = 8080;
        }
        Server server = null;
        try {
            server = new Server(port, networkMessageHandler);
        } catch (IOException e) {
            throw new RuntimeException("Failed to start Server");
        }

        new Thread(server).start();
        new Thread(stockPoller).start();
        new Thread(periodicUpdater).start();
    }

    /**
     * Method initializes the view.
     */
    private void initializeView() {
        StockExchangeData stockExchangeData = new StockExchangeData(stockExchange);
        SimpleViewFactory viewFactory = new SimpleViewFactory();
        viewFactory.createView(stockExchangeData);
    }
}
