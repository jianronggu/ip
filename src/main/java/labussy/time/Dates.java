package labussy.time;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.format.DateTimeParseException;

/**
 * Small value type that parses flexible date/time inputs and formats them for display. Comparable by instant.
 */

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

    /**
     * Constructs a new Dates.
     * @param date parameter
     */

    public Dates(String date) {
        this.value = parseFlexible(date);
    }

    /**
     * Constructs a new Dates.
     * @param date parameter
     */

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

    /**
     * Days until the date that the task is due/
     * @return result
     */

    public long daysUntil() {
        return LocalDateTime.now().until(value, ChronoUnit.DAYS);
    }

    /**
     * Compares this instance to another by instant.
     * @param other parameter
     * @return result
     */

    @Override
    public int compareTo(Dates other) {
        if (this.value.isBefore(other.value)) return -1;
        if (this.value.isAfter(other.value)) return 1;
        else return 0;
    }

    /**
     * Returns a human-readable representation for lists and storage echoes.
     * @return result
     */

    @Override
    public String toString() {
        return value.format(OUT);
    }
}
