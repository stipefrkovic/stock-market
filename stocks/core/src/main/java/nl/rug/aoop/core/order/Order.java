package nl.rug.aoop.core.order;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

/**
 * Order class represents orders for the stock market.
 */
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type")
@Getter
@Setter
public class Order {
    /**
     * Order type.
     */
    protected String type;
    /**
     * Id of the trader sending the order.
     */
    protected String traderId;
    /**
     * Id of the stock.
     */
    protected String stockId;
    /**
     * Operation represents whether the order is buy/sell.
     */
    protected String operation;
    /**
     * Price of the order.
     */
    protected Integer price;
    /**
     * Amount of stocks the order is worth.
     */
    protected Integer amount;

    /**
     * Default constructor.
     */
    public Order() {
    }

    /**
     * Constructor with builder creates the order.
     *
     * @param builder Order builder.
     */
    public Order(Builder<?> builder) {
        this.type = "Order";
        this.traderId = builder.traderId;
        this.stockId = builder.stockId;
        this.operation = builder.operation;
        this.price = builder.price;
        this.amount = builder.amount;
    }

    /**
     * Creates new order builder.
     *
     * @return New Builder.
     */
    public static Builder newBuilder() {
        return new Builder();
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, traderId, stockId, operation, price, amount);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Order order = (Order) o;
        return this.hashCode() == o.hashCode();
    }

    /**
     * Builder Class for Order.
     *
     * @param <T> Generic extension of Builder.
     */
    public static class Builder<T extends Builder<T>> {
        /**
         * Trader id.
         */
        private String traderId;
        /**
         * Stock id.
         */
        private String stockId;
        /**
         * Operation.
         */
        private String operation;
        /**
         * Price.
         */
        private Integer price;
        /**
         * Amount.
         */
        private Integer amount;

        /**
         * Sets the trader id.
         *
         * @param traderId Trader id.
         * @return Builder.
         */
        public Builder<T> setTraderId(String traderId) {
            this.traderId = traderId;
            return this;
        }

        /**
         * Sets the stock id.
         *
         * @param stockId Stock id.
         * @return Builder.
         */
        public Builder<T> setStockId(String stockId) {
            this.stockId = stockId;
            return this;
        }

        /**
         * Sets the operation.
         *
         * @param operation Operation.
         * @return Builder.
         */
        public Builder<T> setOperation(String operation) {
            this.operation = operation;
            return this;
        }

        /**
         * Sets the price.
         *
         * @param price Price.
         * @return Builder.
         */
        public Builder<T> setPrice(Integer price) {
            this.price = price;
            return this;
        }

        /**
         * Sets the amount.
         *
         * @param amount Amount.
         * @return Builder.
         */
        public Builder<T> setAmount(Integer amount) {
            this.amount = amount;
            return this;
        }

        /**
         * Builds the order.
         *
         * @return New Order.
         */
        public Order build() {
            return new Order(this);
        }
    }
}
