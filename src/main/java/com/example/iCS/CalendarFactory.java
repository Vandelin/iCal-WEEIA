package com.example.iCS;

import net.fortuna.ical4j.data.CalendarOutputter;
import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.Date;
import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.model.property.CalScale;
import net.fortuna.ical4j.model.property.ProdId;
import net.fortuna.ical4j.model.property.Version;
import net.fortuna.ical4j.util.RandomUidGenerator;

import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.HashMap;

public class CalendarFactory {
    private static Calendar iCS = new Calendar();
    private static RandomUidGenerator randomUidGenerator = new RandomUidGenerator();
    private static VEvent event;
    private static java.util.Calendar calendar;
    private static java.util.Date date;

    public static Calendar getCalendar(HashMap<Integer, String> events, int month, int day) {
        try {
            calendar = java.util.Calendar.getInstance();
            iCS.getProperties().add(CalScale.GREGORIAN);
            iCS.getProperties().add(new ProdId("//Kalendarz-weeia/"));
            iCS.getProperties().add(Version.VERSION_2_0);
            date = new SimpleDateFormat("yyyyMM").parse("2019" + month);

            calendar.setTime(date);

            for (Integer i :
                    events.keySet()) {

                calendar.set(java.util.Calendar.DAY_OF_MONTH, i);

                if (events.get(i) != "") {
                    event = new VEvent(new Date(calendar.getTime()), events.get(i));
                    event.getProperties().add(randomUidGenerator.generateUid());
                    iCS.getComponents().add(event);
                }

            }

            return iCS;

        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void printCalendar(Calendar calendar) throws IOException {
        FileOutputStream fout = new FileOutputStream("myCalendar.ics");
        CalendarOutputter outputter = new CalendarOutputter();
        outputter.output(iCS, fout);
    }
}