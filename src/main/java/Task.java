public class Task {
    private String description;
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

    public String getStatus() {
        return isDone ? "X" : " ";
    }
    
    public String toString() {
        return "[" + getStatus() + "] " + description;
    }
}

