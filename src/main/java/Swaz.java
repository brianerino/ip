import java.util.Scanner;

public class Swaz {
    public static void main(String[] args) {
        
        // Chatbot style
        String horizontal = "----------------------------------";
        
        // Task List + Count
        String[] tasks = new String[100];
        int taskCount = 0;
        
        // Opening message
        System.out.println(horizontal);
        System.out.println("Hello! I'm Swaz");
        System.out.println("What can I do for you?");
        System.out.println(horizontal);

        // Get input from user
        Scanner in = new Scanner(System.in);

        // add-to-list, bye, and list logic
        while (true) {
            String input = in.nextLine();

            if (input.equals("bye")) {
                System.out.println(horizontal);
                System.out.println("Bye. Hope to see you again soon!");
                System.out.println(horizontal);
                break;
            }
            
            if (input.equals("list")) {
                System.out.println(horizontal);
                for (int i = 0; i < taskCount; i++) {
                    System.out.println((i + 1) + ". " + tasks[i]);
                }
                System.out.println(horizontal);
                continue;
            }
            
            tasks[taskCount] = input;
            taskCount++;
            
            System.out.println(horizontal);
            System.out.println("added: " + input);
            System.out.println(horizontal);
        }
    }
}
