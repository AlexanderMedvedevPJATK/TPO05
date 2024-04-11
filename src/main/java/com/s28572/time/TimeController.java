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
            @RequestParam(required = false, defaultValue = "")
            String zone,
            @RequestParam(required = false, defaultValue = "HH:mm:ss.SSSS yyyy/MM/dd")
            String format) {

        if (zone.isEmpty()) {
            zone = ZoneId.systemDefault().getId();
        }

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
        return returnHTMLDocumentWithData(String.format(
                "Current time in <br><span>%s</span><br> in format <br><span>%s</span><br> is<br> <span>%s</span>",
                zone, format, formatDateTime));
    }

    @GetMapping("/current-year")
    @ResponseBody
    public String getCurrentYear() {
        return returnHTMLDocumentWithData("It's year <span>" + LocalDate.now().getYear() + "</span> now!");
    }

    public String returnHTMLDocumentWithData(String data) {
        return String.format("""
                 <!DOCTYPE html>
                 <html lang="en">
                 <head>
                   <meta charset="UTF-8">
                   <meta name="viewport" content="width=device-width, initial-scale=1.0">
                   <title>Styled Text</title>
                   <link rel="stylesheet" href="styles/style.css">
                 </head>
                 <body>
                   <div class="container">
                     <div class="styled-text">
                       <p>
                            %s
                       </p>
                     </div>
                   </div>
                 </body>
                 </html>
                """, data);
    }
}
