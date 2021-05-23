package de.sebbecking.lieferandorestaurantscraper.lieferandoapi.dto.RestaurantData;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

public class RestaurantData {
    @JacksonXmlProperty(localName = "nm")
    public String name;

    @JacksonXmlProperty(localName = "ri")
    public String id;

   @JacksonXmlProperty(localName = "mh")
   public String headerImageUrl;

   @JacksonXmlProperty(localName = "ad")
   public Address address;

   @JacksonXmlProperty(localName = "pms")
   public Promotions promotions;

   @JacksonXmlProperty(localName = "lgl")
   public LegalInfoLegacy legalInfoLegacy;

   @JacksonXmlProperty(localName = "dm")
   public DeliveryMethod deliveryMethod;

   @JacksonXmlProperty(localName = "pm")
   public PaymentMethods paymentMethods;

    @JacksonXmlProperty(localName = "dt")
    public DeliveryTime deliveryTime;

    @JacksonXmlProperty(localName = "de")
    public DeliveryTime deliveryExceptions;

    @JacksonXmlProperty(localName = "pt")
    public DeliveryTime pickupTime;

    @JacksonXmlProperty(localName = "pe")
    public DeliveryTime pickupExceptions;

    @JacksonXmlProperty(localName = "dd")
    public DeliveryDestinations deliveryDestinations;

    @JsonIgnore
    @JacksonXmlProperty(localName = "mc", isAttribute = false)
    public MenuCart menuCart;

    @JsonIgnore
    @JacksonXmlProperty(localName = "pd")
    public PopularDishes popularDishes;

    // Unknown meanings..
    @JacksonXmlProperty(localName = "oo")
    public Oo OoObject;


    //estimatedDeliveryTime
    public String bn;

    public String tr;
    public String pne;
    public String rci;
    public String sco;
    public String smid;
    public String rte;
    public String ck;
    public String op;
    public String ac;
    public String ply;
    public String ct;
    public String wd;
    public String ce;
}
