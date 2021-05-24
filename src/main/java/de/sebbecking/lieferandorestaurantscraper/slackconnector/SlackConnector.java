package de.sebbecking.lieferandorestaurantscraper.slackconnector;

import com.slack.api.Slack;
import com.slack.api.methods.MethodsClient;
import com.slack.api.methods.response.chat.ChatPostMessageResponse;
import com.slack.api.methods.response.chat.ChatUpdateResponse;
import com.slack.api.model.block.LayoutBlock;
import de.sebbecking.lieferandorestaurantscraper.eventhandler.EventHandler;
import de.sebbecking.lieferandorestaurantscraper.eventhandler.EventHandlerAware;
import de.sebbecking.lieferandorestaurantscraper.httpserver.SlackCallbackServer;
import de.sebbecking.lieferandorestaurantscraper.lieferandoapi.dto.RestaurantData.RestaurantData;
import de.sebbecking.lieferandorestaurantscraper.util.Config;
import de.sebbecking.lieferandorestaurantscraper.votingmanager.Votes;
import de.sebbecking.lieferandorestaurantscraper.votingmanager.VotingManager;

import java.time.LocalTime;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import static com.slack.api.model.block.Blocks.section;
import static com.slack.api.model.block.composition.BlockCompositions.markdownText;
import static com.slack.api.model.block.composition.BlockCompositions.plainText;
import static com.slack.api.model.block.element.BlockElements.button;

public class SlackConnector extends EventHandlerAware {
    private SlackCallbackServer slackCallbackServer;
    private final Slack slack = Slack.getInstance();
    private final MethodsClient slack_methods = slack.methods(Config.get("SLACK_TOKEN"));
    private VotingManager votingManager;
    static final Logger logger = Logger.getLogger(SlackConnector.class.getName());

    public SlackConnector(EventHandler eventHandler, VotingManager votingManager) {
        super(eventHandler);
        this.votingManager = votingManager;
    }

    public void updatePost(String messageTs){
        ChatUpdateResponse response = null;
        Votes votes = this.votingManager.getVotes(messageTs);
        List<RestaurantData> restaurantData = this.votingManager.getVotes(messageTs).restaurants;

        try {
            response = slack_methods.chatUpdate(req -> req
                    .channel(Config.get("SLACK_CHANNEL"))
                    .ts(messageTs)
                    .blocks(getSlackMessageForRestaurantsAndVotes(restaurantData, votes))
            );
        } catch (Exception e) {
            logger.severe(e.getMessage());
        }

        if (!response.isOk()) {
            logger.severe("Error when sending message: ");
            logger.severe(response.getError());
        }
    }

    public String newPost(List<RestaurantData> restaurantData){
        Votes votes = new Votes(restaurantData);

        ChatPostMessageResponse response = null;
        try {
            response = slack_methods.chatPostMessage(req -> req
                    .channel(Config.get("SLACK_CHANNEL"))
                    .blocks(getSlackMessageForRestaurantsAndVotes(restaurantData, votes))
            );
        } catch (Exception e) {
            logger.severe(e.getMessage());
        }

        if (response.isOk()) {
            this.votingManager.newVoting(response.getMessage().getTs(), restaurantData, votes);
        } else {
            logger.severe("Error when sending message: ");
            logger.severe(response.getError());
        }

        return response.getMessage().getTs();
    }

    private List<LayoutBlock> getSlackMessageForRestaurantsAndVotes(List<RestaurantData> openRestaurants, Votes votes) {
        if(votes.ended){
            openRestaurants = openRestaurants.stream().filter(restaurant -> votes.getNumberOfVotersForRestaurant(restaurant.id) > 0).collect(Collectors.toList());
        }
        List<LayoutBlock> blockList = new LinkedList<>();
        blockList.add(section(section -> section.text(markdownText("@here Essens-Abstimmung!" + (votes.ended ? " (_Beendet_)" : ((votes.endTime != null) ? (" Endet " + votes.endTime.format(Config.timeFormat)) : ""))))));
        openRestaurants.forEach(openRestaurant -> blockList
                .add(section(section -> {
                    section.text(markdownText(
                                "*" + openRestaurant.name + "*\n" + votes.getStringListOfVotersForRestaurant(openRestaurant.id)
                                    + "\t _" + votes.getNumberOfVotersForRestaurant(openRestaurant.id) + "_ _Stimme(n)_"));
                    if(!votes.ended) {
                        section.accessory(button(buttonElementBuilder -> buttonElementBuilder
                                .actionId("TOGGLE_VOTE")
                                .value(openRestaurant.id)
                                .text(plainText("Abstimmen!"))));
                    }
                    return section;
                }))
        );
        return blockList;
    }
}
