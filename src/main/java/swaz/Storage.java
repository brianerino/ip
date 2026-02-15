package swaz;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Storage {
    private final Path filePath;

    // constructor
    public Storage(String relativePath) {
        this.filePath = Path.of(relativePath);
    }

    // check if file exists
    // file does not exist, create parent folders
    // creates data/swaz.txt 
    public void createFileIfMissing() throws SwazException {
        try {
            if (!Files.exists(filePath)) {
                Path parent = filePath.getParent();
                if (parent != null && !Files.exists(parent)) {
                    Files.createDirectories(parent);
                }
                Files.createFile(filePath);
            }
        } catch (IOException e) {
            throw new SwazException("OOPS!!! I had trouble creating the save file.");
        }
    }

    // load task from file when swaz starts up
    public ArrayList<Task> load() throws SwazException {
        createFileIfMissing();
        
        try {
            // read lines from file and converts them into a list of strings
            List<String> lines = Files.readAllLines(filePath);
            ArrayList<Task> tasks = new ArrayList<>();
            for (String line : lines) {
                if (line.trim().isEmpty()) {
                    continue;
                }
                tasks.add(parseTask(line));
            }
            return tasks;

        } catch (IOException e) {
            throw new SwazException("OOPS!!! I had trouble loading your saved tasks.");
        }
    }

    // save tasks to file after every modification
    public void save(Task[] tasks, int taskCount) throws SwazException {
        createFileIfMissing();

        try {
            ArrayList<String> lines = new ArrayList<>();
            for (int i = 0; i < taskCount; i++) {
                lines.add(tasks[i].toStorageString());
            }
            Files.write(filePath, lines);

        } catch (IOException e) {
            throw new SwazException("OOPS!!! I had trouble saving your tasks.");
        }
    }

    // converts a line from the file to a task object
    private Task parseTask(String line) throws SwazException {
        String[] parts = line.split("\\|", -1);

        // must have at least 3 parts
        if (parts.length < 3) {
            throw new SwazException("OOPS!!! Your save file is corrupted.");
        }

        String type = parts[0];
        boolean isDone = parts[1].equals("1");
        String description = parts[2];

        switch (type) {
        case "T":
            ToDo todo = new ToDo(description);
            if (isDone) {
                todo.markDone();
            }
            return todo;
        case "D":
            if (parts.length < 4) {
                throw new SwazException("OOPS!!! Your save file is corrupted.");
            }
            Deadline deadline = new Deadline(description, parts[3]);
            if (isDone) {
                deadline.markDone();
            }
            return deadline;
        case "E":
            if (parts.length < 5) {
                throw new SwazException("OOPS!!! Your save file is corrupted.");
            }
            Event event = new Event(description, parts[3], parts[4]);
            if (isDone) {
                event.markDone();
            }
            return event;
        default:
            throw new SwazException("OOPS!!! Your save file is corrupted.");
        }
    }
}

