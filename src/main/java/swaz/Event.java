package swaz;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Represents an Event task with a start and end date/time.
 */
public class Event extends Task {
    private LocalDate from;
    private LocalDate to;

    /**
     * Creates an Event task with the given description, start, and end values.
     *
     * @param description task description
     * @param from start value (e.g., yyyy-MM-dd for Level-8)
     * @param to end value (e.g., yyyy-MM-dd for Level-8)
     */
    public Event(String description, String from, String to) {
        super(description);
        this.from = LocalDate.parse(from);
        this.to = LocalDate.parse(to);
    }

    /**
     * Converts this Event into the storage format.
     *
     * @return storage string representation of this Event
     */
    @Override
    public String toStorageString() {
        String status = isDone() ? "1" : "0";
        return "E" + "|" + status + "|" + getDescription() + "|" + from.toString() + "|" + to.toString();
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd yyyy");
        return "[E]" + super.toString() + " (from: " + from.format(formatter) + " to: " + to.format(formatter) + ")";
    } 
}
