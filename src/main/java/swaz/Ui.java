package swaz;

import java.util.Scanner;

public class Ui {
    private final Scanner scanner;

    public Ui() {
        scanner = new Scanner(System.in);
    }

    public String readCommand() {
        return scanner.nextLine().trim();
    }

    // print opening message
    public void printWelcome() {
        printLine();
        System.out.println("  ______   __       __   ______   ________ \n" +
                " /      \\ |  \\  _  |  \\ /      \\ |        \\\n" +
                "|  $$$$$$\\| $$ / \\ | $$|  $$$$$$\\ \\$$$$$$$$\n" +
                "| $$___\\$$| $$/  $\\| $$| $$__| $$    /  $$ \n" +
                " \\$$    \\ | $$  $$$\\ $$| $$    $$   /  $$  \n" +
                " _\\$$$$$$\\| $$ $$\\$$\\$$| $$$$$$$$  /  $$   \n" +
                "|  \\__| $$| $$$$  \\$$$$| $$  | $$ /  $$___ \n" +
                " \\$$    $$| $$$    \\$$$| $$  | $$|  $$    \\\n" +
                "  \\$$$$$$  \\$$      \\$$ \\$$   \\$$ \\$$$$$$$$\n" );
        System.out.println("Hello! I'm Swaz");
        System.out.println("What can I do for you?");
        printLine();
    }
    
    // print horizontal line
    public void printLine() {
        System.out.println("----------------------------------");
    }

    // print added tasks
    public void printAdded(Task task, int taskCount) {
        printLine();
        System.out.println("Got it. I've added this task:");
        System.out.println(task);
        System.out.println("Now you have " + taskCount + " tasks in the list.");
        printLine();
    }

    // print error message
    public void printError(String message) {
        printLine();
        System.out.println(message);
        printLine();
    }

    // print closing message
    public void printBye() {
        printLine();
        System.out.println("Sayonara!! Hope to see you again soon!");
        printLine();
    }

    // print list of items in task list
    public void printList(TaskList tasks) {
        printLine();
        System.out.println("Here are the tasks in your list:");
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println((i + 1) + ". " + tasks.get(i));
        }
        printLine();
    }
    
    // mark task as done
    public void printMarked(Task task) {
        printLine();
        System.out.println("Nice! I've marked this task as done:");
        System.out.println(task);
        printLine();
    }

    // unmark tasks
    public void printUnmarked(Task task) {
        printLine();
        System.out.println("OK, I've marked this task as not done yet:");
        System.out.println(task);
        printLine();
    }

    // remove task
    public void printDeleted(Task removed, int taskCount) {
        printLine();
        System.out.println("Noted. I've removed this task:");
        System.out.println(removed);
        System.out.println("Now you have " + taskCount + " tasks in the list.");
        printLine();
    }

    public void printLoadingError(String message) {
        printError(message);
    }
}
