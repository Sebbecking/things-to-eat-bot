package de.sebbecking.lieferandorestaurantscraper.lieferandoapi.dto.RestaurantData;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import java.util.*;
import java.util.stream.Collectors;

public class DeliveryTime {
    @JacksonXmlProperty(localName = "dr")
    @JacksonXmlElementWrapper(useWrapping = false)
    public List<TimeWindow> timeWindows;

    public Optional<TimeWindow> getForOperatingDate(OperatingDateEnum od){
        if (this.timeWindows == null){
            return Optional.empty();
        }
        List<TimeWindow> results = this.timeWindows.stream().filter(tw -> tw.operatingDate == od).collect(Collectors.toList());
        if(results.size() != 1){
            return Optional.empty();
        }
        return Optional.of(results.get(0));
    }
}
