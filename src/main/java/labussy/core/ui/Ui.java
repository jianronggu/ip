package labussy.core.ui;

import java.util.List;
import java.util.Scanner;

import labussy.core.TaskList;
import labussy.task.Task;
//ChatGPT used
/**
 * UI helper for both CLI and GUI.
 * - CLI methods: showWelcome(), readCommand(), showList(), etc. (they PRINT)
 * - GUI methods: msgWelcome(), msgList(...), msgError(...), etc. (they RETURN strings)
 *
 * Keep all user-facing text in the msg* methods so both frontends stay consistent.
 */
public class Ui {

    // ======= CLI I/O =======

    private final Scanner sc = new Scanner(System.in);

    /** Read one line from stdin (CLI). */
    public String readCommand() {
        return sc.hasNextLine() ? sc.nextLine() : "";
    }

    /** Print a string (internal helper). */
    private void show(String s) {
        System.out.println(s);
    }

    /** CLI: show welcome banner. */
    public void showWelcome() {
        show(msgWelcome());
    }

    /** CLI: show goodbye message. */
    public void showBye() {
        show(msgBye());
    }

    /** CLI: show full list or 'empty'. */
    public void showList(TaskList tasks) {
        if (tasks == null || tasks.size() == 0) {
            show(msgEmptyList());
        } else {
            show(msgList(tasks));
        }
    }

    /** CLI: optionally show "due soon" section if any. */
    public void showDueSoon(TaskList tasks) {
        String s = msgDueSoon(tasks);
        if (!s.isBlank()) {
            show(s);
        }
    }

    /** CLI: wrappers that reuse the same wording as GUI. */
    public void showAdded(Task t, int newCount) { show(msgAdded(t, newCount)); }
    public void showDeleted(Task t, int newCount) { show(msgDeleted(t, newCount)); }
    public void showMarked(Task t) { show(msgMarked(t)); }
    public void showUnmarked(Task t) { show(msgUnmarked(t)); }
    public void showFind(List<Task> matches) { show(msgFind(matches)); }
    public void showError(String details) { show(msgError(details)); }
    public void showInvalidIndex() { show(msgInvalidIndex()); }

    // ======= GUI-friendly formatters (no printing) =======

    /**
     * Returns Welcome message.
     */

    public String msgWelcome() {
        return "BEW BEW! I'm Labussy\nWhat can I do for you?";
    }

    /**
     * Returns Bye message.
     */
    public String msgBye() {
        return "BEW BEW. Hope to see you again soon!";
    }

    /**
     * Returns Empty List message.
     */

    public String msgEmptyList() {
        return "Your list is empty.";
    }

    /**
     * Returns List of tasks.
     * @param tasks
     */

    public String msgList(TaskList tasks) {
        StringBuilder sb = new StringBuilder("Here are the tasks in your list:\n");
        for (int i = 0; i < tasks.size(); i++) {
            sb.append(" ").append(i + 1).append(". ").append(tasks.get(i)).append("\n");
        }
        return sb.toString().trim();
    }

    /**
     * Optional "due soon" section.
     * Tries to call a boolean checker on Deadline-like tasks via reflection to avoid hard coupling:
     *  - dueSoon()
     *  - isDueSoon()
     * If neither exists, it silently skips.
     * @param tasks
     */

    public String msgDueSoon(TaskList tasks) {
        StringBuilder sb = new StringBuilder();
        boolean any = false;

        for (int i = 0; i < tasks.size(); i++) {
            Task t = tasks.get(i);
            boolean isDueSoon = false;

            try {
                // Try method name: dueSoon()
                java.lang.reflect.Method m;
                try {
                    m = t.getClass().getMethod("dueSoon");
                } catch (NoSuchMethodException e1) {
                    // Fallback: isDueSoon()
                    try {
                        m = t.getClass().getMethod("isDueSoon");
                    } catch (NoSuchMethodException e2) {
                        m = null;
                    }
                }
                if (m != null) {
                    Object res = m.invoke(t);
                    if (res instanceof Boolean) {
                        isDueSoon = (Boolean) res;
                    }
                }
            } catch (ReflectiveOperationException ignored) {
                // If checker is absent or throws, ignore gracefully.
            }

            if (isDueSoon) {
                if (!any) {
                    sb.append("\nThese tasks are due soon (less than 1 day):\n");
                    any = true;
                }
                sb.append(" ").append(i + 1).append(". ").append(t).append("\n");
            }
        }

        return sb.toString().trim();
    }

    /**
     * Returns Add message.
     * @param t
     * @param newCount
     */

    public String msgAdded(Task t, int newCount) {
        return "Got it. I've added this task:\n  " + t
                + "\nNow you have " + newCount + " tasks in the list.";
    }

    /**
     * Returns Delete message.
     * @param removed
     * @param  newCount
     */

    public String msgDeleted(Task removed, int newCount) {
        return "Noted. I've removed this task:\n  " + removed
                + "\nNow you have " + newCount + " tasks in the list.";
    }

    /**
     * Returns marked message.
     * @param t
     */

    public String msgMarked(Task t) {
        return "BEW BEW! I've marked this task as done:\n  " + t;
    }

    /**
     * Returns unmarked message.
     * @param t
     */

    public String msgUnmarked(Task t) {
        return "BEW BEW, I've marked this task as not done yet:\n  " + t;
    }

    /**
     * Returns a list of found tasks.
     * @param matches
     */

    public String msgFind(List<Task> matches) {
        if (matches == null || matches.isEmpty()) {
            return "No matching tasks.";
        }
        StringBuilder sb = new StringBuilder("Here are the matching tasks in your list:\n");
        for (int i = 0; i < matches.size(); i++) {
            sb.append(" ").append(i + 1).append(". ").append(matches.get(i)).append("\n");
        }
        return sb.toString().trim();
    }

    /**
     * Returns Error message.
     * @param details
     */

    public String msgError(String details) {
        String message = (details == null || details.isBlank())
                ? "Something went wrong."
                : details.trim();
        return "☹ BEW BEW!!! " + message;
    }

    /**
     * Returns InvalidIndex message
     */

    public String msgInvalidIndex() {
        return msgError("Invalid task number");
    }
}
