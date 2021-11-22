package com.music.utils;

import java.util.Calendar;
import java.util.Date;

public class CurrentDate {
    public static String  getCurrentTime(){
        Date date = new Date();
        Calendar instance = Calendar.getInstance();
        instance.setTime(date);
        return instance.get(Calendar.YEAR)+"-"+(instance.get(Calendar.MONTH)+1)+"-"+instance.get(Calendar.DATE);
    }
}
