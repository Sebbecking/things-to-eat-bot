package de.sebbecking.lieferandorestaurantscraper.eventhandler.events;

import com.slack.api.app_backend.interactive_components.payload.BlockActionPayload;

public class UpdatePostEvent implements Event {
    public String messageTs;

    public UpdatePostEvent(String messageTs){
        this.messageTs = messageTs;
    }
}
