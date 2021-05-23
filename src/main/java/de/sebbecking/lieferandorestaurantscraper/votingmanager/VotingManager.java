package de.sebbecking.lieferandorestaurantscraper.votingmanager;

import de.sebbecking.lieferandorestaurantscraper.eventhandler.EventHandler;
import de.sebbecking.lieferandorestaurantscraper.eventhandler.EventHandlerAware;
import de.sebbecking.lieferandorestaurantscraper.lieferandoapi.dto.RestaurantData.RestaurantData;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;

public class VotingManager extends EventHandlerAware {
    private Map<String, Votes> votings = new ConcurrentHashMap<>();
    static Logger logger = Logger.getLogger(VotingManager.class.getName());

    public VotingManager(EventHandler eventHandler){
        super(eventHandler);
    }

    public void endVote(String messageTs){
        this.getVotes(messageTs).endVote();
    }

    public void endAllVotes(){
        this.votings.values().forEach(vote -> vote.endVote());
    }

    public void newVoting(String messageTs, List<RestaurantData> restaurants, Votes initialVotes){
        if(this.votings.containsKey(messageTs)){
            logger.severe("Voting already exists!");
            return;
        }
        this.votings.put(messageTs, initialVotes);
    }

    public Votes getVotes(String messageTs){
        if(!this.votings.containsKey(messageTs)){
            return new Votes(Collections.emptyList());
        }
        return this.votings.get(messageTs);
    }
}
