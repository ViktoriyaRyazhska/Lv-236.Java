package com.inva.hipstertest.service.util;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

/**
 * Utility class for parsing string to date
 */
public class DateUtil {

    /**
     * Parsing String to ZonedDateTime obj.
     *
     * @param date the date string
     * @return ZonedDateTime obj
     */
    public static ZonedDateTime getZonedDateTime(String date) {
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        LocalDateTime localDateTime = LocalDateTime.parse(date.substring(0,19), timeFormatter);
        ZoneId zoneId = ZoneId.systemDefault();
        return localDateTime.atZone(zoneId).truncatedTo(ChronoUnit.DAYS);
    }
}
