package labussy;

import java.util.List;

import labussy.core.Parser;
import labussy.core.Storage;
import labussy.core.TaskList;
import labussy.core.ui.Ui;

import labussy.exception.BlankException;
import labussy.exception.MissingComponentException;

import labussy.task.Task;
import labussy.task.ToDo;
import labussy.task.Deadline;
import labussy.task.Event;

import labussy.time.Dates;

public class Labussy {
    private final Storage storage;
    private final TaskList tasks;
    private final Ui ui = new Ui();   // reuse the same Ui for formatting
    private boolean exit = false;

    public Labussy() {
        this.storage = new Storage();
        this.tasks = new TaskList(storage.load());
    }

    public String getResponse(String input) {
        String s = (input == null) ? "" : input.trim();

        switch (Parser.kind(s)) {
            case BYE:
                exit = true;
                return ui.msgBye();

            case LIST: {
                if (tasks.size() == 0) return ui.msgEmptyList();
                String list = ui.msgList(tasks);
                String soon = ui.msgDueSoon(tasks);
                return soon.isBlank() ? list : (list + "\n\n" + soon);
            }

            case TODO: {
                try {
                    String desc = Parser.todoDesc(s);
                    ToDo t = new ToDo(desc);
                    tasks.add(t);
                    storage.save(tasks.all());
                    return ui.msgAdded(t, tasks.size());
                } catch (BlankException e) {
                    return ui.msgError(e.getMessage());
                }
            }

            case DEADLINE: {
                try {
                    String[] p = Parser.deadlineParts(s); // [desc, by]
                    Deadline d = new Deadline(p[0], new Dates(p[1]));
                    tasks.add(d);
                    storage.save(tasks.all());
                    return ui.msgAdded(d, tasks.size());
                } catch (MissingComponentException | BlankException | IllegalArgumentException e) {
                    return ui.msgError(e.getMessage());
                }
            }

            case EVENT: {
                try {
                    String[] p = Parser.eventParts(s); // [desc, from, to]
                    Event ev = new Event(p[0], new Dates(p[1]), new Dates(p[2]));
                    tasks.add(ev);
                    storage.save(tasks.all());
                    return ui.msgAdded(ev, tasks.size());
                } catch (MissingComponentException | BlankException | IllegalArgumentException e) {
                    return ui.msgError(e.getMessage());
                }
            }

            case MARK: {
                int idx = Parser.index1(s, "mark ") - 1;
                if (idx < 0 || idx >= tasks.size()) return ui.msgInvalidIndex();
                Task t = tasks.get(idx);
                t.markAsDone();
                storage.save(tasks.all());
                return ui.msgMarked(t);
            }

            case UNMARK: {
                int idx = Parser.index1(s, "unmark ") - 1;
                if (idx < 0 || idx >= tasks.size()) return ui.msgInvalidIndex();
                Task t = tasks.get(idx);
                t.markAsUndone();
                storage.save(tasks.all());
                return ui.msgUnmarked(t);
            }

            case DELETE: {
                int idx = Parser.index1(s, "delete ") - 1;
                if (idx < 0 || idx >= tasks.size()) return ui.msgInvalidIndex();
                Task removed = tasks.delete(idx);
                storage.save(tasks.all());
                return ui.msgDeleted(removed, tasks.size());
            }

            case FIND: {
                try {
                    String q = Parser.findKeyword(s);
                    List<Task> matches = tasks.find(q);
                    return ui.msgFind(matches);
                } catch (BlankException e) {
                    return ui.msgError(e.getMessage());
                }
            }

            default:
                return ui.msgError("I'm sorry, but I don't know what that means.");
        }
    }

    public boolean shouldExit() {
        return exit;
    }

    // CLI main can keep using Ui's I/O methods if you already had them
    public static void main(String[] args) {
        Ui ui = new Ui();
        Labussy app = new Labussy();
        ui.showWelcome(); // existing print-based method
        while (true) {
            String input = ui.readCommand();
            String reply = app.getResponse(input);
            System.out.println(reply);
            if (app.shouldExit()) System.exit(0);
        }
    }
}
