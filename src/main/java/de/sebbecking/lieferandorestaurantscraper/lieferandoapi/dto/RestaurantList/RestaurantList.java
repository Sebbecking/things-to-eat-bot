package de.sebbecking.lieferandorestaurantscraper.lieferandoapi.dto.RestaurantList;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import java.util.List;

public class RestaurantList {
    @JsonIgnore
    @JacksonXmlProperty(localName = "bms")
    public String bms;

    @JsonIgnore
    @JacksonXmlProperty(localName = "cp")
    public String cp;

    @JacksonXmlProperty(localName = "ct")
    public String currentTime;

    @JacksonXmlProperty(localName = "wd")
    public int wd;

    @JacksonXmlProperty(localName = "rt")
    @JacksonXmlElementWrapper(useWrapping = false)
    public List<RestuarantListEntry> restaurantList;
}
