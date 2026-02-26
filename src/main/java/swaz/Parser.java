package swaz;

public class Parser {

    public String getCommandWord(String input) {
        String trimmed = input.trim();
        if (trimmed.isEmpty()) {
            return "";
        }
        return trimmed.split("\\s+", 2)[0];
    }

    // get mark/unmark index or error message
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

        return new Event(description, from, to);
    }
}