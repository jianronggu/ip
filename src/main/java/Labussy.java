import java.util.Scanner;


public class Labussy {

    public static void main(String[] args) {

        Ui ui = new Ui();
        Scanner scanner = new Scanner(System.in);
        Storage storage = new Storage();
        TaskList tasks = new TaskList(storage.load());

        ui.showWelcome();

        while(true) {
            String input = ui.readCommand();
            try {
                switch (Parser.kind(input)) {
                    case BYE:
                        ui.showBye();
                        System.exit(0);

                    case LIST:
                        ui.showList(tasks);
                        break;

                    case TODO: {
                        try {
                            String description = Parser.todoDesc(input);
                            ToDo t = new ToDo(description);
                            tasks.add(t);
                            storage.save(tasks.all());
                            ui.showAdded(t, tasks.size());
                            break;
                        } catch (BlankException e) {
                            System.out.println(e.getMessage());
                        }
                    }

                    case DEADLINE: {
                            String[] p = Parser.deadlineParts(input);
                            Deadline d = new Deadline(p[0], new Dates(p[1]));
                            tasks.add(d);
                            storage.save(tasks.all());
                            ui.showAdded(d, tasks.size());
                            break;

                    }

                    case EVENT: {
                            String[] p = Parser.eventParts(input);     // [desc, from, to]
                            Event e = new Event(p[0], new Dates(p[1]), new Dates(p[2]));
                            tasks.add(e);
                            storage.save(tasks.all());
                            ui.showAdded(e, tasks.size());
                            break;
                    }


                    case MARK: {
                        int idx = Parser.index1(input, "mark ") - 1;   // 0-based
                        if (idx < 0 || idx >= tasks.size()) {
                            ui.showError("Invalid task number");
                            break;
                        }
                        tasks.get(idx).markAsDone();
                        storage.save(tasks.all());
                        ui.showMarked(tasks.get(idx));
                        break;
                    }

                    case UNMARK: {
                        int idx = Parser.index1(input, "unmark ") - 1;
                        if (idx < 0 || idx >= tasks.size()) {
                            ui.showError("Invalid task number");
                            break;
                        }
                        tasks.get(idx).markAsUndone();
                        storage.save(tasks.all());
                        ui.showUnmarked(tasks.get(idx));
                        break;
                    }

                    case DELETE: {
                        int idx = Parser.index1(input, "delete ") - 1;
                        if (idx < 0 || idx >= tasks.size()) {
                            ui.showError("Invalid task number");
                            break;
                        }
                        Task removed = tasks.delete(idx);
                        storage.save(tasks.all());
                        ui.showRemoved(removed, tasks.size());
                        break;
                    }

                    default:
                        ui.showLine();
                        ui.showError("I'm sorry, but I don't know what that means.");
                        ui.showLine();
                }
            } catch (MissingComponentException e) {
            System.out.println(e.getMessage());
            } catch (BlankException e) {
                System.out.println(e.getMessage());
            }
        }

    }
}

