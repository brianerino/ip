import java.util.Scanner;

public class Swaz {
    public static void main(String[] args) {
        
        // Chatbot style
        String horizontal = "----------------------------------";
        
        // Task List + Count
        Task[] tasks = new Task[100];
        int taskCount = 0;
        
        // Opening message
        System.out.println(horizontal);
        System.out.println("Hello! I'm Swaz");
        System.out.println("What can I do for you?");
        System.out.println(horizontal);

        // Get input from user
        Scanner in = new Scanner(System.in);

        // Main logic
        while (true) {
            String input = in.nextLine();

            // bye
            if (input.equals("bye")) {
                System.out.println(horizontal);
                System.out.println("Bye. Hope to see you again soon!");
                System.out.println(horizontal);
                break;
            }
            
            // list
            if (input.equals("list")) {
                System.out.println(horizontal);
                System.out.println("Here are the tasks in your list:");
                for (int i = 0; i < taskCount; i++) {
                    System.out.println((i + 1) + ". " + tasks[i]);
                }
                System.out.println(horizontal);
                continue;
            }

            // mark
            if (input.startsWith("mark ")) {
                try {
                    // split input into an array, access index 1 (the int)
                    // convert string (at index 1 of the array) into an int
                    int taskNumber = Integer.parseInt(input.split(" ")[1]);
                    int index = taskNumber - 1;
                    
                    tasks[index].markDone();

                    System.out.println(horizontal);
                    System.out.println("Nice! I've marked this task as done:");
                    System.out.println(tasks[index]);
                    System.out.println(horizontal);
                } catch (Exception e) {
                    System.out.println("Invalid task number.");
                }
                continue;
            }

            // unmark
            if (input.startsWith("unmark ")) {
                try {
                    // split input into an array, access index 1 (the int)
                    // convert string (at index 1 of the array) into an int
                    int taskNumber = Integer.parseInt(input.split(" ")[1]);
                    int index = taskNumber - 1;

                    tasks[index].markNotDone();
                    
                    System.out.println(horizontal);
                    System.out.println("OK, I've marked this task as not done yet:");
                    System.out.println(tasks[index]);
                    System.out.println(horizontal);
                } catch (Exception e) {
                    System.out.println("Invalid task number.");
                }
                continue;
            }

            // add task
            tasks[taskCount] = new Task(input);
            taskCount++;
            System.out.println(horizontal);
            System.out.println("added: " + input);
            System.out.println(horizontal);
        }
    }
}
