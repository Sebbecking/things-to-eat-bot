package de.sebbecking.lieferandorestaurantscraper.lieferandoapi.dto.RestaurantData;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

public class LegalInfoLegacy {
    @JacksonXmlProperty(localName = "col")
    private String content;
}
