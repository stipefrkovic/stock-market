package nl.rug.aoop.traderapplication.bot;

import lombok.Getter;

import java.util.ArrayList;

/**
 * TraderBotManager class manages the tradef bots.
 */
public class TraderBotManager {
    /**
     * List of all trader bots.
     */
    @Getter
    private ArrayList<TraderBot> bots;

    /**
     * Constructor constructs the TraderBotManager class.
     */
    public TraderBotManager() {
        bots = new ArrayList<>();
    }

    /**
     * Method adds a trader bot to the list.
     * @param bot Bot to be added.
     */
    public void addBot(TraderBot bot) {
        bots.add(bot);
    }

    /**
     * Method removes a trader bot from the list.
     * @param bot Bot to be removed.
     */
    public void removeBot(TraderBot bot) {
        bots.remove(bot);
    }
}
