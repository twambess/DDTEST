package com.shvetsov.denis;

import com.digdes.school.DatesToCronConvertException;
import com.digdes.school.DatesToCronConverter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class DatesConverter implements DatesToCronConverter {

    public final String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss";
    public final String CRON_DATE_FORMAT = "ss mm HH dd MM yyyy";

    @Override
    public String convert(List<String> dates) throws DatesToCronConvertException {
        Collections.sort(dates);
        List<Calendar> calendars = new ArrayList<>();
        for(String date : dates)
            calendars.add(StringToDate(date));
        System.out.println(calendars.get(0).getTimeInMillis() - calendars.get(1).getTimeInMillis());
        System.out.println(calendars.get(0).get(Calendar.SECOND));
        System.out.println(getCron(calendars));
        return null;
    }

    public String getCron(List<Calendar> dates){
        Map<Integer, Integer> map = new HashMap<>();
        StringBuilder cron = new StringBuilder("* * * * * *");
        int sub = 0;
        for(int i = 0; i < dates.size(); i++){
            for(int j = i+1; j < dates.size()-1; j++){
                sub = Math.abs(dates.get(i).get(Calendar.HOUR) - dates.get(j).get(Calendar.HOUR));
                if(map.containsKey(sub))
                    map.replace(sub, map.get(sub) + 1);
                else
                    map.put(sub, 1);
            }
        }
        for (HashMap.Entry<Integer, Integer> m : map.entrySet()) {
            if(m.getValue() > dates.size()/2){
                cron.replace(6, 7, "*/" + m.getKey() + " ");
                break;
            }
        }
        for(int i = 0; i < dates.size(); i++){
            for(int j = i+1; j < dates.size()-1; j++){
                sub = Math.abs(dates.get(i).get(Calendar.MINUTE) - dates.get(j).get(Calendar.MINUTE));
                if(map.containsKey(sub))
                    map.replace(sub, map.get(sub) + 1);
                else
                    map.put(sub, 1);
            }
        }
        for (HashMap.Entry<Integer, Integer> m : map.entrySet()) {
            if(m.getValue() > dates.size()/2){
                cron.replace(10, 11, "*/" + m.getKey()+ " ");
                break;
            }
        }
        for(int i = 0; i < dates.size(); i++){
            for(int j = i+1; j < dates.size()-1; j++){
                sub = Math.abs(dates.get(i).get(Calendar.SECOND) - dates.get(j).get(Calendar.SECOND));
                if(map.containsKey(sub))
                    map.replace(sub, map.get(sub) + 1);
                else
                    map.put(sub, 1);
            }
        }
        for (HashMap.Entry<Integer, Integer> m : map.entrySet()) {
            if(m.getValue() > dates.size()/2){
                cron.replace(14, 15, "*/" + m.getKey()+ " ");
                break;
            }
        }
        return cron.toString();
    }

    public String DateToString(Date date){
        SimpleDateFormat sdf = new SimpleDateFormat(CRON_DATE_FORMAT);
        return sdf.format(date);
    }

    public Calendar StringToDate(String date) {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        Calendar calendar = Calendar.getInstance();
        try {
            calendar.setTime(sdf.parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return calendar;
    }

    public static void main(String[] args) throws DatesToCronConvertException {
        List<String> list = new ArrayList<String>();
        DatesConverter b = new DatesConverter();
        list.add("2022-01-26T10:00:00");
        list.add("2022-01-25T07:00:00");
        list.add("2022-01-25T09:20:00");
        list.add("2022-01-25T08:40:00");
        list.add("2022-01-25T09:20:00");
        b.convert(list);
    }


    @Override
    public String getImplementationInfo() {
        System.out.println("Швецов Денис Игоревич");
        System.out.println("DatesConverter");
        System.out.println("com.shvetsov.denis;");
        System.out.println("");
        return null;
    }
}
