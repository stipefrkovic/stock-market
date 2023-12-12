module stock.application {
    requires static lombok;
    requires org.slf4j;
    requires core;
    requires util;
    requires command;
    requires networking;
    requires messagequeue;
    requires stock.market.ui;
    requires awaitility;
    requires com.fasterxml.jackson.core;
}