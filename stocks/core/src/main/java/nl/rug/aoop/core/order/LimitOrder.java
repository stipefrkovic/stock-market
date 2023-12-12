package nl.rug.aoop.core.order;

import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

/**
 * LimitOrder class represents the limit order.
 */
@Getter
@Setter
public class LimitOrder extends Order {
    /**
     * Order price limit.
     */
    private Integer limit;

    /**
     * Default constructor.
     */
    public LimitOrder() {
    }

    /**
     * Constructor creates the limit order.
     * @param builder Limit order builder.
     */
    public LimitOrder(Builder builder) {
        super(builder);
        this.type = "LimitOrder";
        this.limit = price;
    }

    /**
     * Method creates new builder.
     * @return New Builder.
     */
    public static Builder newBuilder() {
        return new Builder();
    }

    @Override
    public int hashCode() {
        return Objects.hash(limit, type, traderId, stockId, operation, price, amount);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        LimitOrder order = (LimitOrder) o;
        return this.hashCode() == o.hashCode();
    }

    /**
     * Builder class for the limit order.
     */
    public static class Builder extends Order.Builder<Builder> {
        /**
         * Limit order limit.
         */
        private Integer limit;

        /**
         * Sets the limit order limit.
         * @param limit Limit order limit.
         * @return Builder.
         */
        public Builder setLimit(Integer limit) {
            this.limit = limit;
            return this;
        }

        /**
         * Builds the limit order.
         * @return New LimitOrder.
         */
        public LimitOrder build() {
            return new LimitOrder(this);
        }
    }
}
