package DocumentManagementSystem.models.command;

import DocumentManagementSystem.models.Document;

public class Editor {

    private final Document document;

    public Editor(Document document) {
        this.document = document;
    }

    public void execute(Command c) {
        c.execute();
    }

    public Document getDocument() {
        return document;
    }
}
