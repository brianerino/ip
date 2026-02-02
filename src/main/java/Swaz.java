import java.util.Scanner;

public class Swaz {
    public static void main(String[] args) {
        
        // Task List + Count
        Task[] tasks = new Task[100];
        int taskCount = 0;
        
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
                try {
                    int index = parseIndex(input, "mark");
                    if (index < 0 || index >= taskCount) {
                        throw new IndexOutOfBoundsException();
                    }
                    
                    tasks[index].markDone();
                    printLine();
                    System.out.println("Nice! I've marked this task as done:");
                    System.out.println(tasks[index]);
                    printLine();
                    
                } catch (Exception e) {
                    System.out.println("Invalid task number.");
                }
                continue;
            }

            // unmark
            if (input.startsWith("unmark ")) {
                try {
                    int index = parseIndex(input, "unmark");
                    if (index < 0 || index >= taskCount) {
                        throw new IndexOutOfBoundsException();
                    }

                    tasks[index].markNotDone();
                    printLine();
                    System.out.println("OK, I've marked this task as not done yet:");
                    System.out.println(tasks[index]);
                    printLine();
                    
                } catch (Exception e) {
                    System.out.println("Invalid task number.");
                }
                continue;
            }

            // todo <description>
            if (input.startsWith("todo ")) {
                String description = input.substring("todo ".length()).trim();
                if (description.isEmpty()) {
                    System.out.println("Description cannot be empty.");
                    continue;
                }

                tasks[taskCount] = new ToDo(description);
                taskCount++;
                printAdded(tasks[taskCount - 1], taskCount);
                continue;
            }

            // deadline <description> /by <by>
            if (input.startsWith("deadline ")) {
                try {
                    String rest = input.substring("deadline ".length()).trim();
                    String[] parts = rest.split(" /by ", 2);
                    
                    if (parts.length < 2) {
                        throw new IllegalArgumentException();
                    }

                    String description = parts[0].trim();
                    String by = parts[1].trim();

                    if (description.isEmpty() || by.isEmpty()) {
                        throw new IllegalArgumentException();
                    }

                    tasks[taskCount] = new Deadline(description, by);
                    taskCount++;
                    printAdded(tasks[taskCount - 1], taskCount);
                } catch (Exception e) {
                    System.out.println("Invalid deadline format. Use: deadline <description> /by <day>");
                }
                continue;
            }

            // event <description> /from <from> /to <to>
            if (input.startsWith("event ")) {
                try {
                    String rest = input.substring("event ".length()).trim();
                    String[] firstSplit = rest.split(" /from ", 2);
                    
                    if (firstSplit.length < 2) {
                        throw new IllegalArgumentException();
                    }

                    String description = firstSplit[0].trim();
                    String[] secondSplit = firstSplit[1].split(" /to ", 2);
                    
                    if (secondSplit.length < 2) {
                        throw new IllegalArgumentException();
                    }

                    String from = secondSplit[0].trim();
                    String to = secondSplit[1].trim();

                    if (description.isEmpty() || from.isEmpty() || to.isEmpty()) {
                        throw new IllegalArgumentException();
                    }

                    tasks[taskCount] = new Event(description, from, to);
                    taskCount++;
                    printAdded(tasks[taskCount - 1], taskCount);
                } catch (Exception e) {
                    System.out.println("Invalid event format. Use: event <description> /from <from> /to <to>");
                }
                continue;
            }

            // fallback
            System.out.println("OOPS!!! I'm sorry, but I don't know what that means :(");
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

    // get index for mark/unamrk
    private static int parseIndex(String input, String commandWord) {
        String numberPart = input.substring(commandWord.length()).trim();
        int taskNumber = Integer.parseInt(numberPart);
        return taskNumber - 1; 
    }
    
}
