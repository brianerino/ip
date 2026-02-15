package swaz;

import java.util.ArrayList;
import java.util.Scanner;

public class Swaz {
    public static void main(String[] args) {

        Storage storage = new Storage("data/swaz.txt");
        
        // swaz.Task List + Count
        Task[] tasks = new Task[100];
        int taskCount = 0;

        // auto load data
        try {
            ArrayList<Task> loaded = storage.load();
            for (Task task : loaded) {
                tasks[taskCount++] = task;
            }
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
                    for (int i = 0; i < taskCount; i++) {
                        System.out.println((i + 1) + ". " + tasks[i]);
                    }
                    printLine();
                    continue;
                }
    
                // mark
                if (input.startsWith("mark ")) {
                    int index = parseIndex(input, "mark", taskCount);
                    tasks[index].markDone();
                    storage.save(tasks, taskCount);
                    printLine();
                    System.out.println("Nice! I've marked this task as done:");
                    System.out.println(tasks[index]);
                    printLine();
                    continue;
                }
    
                // unmark
                if (input.startsWith("unmark ")) {
                    int index = parseIndex(input, "unmark", taskCount);
                    tasks[index].markNotDone();
                    storage.save(tasks, taskCount);
                    printLine();
                    System.out.println("OK, I've marked this task as not done yet:");
                    System.out.println(tasks[index]);
                    printLine();
                    continue;
                }
    
                // todo <description>
                if (input.startsWith("todo ")) {
                    String description = parseTodo(input);
                    tasks[taskCount] = new ToDo(description);
                    taskCount++;
                    storage.save(tasks, taskCount);
                    printAdded(tasks[taskCount - 1], taskCount);
                    continue;
                }
    
                // deadline <description> /by <by>
                if (input.startsWith("deadline ")) {
                    Task task = parseDeadline(input);
                    tasks[taskCount++] = task;
                    storage.save(tasks, taskCount);
                    printAdded(task, taskCount);
                    continue;
                }
    
                // event <description> /from <from> /to <to>
                if (input.startsWith("event ")) {
                    Task task = parseEvent(input);
                    tasks[taskCount++] = task;
                    storage.save(tasks, taskCount);
                    printAdded(task, taskCount);
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

        // mark/unmark <task number> not given
        if (numberPart.isEmpty()) {
            throw new SwazException("OOPS!!! You must provide a task number. Example: " + commandWord + " 2");
        }

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
