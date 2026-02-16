package mynghn.springbatchpatterns.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateUtils {

    public static final DateTimeFormatter FORMATTER_yyyyMMdd = DateTimeFormatter.ofPattern(
            "yyyyMMdd");
}
