package labussy.core;

import labussy.task.Task;

import java.util.ArrayList;

public class TaskList {
    private final ArrayList<Task> list;

    // Return a new empty list with no input.
    public TaskList() {
        this.list = new ArrayList<>();
    }

    // Return a new filled list with input
    public TaskList(ArrayList<Task> tasks) {
        this.list = new ArrayList<>(tasks);
    }

    // Add a task to the task list.
    public void add(Task t) {
        list.add(t);
    }

    // Delete by 0-index from the list.
    public Task delete(int idx0) {
        return list.remove(idx0);
    }

    // Getter function with index as para.
    public Task get(int idx0) {
        return list.get(idx0);
    }

    // Return list size.
    public int size() {
        return list.size();
    }

    // Access the whole list for storage.
    public ArrayList<Task> all() {
        return list;
    }

    // Return an arraylist of tasks with matching keyword
    public ArrayList<Task> find(String keyword) {
        String q = keyword.toLowerCase();
        ArrayList<Task> res = new ArrayList<>();
        for (Task t : list) {
            if (t.getDescription().toLowerCase().contains(q)) {
                res.add(t);
            }
        }
        return res;
    }
}
