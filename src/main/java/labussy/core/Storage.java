package labussy.core;

import labussy.task.Deadline;
import labussy.task.Event;
import labussy.task.Task;
import labussy.task.ToDo;
import labussy.time.Dates;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Storage {
    private final File file;

    public Storage() {
        this("data", "duke.txt");
    }

    public Storage(String dir, String name) {
        this.file = new File(dir, name);
    }
// Load tasks from disk, create a folder if missing, skip bad lines.
public ArrayList<Task> load() {
    ensurePath();
    ArrayList<Task> tasks = new ArrayList<>();
    try (Scanner sc = new Scanner(file)) {
        while (sc.hasNextLine()) {
            String line = sc.nextLine().trim();
            if (line.isEmpty() || line.charAt(0) != '[') continue;

            char type = line.charAt(1);              // T / D / E
            boolean done = line.length() > 4 && line.charAt(4) == 'X';
            int descStart = Math.min(7, line.length());

            if (type == 'T') {
                String desc = line.substring(descStart).trim();
                ToDo t = new ToDo(desc);
                if (done) t.markAsDone();
                tasks.add(t);

            } else if (type == 'D') {
                int b = line.indexOf("(by:");
                int close = line.lastIndexOf(')');
                if (b < 0 || close < 0) continue;
                String desc = line.substring(descStart, b).trim();
                String by   = line.substring(b + 4, close).trim(); // after "(by:"
                Deadline d = new Deadline(desc, new Dates(by));
                if (done) d.markAsDone();
                tasks.add(d);

            } else if (type == 'E') {
                int f = line.indexOf("(from:");
                int t = (f < 0) ? -1 : line.indexOf("to:", f);
                int close = line.lastIndexOf(')');
                if (f < 0 || t < 0 || close < 0) continue;
                String desc  = line.substring(descStart, f).trim();
                String from  = line.substring(f + 6, t).trim();     // after "(from:"
                String to    = line.substring(t + 3, close).trim(); // after "to:"
                Event e = new Event(desc, new Dates(from), new Dates(to));
                if (done) e.markAsDone();
                tasks.add(e);
            }
        }
    } catch (FileNotFoundException ignored) { }
    return tasks;
}

    public void save(ArrayList<Task> tasks) {
        ensurePath();
        try (FileWriter fw = new FileWriter(file, false)) {
            String nl = System.lineSeparator();
            for (Task t : tasks) {
                fw.write(t.toString());
                fw.write(nl);
            }
            fw.close();
        } catch (IOException ignored) {}

    }

    private void ensurePath() {
        try {
            File parent = file.getParentFile();
            if (parent != null && !parent.exists()) parent.mkdirs();
            if (!file.exists()) file.createNewFile();
        } catch (IOException ignored) {
            System.out.println("No list detected, creating a new list now.");}
    }

}
