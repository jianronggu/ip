# Labussy — User Guide

Labussy is a lightweight task manager with a chat‑style GUI built on JavaFX. It’s optimized for a fast keyboard workflow while remaining friendly for new users. Create todos, deadlines, and events; mark/unmark tasks; search quickly; and auto‑save everything.

---

## Quick Start

1. **Install Java 17+** on your computer (JDK 17 recommended).
2. **Download** the latest `labussy.jar` from your releases page.
3. **Choose a home folder** and copy `labussy.jar` there.
4. **Open a terminal** and run:

   ```bash
   cd <folder-containing-jar>
   java -jar labussy.jar
   ```


---

## Command Rules

* **UPPER\_CASE** means you supply the value. Example: `todo DESCRIPTION`.
* **Square brackets** `[...]` are optional parts.
* `…` means the part can repeat **0 or more** times.
* **Order‑agnostic parameters**: when prefixes like `n/`, `p/` are used (not common in Labussy), their order doesn’t matter.
* Extra words for commands that take **no parameters** are ignored (e.g., `list now` ≈ `list`).

---

## Date & Time Input

Labussy accepts multiple date/time formats. Use whichever you prefer:

* `yyyy-MM-dd HHmm` → `2019-12-01 1800`
* `yyyy-MM-dd HH:mm` → `2019-12-01 18:00`
* `MMM d yyyy h:mm a` → `Dec 1 2019 6:00 PM`
* ISO local date‑time → `2019-12-01T18:00`
* ISO local date (defaults to 00:00) → `2019-12-01`

Displayed times use: `MMM d yyyy h:mm a` (e.g., `Dec 1 2019 6:00 PM`).

---

## Features & Commands

### 1) List tasks — `list`

Show all tasks (todos, deadlines, events). Labussy also highlights items **due within 24 hours**.

**Format**

```
list
```

---

### 2) Add a todo — `todo`

Create a simple task with just a description.

**Format**

```
todo DESCRIPTION
```

**Examples**

```
todo read book
```

> 💡 You can add multiple todos quickly by entering them one by one, separated by a comma.

---

### 3) Add a deadline — `deadline`

Create a task that is due **by** a specific date/time.

**Format**

```
deadline DESCRIPTION /by DATE_TIME
```

**Examples**

```
deadline CS2030S lab /by 2025-09-25 23:59
deadline submit proposal /by Dec 1 2025 6:00 PM
```

---

### 4) Add an event — `event`

Create a task that spans a **start** and **end**.

**Format**

```
event DESCRIPTION /from START_DATE_TIME /to END_DATE_TIME
```

**Examples**

```
event Hackathon /from 2025-10-03 09:00 /to 2025-10-05 18:00
```

---

### 5) Mark as done — `mark`

Mark a task (by its shown index) as completed.

**Format**

```
mark INDEX
```

**Example**

```
mark 2
```

---

### 6) Mark as not done — `unmark`

Set a task back to “not completed”.

**Format**

```
unmark INDEX
```

**Example**

```
unmark 2
```

---

### 7) Delete a task — `delete`

Remove a task by index.

**Format**

```
delete INDEX
```

**Example**

```
delete 3
```

---

### 8) Find tasks — `find`

Search tasks by keyword (case‑insensitive, matches the task text).

**Format**

```
find KEYWORD
```

**Examples**

```
find book
find CS2030S
```

---

### 9) Exit — `bye`

Close the app gracefully after responding.

**Format**

```
bye
```

---

## Data & Storage

* **Auto‑save**: Your list is saved **after every change**.
* **Location**: A plain‑text data file is created in the app’s home folder (inside a `data/` subfolder). You can back this up or move it to transfer your tasks.
* **Manual edits**: Editing the file is possible but risky—use valid formats to avoid data loss.

---

## Known Behaviors

* **Due soon**: Deadlines/events starting within the next **24 hours** are flagged in `list`.
* **Indexing**: Commands like `mark`, `unmark`, and `delete` use the **index shown** by the latest `list`/`find`.

---

## Command Summary

| Action           | Format                                  | Example                                                     |
| ---------------- | --------------------------------------- | ----------------------------------------------------------- |
| **List**         | `list`                                  | —                                                           |
| **Add todo**     | `todo DESCRIPTION`                      | `todo read book`                                            |
| **Add deadline** | `deadline DESCRIPTION /by DATE_TIME`    | `deadline report /by 2025-09-30 23:59`                      |
| **Add event**    | `event DESCRIPTION /from START /to END` | `event meeting /from 2019-12-01 18:00 /to 2019-12-01 19:00` |
| **Mark**         | `mark INDEX`                            | `mark 2`                                                    |
| **Unmark**       | `unmark INDEX`                          | `unmark 2`                                                  |
| **Delete**       | `delete INDEX`                          | `delete 3`                                                  |
| **Find**         | `find KEYWORD`                          | `find book`                                                 |
| **Exit**         | `bye`                                   | —                                                           |

---

