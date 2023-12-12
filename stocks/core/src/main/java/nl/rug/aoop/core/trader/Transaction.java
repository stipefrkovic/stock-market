package nl.rug.aoop.core.trader;

/**
 * Record class Transaction keeps track of the transaction.
 * @param stockId Id of stock in the transaction.
 * @param stockAmount Amount of stock.
 * @param stockPrice Price at which transaction was executed.
 */
public record Transaction(String stockId, Integer stockAmount, Integer stockPrice) {
}
