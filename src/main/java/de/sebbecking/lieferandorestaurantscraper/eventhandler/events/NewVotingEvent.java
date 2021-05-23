package de.sebbecking.lieferandorestaurantscraper.eventhandler.events;

import java.time.LocalTime;

public class NewVotingEvent implements Event {
    public LocalTime endTime;
    public NewVotingEvent(LocalTime endTime){
        this.endTime = endTime;
    }
}
