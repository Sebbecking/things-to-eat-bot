package de.sebbecking.lieferandorestaurantscraper.lieferandoapi.dto.RestaurantList;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

public class RestuarantListEntry {
    @JacksonXmlProperty(localName = "nm")
    public String name;

    @JacksonXmlProperty(localName = "id")
    public String id;
}
