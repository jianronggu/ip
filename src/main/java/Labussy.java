import javax.lang.model.type.NullType;
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

            if (input.toLowerCase().startsWith("delete ")) {
                String[] parts = input.split("\\s+", 2);
                int index = Integer.parseInt(parts[1]) - 1;
                if (index + 1 > tasks.size() || index < 0) {
                    System.out.println("Invalid. Please refer to the correct task numbers");
                    continue;
                }
                Task task = tasks.get(index);
                tasks.remove(index);
                divide();
                System.out.println("Noted. I've removed this task: ");
                System.out.println(task);
                System.out.println("Now you have " + tasks.size() + " tasks in the list.");
                divide();
                continue;
            }

            if (input.toLowerCase().startsWith("mark ")) {
                String[] parts = input.split("\\s+", 2);

                int index = Integer.parseInt(parts[1]) - 1;
                if (index + 1 > tasks.size() || index < 0) {
                    System.out.println("Invalid. Please refer to the correct task numbers");
                    continue;
                }
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
                if (index + 1 > tasks.size() || index < 0) {
                    System.out.println("Invalid. Please refer to the correct task numbers");
                    continue;
                }
                tasks.get(index).markAsUndone();
                divide();
                System.out.println("OK, I've marked this task as not done yet: ");
                System.out.println(tasks.get(index));
                divide();
                continue;
            }
            else {
                try {
                    if (input.toLowerCase().startsWith("todo ")) {
                        try {
                            int firstSpaceIndex = input.indexOf(" ");
                            String description = input.substring(firstSpaceIndex + 1);
                            if (description.isEmpty()) throw new BlankException();

                            ToDo todo = new ToDo(description);
                            tasks.add(todo);
                            divide();
                            System.out.println("Got it. I've added this task: ");
                            System.out.println(todo);
                            System.out.println("Now you have " + tasks.size() + " tasks in the list.");
                            divide();
                        } catch (BlankException e) {
                            divide();
                            System.out.println(e.getMessage());
                            divide();
                        }
                        continue;
                    }

                    if (input.toLowerCase().startsWith("deadline ")) {
                        try {
                            int firstSpaceIndex = input.indexOf(" ");
                            int bySpaceIndex = input.indexOf("/by");
                            if (bySpaceIndex == -1) throw new MissingComponentException();
                            String description = input.substring(firstSpaceIndex + 1, bySpaceIndex);
                            if (description.isEmpty()) throw new BlankException();
                            String by = input.substring(bySpaceIndex + 4);
                            Deadline deadline = new Deadline(description, by);
                            tasks.add(deadline);
                            divide();
                            System.out.println("Got it. I've added this task: ");
                            System.out.println(deadline);
                            System.out.println("Now you have " + tasks.size() + " tasks in the list.");
                            divide();
                        } catch (BlankException e) {
                            divide();
                            System.out.println(e.getMessage());
                            divide();
                        } catch (MissingComponentException e) {
                            divide();
                            System.out.println(e.getMessage());
                            divide();
                        }
                        continue;
                    }

                    if (input.toLowerCase().startsWith("event ")) {
                        try {
                            int firstSpaceIndex = input.indexOf(" ");
                            int fromSpaceIndex = input.indexOf("/from");
                            if (fromSpaceIndex == -1) throw new MissingComponentException();
                            int toSpaceIndex = input.indexOf("/to");
                            if (toSpaceIndex == -1) throw new MissingComponentException();
                            String description = input.substring(firstSpaceIndex + 1, fromSpaceIndex);
                            if (description.isEmpty()) throw new BlankException();
                            String from = input.substring(fromSpaceIndex + 6, toSpaceIndex);
                            String to = input.substring(toSpaceIndex + 4);

                            Event event = new Event(description, from, to);
                            tasks.add(event);
                            divide();
                            System.out.println("Got it. I've added this task: ");
                            System.out.println(event);
                            System.out.println("Now you have " + tasks.size() + " tasks in the list.");
                            divide();
                        } catch (BlankException e) {
                            divide();
                            System.out.println(e.getMessage());
                            divide();
                        } catch (MissingComponentException e) {
                            divide();
                            System.out.println(e.getMessage());
                            divide();
                        }

                    }
                    throw new IdentifierException();
                } catch (IdentifierException e) {
                    divide();
                    System.out.println(e.getMessage());
                    divide();
                }
            }
        }
    }
}

