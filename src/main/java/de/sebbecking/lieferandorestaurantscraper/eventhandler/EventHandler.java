package de.sebbecking.lieferandorestaurantscraper.eventhandler;

import com.slack.api.app_backend.interactive_components.payload.BlockActionPayload;
import de.sebbecking.lieferandorestaurantscraper.eventhandler.events.*;
import de.sebbecking.lieferandorestaurantscraper.lieferandoapi.LieferandoApi;
import de.sebbecking.lieferandorestaurantscraper.httpserver.SlackCallbackServer;
import de.sebbecking.lieferandorestaurantscraper.lieferandoapi.dto.RestaurantData.RestaurantData;
import de.sebbecking.lieferandorestaurantscraper.scheduler.Scheduler;
import de.sebbecking.lieferandorestaurantscraper.slackconnector.SlackConnector;
import de.sebbecking.lieferandorestaurantscraper.util.Deserializers;
import de.sebbecking.lieferandorestaurantscraper.votingmanager.VotingManager;

import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Logger;

public class EventHandler implements Runnable {
    private SlackCallbackServer slackCallbackServer = new SlackCallbackServer(this);
    private LieferandoApi lieferandoApi = new LieferandoApi(this);
    private VotingManager votingManager = new VotingManager(this);
    private Scheduler scheduler = new Scheduler(this);
    private SlackConnector slackConnector = new SlackConnector(this, votingManager);
    static final Logger logger = Logger.getLogger(EventHandler.class.getName());
    private boolean running = true;


    private LinkedBlockingQueue<Event> eventQueue = new LinkedBlockingQueue<>();

    public EventHandler(){ }

    public void addEvent(Event e){
        try {
            this.eventQueue.put(e);
        } catch (InterruptedException ex) {
            shutdownGracefully();
        }
    }

    public void shutdownGracefully(){
        // TODO: Handle Shutdown.
        logger.info("Catched InterruptedException. Shutting Down.");
        this.running = false;
    }

    @Override
    public void run(){
        while(running){
            try {
                Event e = eventQueue.take();
                if (e instanceof VoteActionEvent) { this.handleVoteAction((VoteActionEvent) e); }
                if (e instanceof UpdatePostEvent) { this.handleUpdatePost((UpdatePostEvent) e); }
                if (e instanceof NewVotingEvent) { this.handleNewVoting((NewVotingEvent) e); }
                if (e instanceof EndVotingEvent) { this.handleEndVoting((EndVotingEvent) e); }
                if (e instanceof ScheduleRegularEvent) { this.handleScheduleRegular((ScheduleRegularEvent) e); }
            } catch (InterruptedException ex){
                shutdownGracefully();
            }
        }
    }

    private void handleVoteAction(VoteActionEvent e){
        BlockActionPayload blockActionPayload;
        try {
            blockActionPayload = Deserializers.gson.fromJson(e.actionBody, BlockActionPayload.class);
            if(        blockActionPayload.getMessage() == null
                    || blockActionPayload.getMessage().getTs() == null
                    || blockActionPayload.getUser() == null
                    || blockActionPayload.getUser().getUsername() == null
                    || blockActionPayload.getActions() == null
            ){
                logger.info("Ignoring malformed BlockAction");
                return;
            }
        } catch (Exception ex) {
            logger.severe("Unable to deserialize BlockAction: " + e.actionBody);
            return;
        }

        String messageTs = blockActionPayload.getMessage().getTs();
        blockActionPayload.getActions().stream()
                .filter(action -> action.getActionId().equals("TOGGLE_VOTE"))
                .forEach(action -> this.votingManager.getVotes(messageTs).toggleVoteForRestaurant(action.getValue(), blockActionPayload.getUser().getUsername()));
        this.addEvent(new UpdatePostEvent(messageTs));
    }

    private void handleNewVoting(NewVotingEvent e){
        List<RestaurantData> openRestaurants = this.lieferandoApi.getRestaurantsOpenTodayAtLunchTime();
        String messageTs = this.slackConnector.newPost(openRestaurants);
        if(e.endTime != null){
            this.scheduler.scheduleEvent(e.endTime, new EndVotingEvent(messageTs));
            this.votingManager.getVotes(messageTs).endTime = e.endTime;
            this.slackConnector.updatePost(messageTs);
        }
    }

    private void handleEndVoting(EndVotingEvent e){
        this.votingManager.endVote(e.messageTs);
        this.slackConnector.updatePost(e.messageTs);
    }

    private void handleUpdatePost(UpdatePostEvent e){
        this.slackConnector.updatePost(e.messageTs);
    }

    private void handleScheduleRegular(ScheduleRegularEvent e){
        this.scheduler.scheduleDailyEvent(e.time, e.event);
    }
}
