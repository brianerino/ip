package swaz;

/**
 * Represents a generic task with a description and completion status.
 * Subclasses (ToDo/Deadline/Event) may extend this with additional fields.
 */
public class Task {
    private final String description;
    private boolean isDone;

    // constructor
    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    /**
     * Marks this task as completed.
     */
    public void markDone() {
        isDone = true;
    }

    /**
     * Marks this task as not completed.
     */
    public void markNotDone() {
        isDone = false;
    }

    /**
     * Returns the status icon used in the UI output.
     *
     * @return "X" if done, otherwise a blank space
     */
    public String getStatusIcon() {
        return isDone ? "X" : " ";
    }

    /**
     * Returns whether this task is marked as done.
     *
     * @return true if done, false otherwise
     */
    public boolean isDone() {
        return isDone;
    }

    protected String getDescription() {
        return description;
    }

    /**
     * Converts this task into the text format used for saving to file.
     * Subclasses should override this to provide their own task type and fields.
     *
     * @return storage string representation of this task
     */
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

