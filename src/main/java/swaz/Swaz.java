package swaz;

import java.util.Scanner;
import java.util.ArrayList;

public class Swaz {
    public static void main(String[] args) {

        Storage storage = new Storage("data/swaz.txt");
        
        // swaz.Task List + Count
        ArrayList<Task> tasks = new ArrayList<>();

        // auto load data
        try {
            ArrayList<Task> loaded = storage.load();
            tasks.addAll(loaded);
        } catch (SwazException e) {
            printError(e.getMessage());
        }
        
        // Opening message
        printLine();
        System.out.println("Hello! I'm Swaz");
        System.out.println("What can I do for you?");
        printLine();

        // Get input from user
        Scanner in = new Scanner(System.in);

        // main logic
        while (true) {
            String input = in.nextLine().trim();
            
            try {
                // bye
                if (input.equals("bye")) {
                    printLine();
                    System.out.println("Bye. Hope to see you again soon!");
                    printLine();
                    break;
                }
                
                // list
                if (input.equals("list")) {
                    printLine();
                    System.out.println("Here are the tasks in your list:");
                    for (int i = 0; i < tasks.size(); i++) {
                        System.out.println((i + 1) + ". " + tasks.get(i));
                    }
                    printLine();
                    continue;
                }
    
                // mark
                if (input.startsWith("mark ")) {
                    int index = parseIndex(input, "mark", tasks.size());
                    tasks.get(index).markDone();
                    storage.save(tasks);
                    printLine();
                    System.out.println("Nice! I've marked this task as done:");
                    System.out.println(tasks.get(index));
                    printLine();
                    continue;
                }
    
                // unmark
                if (input.startsWith("unmark ")) {
                    int index = parseIndex(input, "unmark", tasks.size());
                    tasks.get(index).markNotDone();
                    storage.save(tasks);
                    printLine();
                    System.out.println("OK, I've marked this task as not done yet:");
                    System.out.println(tasks.get(index));
                    printLine();
                    continue;
                }

                // delete
                if (input.startsWith("delete ")) {
                    int index = parseIndex(input, "delete", tasks.size());
                    Task removed = tasks.remove(index);
                    storage.save(tasks);
                    printLine();
                    System.out.println("Noted. I've removed this task:");
                    System.out.println(removed);
                    System.out.println("Now you have " + tasks.size() + " tasks in the list.");
                    printLine();
                    continue;
                }
    
                // todo <description>
                if (input.startsWith("todo ")) {
                    String description = parseTodo(input);
                    Task task = new ToDo(description);
                    tasks.add(task);
                    storage.save(tasks);
                    printAdded(task, tasks.size());
                    continue;
                }
    
                // deadline <description> /by <by>
                if (input.startsWith("deadline ")) {
                    Task task = parseDeadline(input);
                    tasks.add(task);
                    storage.save(tasks);
                    printAdded(task, tasks.size());
                    continue;
                }
    
                // event <description> /from <from> /to <to>
                if (input.startsWith("event ")) {
                    Task task = parseEvent(input);
                    tasks.add(task);
                    storage.save(tasks);
                    printAdded(task, tasks.size());
                    continue;
                }

                // error handling for base actions with no descriptions
                switch (input) {
                case "todo":
                    printError("OOPS!!! Todo format: todo <description>");
                    continue;
                case "deadline":
                    printError("OOPS!!! Deadline format: deadline <description> /by <by>");
                    continue;
                case "event":
                    printError("OOPS!!! Event format: event <description> /from <from> /to <to>");
                    continue;
                case "mark":
                    printError("OOPS!!! Mark format: mark <integer>");
                    continue;
                case "unmark":
                    printError("OOPS!!! Unmark format: unmark <integer>");
                    continue;
                case "delete":
                    printError("OOPS!!! Delete format: delete <integer>");
                    continue;    
                default:
                    throw new SwazException("OOPS!!! I'm sorry, but I don't know what that means :(");
                }
           
            } catch (SwazException e) {
                printError(e.getMessage());
            }
        }
    }
    
    // print horizontal line
    private static void printLine() {
        System.out.println("----------------------------------");
    }

    // print added tasks
    private static void printAdded(Task task, int taskCount) {
        printLine();
        System.out.println("Got it. I've added this task:");
        System.out.println(task);
        System.out.println("Now you have " + taskCount + " tasks in the list.");
        printLine();
    }
    
    // print error message
    private static void printError(String message) {
        printLine();
        System.out.println(message);
        printLine();
    }

    // get mark/unmark index or error message
    private static int parseIndex(String input, String commandWord, int taskCount) throws SwazException {
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
    private static String parseTodo(String input) throws SwazException {
        return input.substring("todo".length()).trim();
    }

    // get swaz.Deadline <description> or error message
    private static Task parseDeadline(String input) throws SwazException {
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
    private static Task parseEvent(String input) throws SwazException {
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
