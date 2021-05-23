package de.sebbecking.lieferandorestaurantscraper.lieferandoapi.dto.RestaurantData;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import de.sebbecking.lieferandorestaurantscraper.util.Deserializers;

public class Oo {
    @JacksonXmlProperty(localName = "lu")
    private String logoImageUrl;

    @JacksonXmlProperty(localName = "nt")
    private String restaurantInfo;

    @JacksonXmlProperty(localName = "sl")
    private String slogan;

    @JacksonXmlProperty(localName = "rv")
    private int rating;

    @JacksonXmlProperty(localName = "bd")
    private int ratingCount;

    @JacksonXmlProperty(localName = "cim")
    @JsonDeserialize(using = Deserializers.NumericBooleanDeserializer.class)
    private Boolean cim;

    @JacksonXmlProperty(localName = "ft")
    @JsonDeserialize(using = Deserializers.NumericBooleanDeserializer.class)
    private Boolean hasFoodTrackerData;

    @JacksonXmlProperty(localName = "scf")
    @JsonDeserialize(using = Deserializers.NumericBooleanDeserializer.class)
    private Boolean hasStampCards;
}
