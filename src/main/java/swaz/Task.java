package swaz;

public class Task {
    private final String description;
    private boolean isDone;

    // constructor
    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    public void markDone() {
        isDone = true;
    }

    public void markNotDone() {
        isDone = false;
    }

    public String getStatusIcon() {
        return isDone ? "X" : " ";
    }

    public boolean isDone() {
        return isDone;
    }

    protected String getDescription() {
        return description;
    }

    public String toStorageString() {
        String status = isDone ? "1" : "0";
        String type = "?";
        return type + "|" + status + "|" + description;
    }

    @Override
    public String toString() {
        return "[" + getStatusIcon() + "] " + description;
    }
}

