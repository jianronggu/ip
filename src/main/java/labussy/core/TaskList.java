package labussy.core;

import labussy.task.Task;

import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 * Mutable list-like facade around tasks with helpers for add/remove/find and persistence integration.
 */

public class TaskList {
    private final ArrayList<Task> list;

    /**
     * Constructs a new TaskList.
     */

    public TaskList() {
        this.list = new ArrayList<>();
    }

    /**
     * Constructs a new TaskList.
     * @param tasks parameter
     */

    public TaskList(ArrayList<Task> tasks) {
        this.list = new ArrayList<>(tasks);
    }

    /**
     * Appends a task to the end of the list.
     * @param t parameter
     */

    public void add(Task t) {
        list.add(t);
    }

    /**
     * Removes and returns the task at the given index.
     * @param idx0 parameter
     * @return result
     */

    public Task delete(int idx0) {
        return list.remove(idx0);
    }

    /**
     * Returns the task at the given zero-based index.
     * @param index parameter
     * @return result
     */

    public Task get(int index) {
        assert index >= 0 && index < list.size() : "index out of bounds";
        return list.get(index);
    }

    /**
     * Returns the number of tasks held.
     * @return result
     */

    public int size() {
        return list.size();
    }

    /**
     * Returns tasks whose string form contains the case-insensitive keyword.
     * @param keyword parameter
     * @return result
     */

    public ArrayList<Task> all() {
        return list;
    }

    // Return an arraylist of tasks with matching keyword
    public ArrayList<Task> find(String keyword) {
        String q = keyword.toLowerCase();
        return list.stream()
                .filter(t -> t.toString().toLowerCase().contains(q))
                .collect(Collectors.toCollection(ArrayList::new));
    }
}
