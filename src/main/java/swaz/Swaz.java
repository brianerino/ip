package swaz;

import java.util.ArrayList;

/**
 * Main entry point of the Swaz task manager application.
 * Coordinates user interaction, command parsing, task list operations, and storage.
 */
public class Swaz {
    private final Storage storage;
    private TaskList tasks;
    private final Ui ui;
    private final Parser parser;

    /**
     * Constructs a Swaz instance using the given file path for persistent storage.
     *
     * @param filePath path to the save file used to load and save tasks
     */
    public Swaz(String filePath) {
        ui = new Ui();
        parser = new Parser();
        storage = new Storage(filePath);
        
        try {
            ArrayList<Task> loaded = storage.load();
            tasks = new TaskList(loaded);
        } catch (SwazException e) {
            ui.printLoadingError(e.getMessage());
            tasks = new TaskList();
        }
    }

    /**
     * Runs the main command loop of the application until the user exits.
     */
    public void run() {
        ui.printWelcome();

        while (true) {
            String input = ui.readCommand();
            try {
                String commandWord = parser.getCommandWord(input);
                switch (commandWord) {
                case "bye":
                    ui.printBye();
                    return;
                case "list":
                    ui.printList(tasks);
                    break;
                case "mark": {
                    int index = parser.parseIndex(input, "mark", tasks.size());
                    tasks.get(index).markDone();
                    storage.save(tasks.asArrayList());
                    ui.printMarked(tasks.get(index));
                    break;
                }
                case "unmark": {
                    int index = parser.parseIndex(input, "unmark", tasks.size());
                    tasks.get(index).markNotDone();
                    storage.save(tasks.asArrayList());
                    ui.printUnmarked(tasks.get(index));
                    break;
                }
                case "delete": {
                    int index = parser.parseIndex(input, "delete", tasks.size());
                    Task removed = tasks.remove(index);
                    storage.save(tasks.asArrayList());
                    ui.printDeleted(removed, tasks.size());
                    break;
                }
                case "todo": {
                    String description = parser.parseTodo(input);
                    Task task = new ToDo(description);
                    tasks.add(task);
                    storage.save(tasks.asArrayList());
                    ui.printAdded(task, tasks.size());
                    break;
                }
                case "deadline": {
                    Task task = parser.parseDeadline(input);
                    tasks.add(task);
                    storage.save(tasks.asArrayList());
                    ui.printAdded(task, tasks.size());
                    break;
                }
                case "event": {
                    Task task = parser.parseEvent(input);
                    tasks.add(task);
                    storage.save(tasks.asArrayList());
                    ui.printAdded(task, tasks.size());
                    break;
                }
                case "find": {
                    String keyword = input.substring("find".length()).trim();
                    if (keyword.isEmpty()) {
                        throw new SwazException("OOPS!!! Find format: find <keyword>");
                    }
                    var matches = tasks.find(keyword);
                    ui.showFindResults(matches);
                    break;
                }
                default:
                    throw new SwazException("OOPS!!! I'm sorry, but I don't know what that means :(");
                }
            } catch (SwazException e) {
                ui.printError(e.getMessage());
            }
        }
    }

    /**
     * Starts the application.
     *
     * @param args command-line arguments (unused)
     */
    public static void main(String[] args) {
        new Swaz("data/swaz.txt").run();
    }
}
