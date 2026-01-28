import java.util.Scanner;

public class Swaz {
    public static void main(String[] args) {
        String horizontal = "----------------------------------";
                
        System.out.println(horizontal);
        System.out.println("Hello! I'm Swaz");
        System.out.println("What can I do for you?");
        System.out.println(horizontal);

        Scanner in = new Scanner(System.in);

        while (true) {
            String input = in.nextLine();

            if (input.equals("bye")) {
                System.out.println(horizontal);
                System.out.println("Bye. Hope to see you again soon!");
                System.out.println(horizontal);
                break;
            }
            System.out.println(horizontal);
            System.out.println(input);
            System.out.println(horizontal);
        }
    }
}
