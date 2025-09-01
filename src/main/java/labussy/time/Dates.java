package labussy.time;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.format.DateTimeParseException;

public final class Dates implements Comparable<Dates> {
    private static final DateTimeFormatter OUT = DateTimeFormatter.ofPattern("MMM d yyyy h:mm a");
    private static final DateTimeFormatter[] IN = new DateTimeFormatter[] {
            DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm"),   // 2019-12-01 1800
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"),  // 2019-12-01 18:00
            DateTimeFormatter.ofPattern("MMM d yyyy h:mm a"), // Dec 1 2019 6:00 PM
            DateTimeFormatter.ISO_LOCAL_DATE_TIME,            // 2019-12-01T18:00
            DateTimeFormatter.ISO_LOCAL_DATE                  // 2019-12-01 (→ 00:00)
    };
    private final LocalDateTime value;

    public Dates(String date) {
        this.value = parseFlexible(date);
    }
    public Dates(LocalDateTime date) {
        this.value = date;
    }

    private static LocalDateTime parseFlexible(String s) {
        s = s.trim();
        for (DateTimeFormatter f : IN) {
            try {
                if (f == DateTimeFormatter.ISO_LOCAL_DATE) {
                    return LocalDate.parse(s, f).atStartOfDay();
                }
                return LocalDateTime.parse(s, f);
            } catch (DateTimeParseException ignored) {}
        }
        throw new IllegalArgumentException("Invalid date/time: " + s);
    }

    public long daysUntil() {
        return LocalDateTime.now().until(value, ChronoUnit.DAYS);
    }

    @Override
    public int compareTo(Dates other) {
        if (this.value.isBefore(other.value)) return -1;
        if (this.value.isAfter(other.value)) return 1;
        else return 0;
    }

    @Override
    public String toString() {
        return value.format(OUT);
    }
}
