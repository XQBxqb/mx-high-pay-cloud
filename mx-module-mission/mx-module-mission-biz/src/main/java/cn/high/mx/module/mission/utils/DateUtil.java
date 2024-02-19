package cn.high.mx.module.mission.utils;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class DateUtil {
    public static long LocalDateTimeToTimeStamp(LocalDateTime localDateTime){

        // 指定时区，例如系统默认时区
        ZoneId zoneId = ZoneId.systemDefault();

        // 将LocalDateTime转换为ZonedDateTime
        ZonedDateTime zonedDateTime = localDateTime.atZone(zoneId);

        // 将ZonedDateTime转换为Instant
        Instant instant = zonedDateTime.toInstant();

        // 从Instant获取时间戳（毫秒）
        long timestamp = instant.toEpochMilli();

        return timestamp;
    }
}
