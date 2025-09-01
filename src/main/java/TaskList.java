import java.util.ArrayList;

public class TaskList {
    private final ArrayList<Task> list;

    public TaskList() {
        this.list = new ArrayList<>();
    }

    public TaskList(ArrayList<Task> tasks) {
        this.list = new ArrayList<>(tasks);
    }

    public void add(Task t) {
        list.add(t);
    }

    // Delete by 0-index from the list.
    public Task delete(int idx0) {
        return list.remove(idx0);
    }

    public Task get(int idx0) {
        return list.get(idx0);
    }

    public int size() {
        return list.size();
    }

    // Access the whole list for storage.
    public ArrayList<Task> all() {
        return list;
    }
}
