package labussy.task;

public class Task {
    protected String description;
    protected boolean isDone;

    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }
    // Mark task with X.
    public String getStatusIcon() {
        return (isDone ? "X" : " ");
    }

    //Return description
    public String getDescription() { return description; }
    // Set task as done.
    public void markAsDone() {
        isDone = true;
    }

    // Set task as undone.
    public void markAsUndone() {
        isDone = false;
    }
    @Override
    public String toString() {
        return "[" + getStatusIcon() + "] " + description;
    }
}
