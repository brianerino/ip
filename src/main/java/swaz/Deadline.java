package swaz;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Represents a Deadline task that must be completed by a given date/time.
 */
public class Deadline extends Task {
    private LocalDate by;

    /**
     * Creates a Deadline task with the given description and deadline value.
     *
     * @param description task description
     * @param by deadline value (e.g., yyyy-MM-dd for Level-8)
     */
    public Deadline(String description, String by) {
        super(description);
        // parses yyyy-mm-dd
        this.by = LocalDate.parse(by);
    }

    /**
     * Converts this Deadline into the storage format.
     *
     * @return storage string representation of this Deadline
     */
    @Override
    public String toStorageString() {
        String status = isDone() ? "1" : "0";
        return "D" + "|" + status + "|" + getDescription() + "|" + by.toString();
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd yyyy");
        return "[D]" + super.toString() + " (by: " + by.format(formatter) + ")";
    }
}
