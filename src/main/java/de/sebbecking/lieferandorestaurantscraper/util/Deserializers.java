package de.sebbecking.lieferandorestaurantscraper.util;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.dataformat.xml.JacksonXmlModule;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.google.gson.Gson;
import com.slack.api.util.json.GsonFactory;

import java.io.IOException;

public class Deserializers {
    public static final ObjectMapper xmlMapper = new XmlMapper(new JacksonXmlModule()).configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    //public static final ObjectMapper jsonMapper = new JsonMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    public static final Gson gson = GsonFactory.createSnakeCase();

    public static class NumericBooleanDeserializer extends JsonDeserializer<Boolean> {

        @Override
        public Boolean deserialize(JsonParser parser, DeserializationContext context) throws IOException, JsonProcessingException {
            return !"0".equals(parser.getText());
        }
    }
}
