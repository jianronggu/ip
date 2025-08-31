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
                if (line.isEmpty()) continue;
                Task t = new Task(line);
                tasks.add(t);
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
