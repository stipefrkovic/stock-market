module messagequeue {
    exports nl.rug.aoop.messagequeue.message;
    exports nl.rug.aoop.messagequeue.process;
    exports nl.rug.aoop.messagequeue.queue;
    // Needed for gson to work. If your message queue resides in a sub-package,
    // be sure to open this to com.google.gson as well.
    //    opens nl.rug.aoop.messagequeue to com.google.gson;
    requires static lombok;
    requires org.slf4j;
    requires org.mockito;
    requires com.google.gson;
    requires networking;
    requires command;
}