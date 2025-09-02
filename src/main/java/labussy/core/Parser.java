package labussy.core;

import labussy.exception.BlankException;
import labussy.exception.MissingComponentException;

public final class Parser {
    private Parser() {}
    // Kind ENUM stores all the instruction words.
    public enum Kind { BYE, LIST, TODO, DEADLINE, EVENT, MARK, UNMARK, DELETE, FIND, UNKNOWN }

    public static Kind kind(String in) {
        String s = in.trim();
        if (s.equalsIgnoreCase("bye"))  return Kind.BYE;
        if (s.equalsIgnoreCase("list")) return Kind.LIST;
        if (s.startsWith("todo "))      return Kind.TODO;
        if (s.startsWith("deadline "))  return Kind.DEADLINE;
        if (s.startsWith("event "))     return Kind.EVENT;
        if (s.startsWith("mark "))      return Kind.MARK;
        if (s.startsWith("unmark "))    return Kind.UNMARK;
        if (s.startsWith("delete "))    return Kind.DELETE;
        if (s.startsWith("find "))      return Kind.FIND;
        return Kind.UNKNOWN;
    }

    // Parse a 1-based index after a prefix like "todo ".
    public static int index1(String in, String prefix) {
        return Integer.parseInt(in.substring(prefix.length()).trim());
    }

    /**
     * Returns String description for todo
     * If the position is unset, BlankException is returned.
     *
     * @param in input of "todo description".
     * @return Lateral location.
     * @throws BlankException if description is empty.
     */
    public static String todoDesc(String in) throws BlankException {
        String description = in.substring("todo ".length()).trim();
        if (description.isEmpty()) throw new BlankException();
        return description;
    }
    /** deadline <desc> /by <when>  -> [desc, when]
     *  @param in input of "deadline description /by 2025-09-01"
     *  @return [description, when]
     *  @throws BlankException if description is empty.
     *  @throws MissingComponentException if /by is missing.
     *  */
    public static String[] deadlineParts(String in) throws MissingComponentException, BlankException {
        int byIdx = in.indexOf("/by");
        if (byIdx < 0) throw new MissingComponentException();
        String desc = in.substring("deadline ".length(), byIdx).trim();
        if (desc.isEmpty()) throw new BlankException();
        String when = in.substring(byIdx + 3).trim();
        return new String[]{desc, when};
    }

    /** event <desc> /from <from> /to <to>  -> [desc, from, to]
     *  @param in input of "event sportsday /from 2025-09-01 /to 2025-09-02"
     *  @return [description, from, to]
     *  @throws BlankException if description is empty.
     *  @throws MissingComponentException if /by is missing.
     *  */
    public static String[] eventParts(String in) throws MissingComponentException, BlankException {
        int f = in.indexOf("/from");
        int t = in.indexOf("/to");
        if (f < 0 || t < 0 || t < f) throw new MissingComponentException();
        String desc = in.substring("event ".length(), f).trim();
        if (desc.isEmpty()) throw new BlankException();
        String from = in.substring(f + 5, t).trim();
        String to   = in.substring(t + 3).trim();
        return new String[]{desc, from, to};
    }

    // Pull out the key word.
    public static String findKeyword(String in) throws BlankException {
        String q = in.substring("find ".length()).trim();
        if (q.isEmpty()) throw new BlankException();
        return q;
    }
}
