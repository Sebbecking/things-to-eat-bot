package de.sebbecking.lieferandorestaurantscraper.httpserver;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import de.sebbecking.lieferandorestaurantscraper.eventhandler.EventHandler;
import de.sebbecking.lieferandorestaurantscraper.eventhandler.EventHandlerAware;
import de.sebbecking.lieferandorestaurantscraper.eventhandler.events.VoteActionEvent;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.logging.Logger;

public class VoteActionHandler extends EventHandlerAware implements HttpHandler {
    static final Logger logger = Logger.getLogger(VoteActionHandler.class.getName());

    public VoteActionHandler(EventHandler eventHandler) {
        super(eventHandler);
    }

    @Override
    public void handle(HttpExchange t) throws IOException {
        String result = IOUtils.toString(t.getRequestBody(), StandardCharsets.UTF_8).replaceFirst("payload=","");
        this.eventHandler.addEvent(new VoteActionEvent(java.net.URLDecoder.decode(result, StandardCharsets.UTF_8)));

        logger.info("Received VoteAction");
        byte[] response = "OK".getBytes();
        t.sendResponseHeaders(200, response.length);
        OutputStream os = t.getResponseBody();
        os.write(response);
        os.close();
    }
}
