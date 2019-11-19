package com.example.iCS;

import net.fortuna.ical4j.model.Calendar;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.Date;

@Controller
@RequestMapping("/calendar")
public class CalendarController {

    @GetMapping
    public boolean getCalendarForYearAndMonth(@RequestParam(name = "year") String year,
                                           @RequestParam(name = "month") String month){
        Integer ye = Integer.parseInt(year);
        Integer mo = Integer.parseInt(month);
        HTML html = new HTML("http://www.weeia.p.lodz.pl" , ye, mo);
        Calendar calendar = CalendarFactory.getCalendar(html.getEvents(), html.getMonth(), html.getYear());
        try {
            CalendarFactory.printCalendar(calendar);
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    @GetMapping("/now")
    public boolean getCalendarNow(){
        HTML html = new HTML("http://www.weeia.p.lodz.pl" , java.util.Calendar.getInstance().get(java.util.Calendar.YEAR), java.util.Calendar.getInstance().get(java.util.Calendar.MONTH));
        Calendar calendar = CalendarFactory.getCalendar(html.getEvents(), html.getMonth(), html.getYear());
        try {
            CalendarFactory.printCalendar(calendar);
        } catch (IOException e) {
            return false;
        }
        return true;
    }
}
