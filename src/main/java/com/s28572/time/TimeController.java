package com.s28572.time;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.*;
import java.time.format.DateTimeFormatter;

@Controller
public class TimeController {

    @GetMapping("/current-time")
    @ResponseBody
    public String getCurrentTime(
            @RequestParam(required = false, defaultValue = "UTC")
            String zone,
            @RequestParam(required = false, defaultValue = "HH:mm:ss.SSSS yyyy/MM/dd")
            String format) {
        System.out.println(zone + " " + format);

        ZonedDateTime now;
        try {
            now = ZonedDateTime.now(ZoneId.of(zone));
        } catch (DateTimeException e) {
            System.out.println(e.getMessage());
            zone = "your device time zone";
            now = ZonedDateTime.now();
        }

        DateTimeFormatter formatter;
        try {
            formatter = DateTimeFormatter.ofPattern(format);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            format = "HH:mm:ss.SSSS yyyy/MM/dd";
            formatter = DateTimeFormatter.ofPattern(format);
        }

        String formatDateTime = now.format(formatter);
        System.out.println(formatDateTime);
        return String.format("Current time in %s in format %s is %s", zone, format, formatDateTime);
    }

    @GetMapping("/current-year")
    @ResponseBody
    public String getCurrentYear() {
        return "It's " + LocalDate.now().getYear();
    }
}
