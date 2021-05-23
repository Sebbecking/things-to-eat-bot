package de.sebbecking.lieferandorestaurantscraper.eventhandler.events;

public class VoteActionEvent implements Event {
    public String actionBody;

    public VoteActionEvent(String actionBody){
        this.actionBody = actionBody;
    }
}
