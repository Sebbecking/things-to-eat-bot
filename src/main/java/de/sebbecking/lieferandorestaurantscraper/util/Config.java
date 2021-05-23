package de.sebbecking.lieferandorestaurantscraper.util;

import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class Config {
    static Logger logger = Logger.getLogger(Config.class.getName());
    static public DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("HH:mm:ss");

    // Todo: Import Config from file?
    static Map<String, String> configMap = new HashMap<>();
    static {
        // VALUES REMOVED FOR GITHUB RELEASE
        // configMap.put("SLACK_TOKEN", "");
        // configMap.put("SLACK_CHANNEL", ""); // id of channel
        // configMap.put("PLZ", ""); // PLZ of Testfabrik AG
        // configMap.put("LAT", ""); // coordinates of Testfabrik AG
        // configMap.put("LONG", ""); // coordinates of Testfabrik AG
        // configMap.put("LUNCH_TIME", ""); // format hh:mm:ss
        // configMap.put("VOTE_START_TIME", ""); // format hh:mm:ss
        // configMap.put("VOTE_END_TIME", ""); // format hh:mm:ss
    }


    public static String get(String key){
        if(!configMap.containsKey(key)){
            logger.severe("Config " + key + " not found!");
        }
        return configMap.get(key);
    }
}
