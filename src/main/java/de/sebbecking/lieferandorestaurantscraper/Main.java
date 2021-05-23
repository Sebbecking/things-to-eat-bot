package de.sebbecking.lieferandorestaurantscraper;
import de.sebbecking.lieferandorestaurantscraper.eventhandler.EventHandler;
import de.sebbecking.lieferandorestaurantscraper.eventhandler.events.NewVotingEvent;
import de.sebbecking.lieferandorestaurantscraper.eventhandler.events.ScheduleRegularEvent;
import de.sebbecking.lieferandorestaurantscraper.util.Config;

import java.time.LocalTime;
import java.util.logging.Logger;

public class Main {
    static final Logger logger = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) {
        EventHandler eventHandler = new EventHandler();
        Thread eventHandlerT = new Thread(eventHandler);
        eventHandlerT.start();

        eventHandler.addEvent(new ScheduleRegularEvent(
                new NewVotingEvent(LocalTime.parse(Config.get("VOTE_END_TIME"), Config.timeFormat)),
                LocalTime.parse(Config.get("VOTE_START_TIME"), Config.timeFormat)
        ));
        //eventHandler.addEvent(new ScheduleRegularEvent(new NewVoteEvent(LocalTime.of(0,40))));
    }

}
