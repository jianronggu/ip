import java.sql.SQLOutput;
import java.util.Scanner;

public class Labussy {


    private static void divide() {
        String Line = "____________________________________________________________";
        System.out.println(Line);
    }
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        divide();
        System.out.println("Hello! I'm Labussy.");
        System.out.println("What can I do for you?");
        divide();

        while (true) {
            String word = scanner.nextLine();
            if (word.equals("bye")) {
                divide();
                System.out.println("Bye. Hope to see you again soon!");
                divide();
                break;
            }
                divide();
                System.out.println(word);
                divide();
            }
        }
}

