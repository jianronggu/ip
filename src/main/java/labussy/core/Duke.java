package labussy.core;

import labussy.core.Parser;
import labussy.core.Storage;
import labussy.core.TaskList;
import labussy.exception.BlankException;
import labussy.exception.MissingComponentException;
import labussy.task.Deadline;
import labussy.task.Event;
import labussy.task.Task;
import labussy.task.ToDo;
import labussy.time.Dates;

import java.lang.reflect.Array;
import java.util.ArrayList;

/** GUI-facing facade that reuses the Labussy core (Parser/TaskList/Storage) but returns text replies. */
public class Duke {
    private final Storage storage = new Storage();
    private final TaskList tasks = new TaskList(storage.load());
    private boolean exit = false;

    /** Exposed in case the GUI wants to know if it should close. */
    public boolean shouldExit() {
        return exit;
    }
    /** Varargs for one or more todos */
    private String addTodos(String... descriptions) {
        ArrayList<String> items = new ArrayList<>();
        for (String d : descriptions) {
            if (d != null) {
                String t = d.trim();
                if (!t.isEmpty()) items.add(t);
            }
        }
        if (items.isEmpty()) {
            return "The description of a todo cannot be empty.";
        }

        int initSize = tasks.size() - 1;
        int counter = 0;

        for (String desc : items) {
            ToDo t = new ToDo(desc);
            tasks.add(t);
            counter++;
        }

        storage.save(tasks.all());

        // build message (single-item keeps old wording; multi-item shows a list)
        if (counter == 1) {
            return "Got it. I've added this task:\n  " + tasks.get(initSize + counter)
                    + "\nNow you have " + tasks.size() + " tasks in the list.";
        } else {
            StringBuilder sb = new StringBuilder("Got it. I've added these tasks:\n");
            for (int i = 0; i < counter; i++) {
                sb.append("  ").append(i + 1).append(". ").append(tasks.get(initSize + i + 1)).append("\n");
            }
            sb.append("Now you have ").append(tasks.size()).append(" tasks in the list.");
            return sb.toString();
        }
    }

    /** Process one user input and return the reply text (no printing here). */
    public String getResponse(String input) {
        String s = (input == null) ? "" : input.trim();

        switch (Parser.kind(s)) {
            case BYE:
                exit = true;
                return "Bye. Hope to see you again soon!";

            case LIST: {
                if (tasks.size() == 0) return "Your list is empty.";
                StringBuilder sb = new StringBuilder("Here are the tasks in your list:\n");
                for (int i = 0; i < tasks.size(); i++) {
                    sb.append(" ").append(i + 1).append(".").append(tasks.get(i)).append("\n");
                }
                return sb.toString().trim();
            }

            case TODO: {
                // Support both: "todo read book"  OR  "todo homework, movies, dates"
                String payload = s.substring(5).trim(); // after "todo "
                if (payload.isEmpty()) {
                    return "☹ OOPS!!! The description of a todo cannot be empty.";
                }
                // Split on commas if present; otherwise treat as a single description
                String[] parts = payload.contains(",")
                        ? payload.split("\\s*,\\s*")   // split by commas, trimming spaces
                        : new String[]{ payload };

                return addTodos(parts); // <-- Java varargs in action
            }

            case DEADLINE:
                try {
                    String[] p = Parser.deadlineParts(s); // [desc, by]
                    Deadline d = new Deadline(p[0], new Dates(p[1]));
                    tasks.add(d);
                    storage.save(tasks.all());
                    return "Got it. I've added this task:\n  " + d
                            + "\nNow you have " + tasks.size() + " tasks in the list.";
                } catch (MissingComponentException | BlankException e) {
                    return e.getMessage();
                }

            case EVENT:
                try {
                    String[] p = Parser.eventParts(s); // [desc, from, to]
                    Event ev = new Event(p[0], new Dates(p[1]), new Dates(p[2]));
                    tasks.add(ev);
                    storage.save(tasks.all());
                    return "Got it. I've added this task:\n  " + ev
                            + "\nNow you have " + tasks.size() + " tasks in the list.";
                } catch (MissingComponentException | BlankException e) {
                    return e.getMessage();
                }

            case MARK: {
                int idx = Parser.index1(s, "mark ") - 1;
                if (idx < 0 || idx >= tasks.size()) return "Invalid task number";
                tasks.get(idx).markAsDone();
                storage.save(tasks.all());
                return "Nice! I've marked this task as done:\n  " + tasks.get(idx);
            }

            case UNMARK: {
                int idx = Parser.index1(s, "unmark ") - 1;
                if (idx < 0 || idx >= tasks.size()) return "Invalid task number";
                tasks.get(idx).markAsUndone();
                storage.save(tasks.all());
                return "OK, I've marked this task as not done yet:\n  " + tasks.get(idx);
            }

            case DELETE: {
                int idx = Parser.index1(s, "delete ") - 1;
                if (idx < 0 || idx >= tasks.size()) return "Invalid task number";
                Task removed = tasks.delete(idx);
                storage.save(tasks.all());
                return "Noted. I've removed this task:\n  " + removed
                        + "\nNow you have " + tasks.size() + " tasks in the list.";
            }

            case FIND:
                try {
                    String q = Parser.findKeyword(s);
                    java.util.List<Task> matches = tasks.find(q);
                    if (matches.isEmpty()) return "No matching tasks.";
                    StringBuilder sb = new StringBuilder("Here are the matching tasks in your list:\n");
                    for (int i = 0; i < matches.size(); i++) {
                        sb.append(" ").append(i + 1).append(".").append(matches.get(i)).append("\n");
                    }
                    return sb.toString().trim();
                } catch (BlankException e) {
                    return e.getMessage();
                }

            default:
                return "I'm sorry, but I don't know what that means.";
        }
    }
}
