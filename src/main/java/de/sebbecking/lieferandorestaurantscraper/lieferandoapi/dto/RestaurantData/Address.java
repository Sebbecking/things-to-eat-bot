package de.sebbecking.lieferandorestaurantscraper.lieferandoapi.dto.RestaurantData;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

public class Address {
    @JacksonXmlProperty(localName = "st")
    private String street;
    @JacksonXmlProperty(localName = "hn")
    private String houseNo;
    @JacksonXmlProperty(localName = "pc")
    private String postCode;
    @JacksonXmlProperty(localName = "tn")
    private String townName;
    @JacksonXmlProperty(localName = "lt")
    private String latitude;
    @JacksonXmlProperty(localName = "ln")
    private String longitude;
}
