package swaz;

public class ToDo extends Task {
    
    // constructor 
    public ToDo(String description) {
        super(description);
    }

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
