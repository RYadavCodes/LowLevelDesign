package DocumentManagementSystem;

public class Editor {

    private Document document;
    private History history;

    public void undo() {
    }

    public void redo() {
    }

    public void execute(Command c) {
        history.push(c, new Memento(this));
        c.execute();
    }

    public String backup() {
        return null;
    }

    public void restore(String state) {

    }
}
