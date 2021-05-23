package de.sebbecking.lieferandorestaurantscraper.lieferandoapi.dto.RestaurantData;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import java.time.LocalTime;
import java.util.List;

public class TimeWindow {
        @JacksonXmlProperty(localName = "od")
        public OperatingDateEnum operatingDate;
        @JacksonXmlProperty(localName = "ru")
        @JacksonXmlElementWrapper(useWrapping = false)
        public List<TimeHolder> windows;

        public Boolean timeInWindows(LocalTime time){
                return this.windows.stream().anyMatch(window -> window.timeInRange(time));
        }
}
