package swaz;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * Handles loading tasks from a file and saving tasks to a file.
 * The storage format uses one task per line with '|' as the delimiter.
 */
public class Storage {
    private final Path filePath;

    // constructor
    public Storage(String relativePath) {
        this.filePath = Path.of(relativePath);
    }

    /**
     * Ensures the save file exists by creating parent directories and the file if needed.
     *
     * @throws SwazException if the file cannot be created
     */
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

    /**
     * Loads tasks from the save file into a list of Task objects.
     *
     * @return list of tasks loaded from file
     * @throws SwazException if the file cannot be read or contains corrupted task data
     */
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

    /**
     * Saves the current task list to the save file.
     *
     * @param tasks tasks to be saved
     * @throws SwazException if the file cannot be written
     */
    // save tasks to file after every modification
    public void save(ArrayList<Task> tasks) throws SwazException {
        createFileIfMissing();

        try {
            ArrayList<String> lines = new ArrayList<>();
            for (int i = 0; i < tasks.size(); i++) {
                lines.add(tasks.get(i).toStorageString());
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

