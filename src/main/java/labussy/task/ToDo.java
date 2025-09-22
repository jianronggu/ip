package labussy.task;

// Basic todo to handle a task.

public class ToDo extends Task {

    // Initialise a todo with description.

    public ToDo(String description) {
        super(description);
    }

    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}