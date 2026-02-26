package swaz;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Deadline extends Task {
    private LocalDate by;

    // constructor 
    public Deadline(String description, String by) {
        super(description);
        // parses yyyy-mm-dd
        this.by = LocalDate.parse(by);
    }

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
