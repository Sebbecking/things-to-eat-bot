package de.sebbecking.lieferandorestaurantscraper.lieferandoapi.dto.RestaurantData;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum PaymentMethodEnum {
    PAYMENT_BC_HOME(20),
    PAYMENT_CASH(0),
    PAYMENT_CC_HOME(4),
    PAYMENT_CREDITCARD(6),
    PAYMENT_GOOGLE_PAY(30),
    PAYMENT_IDEAL(3),
    PAYMENT_MRCASH(16),
    PAYMENT_PAYPAL(18),
    PAYMENT_PAYU(31),
    PAYMENT_PIN_HOME(2),
    PAYMENT_SOFORT(15),
    PAYMENT_VOUCHER(13),
    PAYMENT_BITCOIN(27),
    PAYMENT_OTHER(99);

    public int i;
    PaymentMethodEnum(int i) {
        this.i = i;
    }

    public static PaymentMethodEnum forCode(int i) {
        for (PaymentMethodEnum element : values()) {
            if (element.i == i) {
                return element;
            }
        }
        System.out.println("Unknown Payment Id: " + String.valueOf(i));
        return PaymentMethodEnum.PAYMENT_OTHER;
    }

    @JsonCreator
    public static PaymentMethodEnum forValue(String v) {
        return PaymentMethodEnum.forCode(Integer.parseInt(v));
    }
}