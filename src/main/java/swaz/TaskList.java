package swaz;

import java.util.ArrayList;

public class TaskList {
    private final ArrayList<Task> tasks;

    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    public TaskList(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }
    
    // return task list size
    public int size() {
        return tasks.size();
    }

    public Task get(int index) {
        return tasks.get(index);
    }

    // add a task
    public void add(Task task) {
        tasks.add(task);
    }

    // remove a task
    public Task remove(int index) {
        return tasks.remove(index);
    }

    public ArrayList<Task> asArrayList() {
        return tasks;
    }

    // find <keyword>
    public ArrayList<Task> find(String keyword) {
        ArrayList<Task> matches = new ArrayList<>();
        for (Task task : tasks) {
            if (task.getDescription().contains(keyword)) {
                matches.add(task);
            }
        }
        return matches;
    }
}
