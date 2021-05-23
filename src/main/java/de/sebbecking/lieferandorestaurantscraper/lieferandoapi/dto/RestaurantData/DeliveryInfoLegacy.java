package de.sebbecking.lieferandorestaurantscraper.lieferandoapi.dto.RestaurantData;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

public class DeliveryInfoLegacy {
    @JacksonXmlProperty(localName = "op")
    private String op;
    @JacksonXmlProperty(localName = "mpt")
    private String mpt;
    @JacksonXmlProperty(localName = "oh")
    private String oh;
}
