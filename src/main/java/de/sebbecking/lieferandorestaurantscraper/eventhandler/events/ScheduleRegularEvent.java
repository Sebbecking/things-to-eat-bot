package de.sebbecking.lieferandorestaurantscraper.eventhandler.events;

import java.time.LocalTime;

public class ScheduleRegularEvent implements Event {
    public final Event event;
    public final LocalTime time;

    public ScheduleRegularEvent(Event event, LocalTime time){
        this.event = event;
        this.time = time;
    }
}
