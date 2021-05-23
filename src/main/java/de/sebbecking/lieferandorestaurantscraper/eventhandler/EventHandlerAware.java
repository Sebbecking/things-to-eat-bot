package de.sebbecking.lieferandorestaurantscraper.eventhandler;

public class EventHandlerAware {
    public EventHandler eventHandler;

    public EventHandlerAware(EventHandler eventHandler){
        this.eventHandler = eventHandler;
    }
}
