package de.sebbecking.lieferandorestaurantscraper.lieferandoapi.dto.RestaurantData;


import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import java.util.List;

public class PaymentMethods {
    @JacksonXmlProperty(localName = "me")
    @JacksonXmlElementWrapper(useWrapping = false)
    List<PaymentMethodsData> paymentMethodsData;
}

