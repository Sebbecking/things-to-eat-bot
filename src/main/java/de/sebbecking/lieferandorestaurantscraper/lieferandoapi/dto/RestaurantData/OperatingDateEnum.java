package de.sebbecking.lieferandorestaurantscraper.lieferandoapi.dto.RestaurantData;
import com.fasterxml.jackson.annotation.JsonCreator;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.logging.Logger;

public enum OperatingDateEnum {
    SUNDAY(0),
    MONDAY(1),
    TUESDAY(2),
    WEDNESDAY(3),
    THURSDAY(4),
    FRIDAY(5),
    SATURDAY(6);

    static final Logger logger = Logger.getLogger(OperatingDateEnum.class.getName());
    public int i;
    OperatingDateEnum(int i) {
        this.i = i;
    }

    public static OperatingDateEnum forCode(int i) {
        for (OperatingDateEnum element : values()) {
            if (element.i == i) {
                return element;
            }
        }
        logger.severe("OperatingDay unknown: " + String.valueOf(i));
        return null;
    }

    @JsonCreator
    public static OperatingDateEnum forValue(String v) {
        // A bit hacky.. For Exception, this field contains a concrete Date. I realized this after writing the logic,
        // so I just transform that date to the Day of week. This might be buggy, if lieferando serves the dates more than a week in advance.
        if(v.matches("[0-9]{4}-[0-9]{2}-[0-9]{2}")){
            return OperatingDateEnum.forCode(LocalDate.parse(v, DateTimeFormatter.ofPattern("yyyy-MM-dd")).getDayOfWeek().getValue()%7);
        }
        return OperatingDateEnum.forCode(Integer.parseInt(v));
    }

}
