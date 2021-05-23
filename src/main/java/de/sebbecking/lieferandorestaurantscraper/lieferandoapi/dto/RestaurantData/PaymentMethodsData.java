package de.sebbecking.lieferandorestaurantscraper.lieferandoapi.dto.RestaurantData;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import java.math.BigDecimal;

public class PaymentMethodsData {
    @JacksonXmlProperty(localName = "mi")
    public PaymentMethodEnum id;
    @JacksonXmlProperty(localName = "mt")
    public BigDecimal transactionFixed;
    @JacksonXmlProperty(localName = "mf")
    public BigDecimal transactionPercentage;
}
