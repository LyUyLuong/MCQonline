package com.javaweb.utils;

import com.javaweb.model.raw.FormRaw;

import java.sql.Time;

public class TimeUtils {
    public static Time convertToTIme(FormRaw form) {
        Integer hours = Integer.valueOf(form.getCompleteTime()) / 3600;
        Integer minutes = Integer.valueOf(form.getCompleteTime()) / 60;
        Integer seconds = Integer.valueOf(form.getCompleteTime()) % 60;

        Time time = Time.valueOf(String.format("%02d:%02d:%02d", hours, minutes, seconds));

        return time;
    }
}
