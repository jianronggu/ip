import java.sql.SQLOutput;
import java.util.Scanner;
import java.util.ArrayList;

public class Labussy {

    private static void divide() {
        String Line = "____________________________________________________________";
        System.out.println(Line);
    }

    private static void printList(ArrayList<Task> tasks) {
        divide();
        System.out.println("Here are the tasks in your list: ");
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println(" " + (i + 1) + "." + tasks.get(i));
        }
        divide();
    }

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        ArrayList<Task> tasks = new ArrayList<>();

        divide();
        System.out.println("Hello! I'm Labussy.");
        System.out.println("What can I do for you?");
        divide();

        while (true) {
            String input = scanner.nextLine();
            Task task = new Task(input);

            if (input.equals("bye")) {
                divide();
                System.out.println("Bye. Hope to see you again soon!");
                divide();
                break;
            }

            if (input.equals("list")) {
                printList(tasks);
                continue;
            }

            if (input.toLowerCase().startsWith("mark ")) {
                String[] parts = input.split("\\s+", 2);

                int index = Integer.parseInt(parts[1]) - 1;
                tasks.get(index).markAsDone();
                divide();
                System.out.println("Nice! I've marked this task as done:");
                System.out.println(tasks.get(index));
                divide();
                continue;
            }

            if (input.toLowerCase().startsWith("unmark" )) {
                String[] parts = input.split("\\s+", 2);

                int index = Integer.parseInt(parts[1]) - 1;
                tasks.get(index).markAsUndone();
                divide();
                System.out.println("OK, I've marked this task as not done yet: ");
                System.out.println(tasks.get(index));
                divide();
                continue;
            }
            else {
                tasks.add(task);
                divide();
                System.out.println("added: " + input);
                divide();
            }
        }
    }
}

