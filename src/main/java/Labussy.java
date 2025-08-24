import java.sql.SQLOutput;
import java.util.Scanner;
import java.util.ArrayList;

public class Labussy {

    private static void divide() {
        String Line = "____________________________________________________________";
        System.out.println(Line);
    }
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        ArrayList<String> storage = new ArrayList<>();

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
            if (word.equals("list")) {
                divide();
                 for (int i = 0; i < storage.size(); i++) {
                    System.out.println(i + "." + storage.get(i));
                    break;
                }
                divide();
            }
            storage.add(word);
            divide();
            System.out.println("added: " + word);
            divide();
            }
        }
}

