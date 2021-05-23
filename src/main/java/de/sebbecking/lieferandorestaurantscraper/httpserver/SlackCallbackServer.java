package de.sebbecking.lieferandorestaurantscraper.httpserver;

import com.sun.net.httpserver.HttpServer;
import de.sebbecking.lieferandorestaurantscraper.eventhandler.EventHandler;
import de.sebbecking.lieferandorestaurantscraper.eventhandler.EventHandlerAware;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.logging.Logger;

public class SlackCallbackServer extends EventHandlerAware {
    static final Logger logger = Logger.getLogger(SlackCallbackServer.class.getName());

    public SlackCallbackServer(EventHandler eventHandler) {
        super(eventHandler);
        try {
            HttpServer server = HttpServer.create(new InetSocketAddress(1990), 0);
            server.createContext("/vote", new VoteActionHandler(eventHandler));
            server.setExecutor(null);
            server.start();
        } catch (IOException e) {
            logger.severe("Could not start HTTP Server!");
            logger.severe(e.getMessage());
        }
    }
}
