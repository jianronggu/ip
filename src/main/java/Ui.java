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

}
