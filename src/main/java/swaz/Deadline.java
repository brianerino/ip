package swaz;

public class Deadline extends Task {
    private final String by;

    // constructor 
    public Deadline(String description, String by) {
        super(description);
        this.by = by;
    }

    @Override
    public String toStorageString() {
        String status = isDone() ? "1" : "0";
        return "D" + "|" + status + "|" + getDescription() + "|" + by;
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + by + ")";
    }
}
