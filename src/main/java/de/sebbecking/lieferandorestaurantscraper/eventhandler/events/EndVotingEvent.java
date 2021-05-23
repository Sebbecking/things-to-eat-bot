package de.sebbecking.lieferandorestaurantscraper.eventhandler.events;

public class EndVotingEvent implements Event {
    public String messageTs;
    public EndVotingEvent(String messageTs){
        this.messageTs = messageTs;
    }
}
