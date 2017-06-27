package com.inva.hipstertest.service.util;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Utility class for parsing string to date
 */
public class DataUtil {

    /**
     * Parsing String to ZonedDateTime obj.
     *
     * @param date the date string
     * @return ZonedDateTime obj
     */
    public static ZonedDateTime getZonedDateTime(String date) {
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        LocalDateTime localDateTime = LocalDateTime.parse(date, timeFormatter);
        ZoneId zoneId = ZoneId.systemDefault();
        ZonedDateTime zonedDateTime = localDateTime.atZone(zoneId);
        return zonedDateTime;
    }
}
