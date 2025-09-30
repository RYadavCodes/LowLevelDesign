package DocumentManagementSystem.models.command;

import DocumentManagementSystem.models.Version;
import DocumentManagementSystem.models.Document;

public class CreateCommand implements Command {

    String title;
    String author;
    String content;
    Editor editor;

    public CreateCommand(String title, String author, String content, Editor editor) {
        this.title = title;
        this.author = author;
        this.content = content;
        this.editor = editor;
    }

    @Override
    public String getName() {
        return "Create";
    }

    @Override
    public void execute() {
        Document document = editor.getDocument();
        document.setAuthor(author);
        document.addVersion(new Version(title, content, author, 0, document, this));
    }
}
