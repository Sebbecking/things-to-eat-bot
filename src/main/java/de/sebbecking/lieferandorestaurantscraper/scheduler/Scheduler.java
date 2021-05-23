package de.sebbecking.lieferandorestaurantscraper.scheduler;

import de.sebbecking.lieferandorestaurantscraper.eventhandler.EventHandler;
import de.sebbecking.lieferandorestaurantscraper.eventhandler.EventHandlerAware;
import de.sebbecking.lieferandorestaurantscraper.eventhandler.events.Event;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

class ScheduledEventTask extends TimerTask {
    private final Event event;
    private final EventHandler eventHandler;

    public ScheduledEventTask(EventHandler eventHandler, Event event){
        this.event = event;
        this.eventHandler = eventHandler;
    }

    @Override
    public void run() {
        this.eventHandler.addEvent(this.event);
    }
}

public class Scheduler extends EventHandlerAware {
    private Timer timer = new Timer(true);
    public Scheduler(EventHandler e){
        super(e);
    }

    public void scheduleEvent(LocalTime time, Event event){
        ScheduledEventTask task = new ScheduledEventTask(this.eventHandler, event);
        timer.schedule(task, dateFromLocalTime(time));
    }

    public void scheduleDailyEvent(LocalTime time, Event event){
        ScheduledEventTask task = new ScheduledEventTask(this.eventHandler, event);
        timer.schedule(task, dateFromLocalTime(time), 1000l * 60l * 60l * 24l);
    }

    private Date dateFromLocalTime(LocalTime time){
        return java.util.Date.from(time.atDate(LocalDate.now()).atZone(ZoneId.systemDefault()).toInstant());

    }
}
