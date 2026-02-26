package swaz;

/**
 * Represents a ToDo task which contains only a description.
 */
public class ToDo extends Task {

    /**
     * Creates a ToDo task with the given description.
     *
     * @param description task description
     */
    public ToDo(String description) {
        super(description);
    }

    /**
     * Converts this ToDo into the storage format.
     *
     * @return storage string representation of this ToDo
     */
    @Override
    public String toStorageString() {
        String status = isDone() ? "1" : "0";
        return "T" + "|" + status + "|" + getDescription();
    }

    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}
