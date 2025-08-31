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
    ensurePath(); // make sure ./data/duke.txt exists
    ArrayList<Task> tasks = new ArrayList<>();
    try (Scanner sc = new Scanner(file)) {
        while (sc.hasNextLine()) {
            String line = sc.nextLine().trim();
            if (line.isEmpty() || line.charAt(0) != '[') continue;

            char type = line.charAt(1);                     // T / D / E
            boolean done = line.length() > 4 && line.charAt(4) == 'X';

            // after header "[?][ ] " -> index 7 is the start of description
            String rest = line.length() >= 7 ? line.substring(7) : "";

            if (type == 'T') {
                String desc = rest.trim();
                ToDo t = new ToDo(desc);
                if (done) t.markAsDone();
                tasks.add(t);
            } else if (type == 'D') {
                int byIdx = line.indexOf("(by:", 7);
                if (byIdx < 0) byIdx = line.indexOf("(by: ", 7);
                int close = line.lastIndexOf(')');
                if (byIdx > 0 && close > byIdx) {
                    String desc = line.substring(7, byIdx).trim();
                    int start = byIdx + 4;                  // after "(by:"
                    if (start < line.length() && line.charAt(start) == ' ') start++; // optional space
                    String by = line.substring(start, close).trim();
                    Deadline d = new Deadline(desc, by);
                    if (done) d.markAsDone();
                    tasks.add(d);
                }
            } else if (type == 'E') {
                int fromIdx = line.indexOf("(from:", 7);
                if (fromIdx < 0) fromIdx = line.indexOf("(from: ", 7);
                int toIdx = line.indexOf("to:", fromIdx);   // allow "to:2pm" or "to: 2pm"
                int close = line.lastIndexOf(')');
                if (fromIdx > 0 && toIdx > fromIdx && close > toIdx) {
                    String desc = line.substring(7, fromIdx).trim();
                    int fs = fromIdx + 6;                   // after "(from:"
                    if (fs < line.length() && line.charAt(fs) == ' ') fs++; // optional space
                    String from = line.substring(fs, toIdx).trim();
                    int ts = toIdx + 3;                     // after "to:"
                    if (ts < line.length() && line.charAt(ts) == ' ') ts++; // optional space
                    String to = line.substring(ts, close).trim();
                    Event e = new Event(desc, from, to);
                    if (done) e.markAsDone();
                    tasks.add(e);
                }
            }
        }
    } catch (FileNotFoundException ignored) {}
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
