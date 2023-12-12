module core {
    requires static lombok;
    requires command;
    requires org.slf4j;
    requires com.fasterxml.jackson.annotation;
    requires com.fasterxml.jackson.databind;
    requires messagequeue;
    exports nl.rug.aoop.core.order;
    opens nl.rug.aoop.core.order to com.fasterxml.jackson.databind;
    exports nl.rug.aoop.core.stock;
    opens nl.rug.aoop.core.stock to com.fasterxml.jackson.databind;
    exports nl.rug.aoop.core.trader;
    opens nl.rug.aoop.core.trader to com.fasterxml.jackson.databind;
    requires com.google.gson;
    requires org.mockito;
}