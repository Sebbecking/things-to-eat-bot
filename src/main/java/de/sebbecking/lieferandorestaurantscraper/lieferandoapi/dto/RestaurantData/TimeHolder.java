package de.sebbecking.lieferandorestaurantscraper.lieferandoapi.dto.RestaurantData;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class TimeHolder {
    static DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("HH:mm:ss");

    @JacksonXmlProperty(localName = "st")
    public String startTime;
    @JacksonXmlProperty(localName = "en")
    public String endTime;

    public Boolean timeInRange(LocalTime time){
        LocalTime from = LocalTime.parse(this.startTime, timeFormat);
        LocalTime to = LocalTime.parse(this.endTime, timeFormat);
        return time.isAfter(from) && time.isBefore(to);
    }
}
