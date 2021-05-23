package de.sebbecking.lieferandorestaurantscraper.lieferandoapi.dto.RestaurantData;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

public class DeliveryMethod {
    @JacksonXmlProperty(localName = "ah")
    private Integer orderMethod;
    @JacksonXmlProperty(localName = "dl")
    DeliveryInfoLegacy delivery;
    @JacksonXmlProperty(localName = "pu")
    DeliveryInfoLegacy pickup;
}
