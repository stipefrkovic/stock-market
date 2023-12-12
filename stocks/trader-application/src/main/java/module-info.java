module trader.application {
    requires org.mockito;
    requires networking;
    requires command;
    requires messagequeue;
    requires core;
    requires util;
    requires static lombok;
    requires org.slf4j;
    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.annotation;
    requires awaitility;
    opens nl.rug.aoop.traderapplication.bot to com.fasterxml.jackson.databind;
}