public class Ui {
    private static final String LINE = "____________________________________________________________";
    private final java.util.Scanner in = new java.util.Scanner(System.in);

    public void showLine() {
        System.out.println(LINE);
    }
    public void showWelcome() {
        showLine();
        System.out.println("Hello! I'm Labussy.");
        System.out.println("What can I do for you?");
        showLine();
    }
    public String readCommand() {
        return in.nextLine().trim();
    }
    public void showBye() {
        showLine();
        System.out.println("Bye. Hope to see you again soon!");
        showLine();
    }

    public void showList(TaskList tasks) {
        showLine();
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println(" " + (i + 1) + "." + tasks.get(i));
        }
        showLine();
    }

    public void showAdded(Task task, int size) {
        showLine();
        System.out.println("Got it. I've added this task: ");
        System.out.println(task);
        System.out.println("Now you have " + size + " tasks in the list.");
        showLine();
    }

    public void showError(String errorMessage) {
        showLine();
        System.out.println(errorMessage);
        showLine();
    }

    public void showMarked(Task task) {
        showLine();
        System.out.println("Nice! I've marked this task as done:");
        System.out.println(task);
        showLine();
    }

    public void showUnmarked(Task task) {
        showLine();
        System.out.println("OK, I've marked this task as not done yet: ");
        System.out.println(task);
        showLine();
    }

    public void showRemoved(Task task, int size) {
        showLine();
        System.out.println("Noted. I've removed this task: ");
        System.out.println(task);
        System.out.println("Now you have " + size + " tasks in the list.");
        showLine();
    }

}
