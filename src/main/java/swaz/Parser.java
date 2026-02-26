package swaz;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

/**
 * Parses user input into command words and extracts arguments needed to create tasks or perform actions.
 */
public class Parser {

    /**
     * Extracts the first word (command word) from the user input.
     *
     * @param input full user input
     * @return command word, or empty string if input is blank
     */
    public String getCommandWord(String input) {
        String trimmed = input.trim();
        if (trimmed.isEmpty()) {
            return "";
        }
        return trimmed.split("\\s+", 2)[0];
    }

    /**
     * Parses a 1-based task number from the input and converts it to a 0-based index.
     *
     * @param input full user input
     * @param commandWord command keyword (e.g., "mark", "delete")
     * @param taskCount current number of tasks
     * @return zero-based index into the task list
     * @throws SwazException if the index is missing, not an integer, or out of range
     */
    public int parseIndex(String input, String commandWord, int taskCount) throws SwazException {
        String numberPart = input.substring(commandWord.length()).trim();

        // <task number> is not an integer
        int taskNumber;
        try {
            taskNumber = Integer.parseInt(numberPart);
        } catch (NumberFormatException e) {
            throw new SwazException("OOPS!!! Task number must be an integer. Example: " + commandWord + " 2");
        }

        // <task number> does not exist
        int index = taskNumber - 1;
        if (index < 0 || index >= taskCount) {
            throw new SwazException("OOPS!!! That task number does not exist.");
        }

        return index;
    }

    // get Todo <description> or error message
    public String parseTodo(String input) throws SwazException {
        String desc = input.substring("todo".length()).trim();
        if (desc.isEmpty()) {
            throw new SwazException("OOPS!!! Todo format: todo <description>");
        }
        return desc;
    }

    // get swaz.Deadline <description> or error message
    public Task parseDeadline(String input) throws SwazException {
        String rest = input.substring("deadline".length()).trim();

        if (!rest.contains(" /by ")) {
            throw new SwazException("OOPS!!! Deadline format: deadline <description> /by <by>");
        }

        String[] parts = rest.split(" /by ", 2);
        String description = parts[0].trim();
        String by = parts[1].trim();

        if (description.isEmpty() || by.isEmpty()) {
            throw new SwazException("OOPS!!! Deadline format: deadline <description> /by <by>");
        }

        try {
            LocalDate.parse(by);
        } catch (DateTimeParseException e) {
            throw new SwazException("OOPS!!! Date must be in yyyy-mm-dd format.");
        }

        return new Deadline(description, by);
    }

    // get swaz.Event <description> or error message
    public Task parseEvent(String input) throws SwazException {
        String rest = input.substring("event".length()).trim();

        if (!rest.contains(" /from ") || !rest.contains(" /to ")) {
            throw new SwazException("OOPS!!! Event format: event <description> /from <from> /to <to>");
        }

        String[] firstSplit = rest.split(" /from ", 2);
        String description = firstSplit[0].trim();
        String afterFrom = firstSplit[1].trim();

        String[] secondSplit = afterFrom.split(" /to ", 2);
        String from = secondSplit[0].trim();
        String to = secondSplit[1].trim();

        if (description.isEmpty() || from.isEmpty() || to.isEmpty()) {
            throw new SwazException("OOPS!!! Event format: event <description> /from <from> /to <to>");
        }

        try {
            LocalDate.parse(from);
            LocalDate.parse(to);
        } catch (DateTimeParseException e) {
            throw new SwazException("OOPS!!! Dates must be in yyyy-mm-dd format.");
        }

        return new Event(description, from, to);
    }
}