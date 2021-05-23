package de.sebbecking.lieferandorestaurantscraper.lieferandoapi.dto.RestaurantData;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

public class Promotions {
    @JacksonXmlProperty(localName = "onp")
    private String onp;
    @JacksonXmlProperty(localName = "ofp")
    private String ofp;
}
