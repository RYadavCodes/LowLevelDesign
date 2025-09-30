package DocumentManagementSystem.models.command;

import DocumentManagementSystem.models.Version;
import DocumentManagementSystem.models.Document;

public class DeleteCommand implements Command {

    private Editor editor;
    private String author;


    public DeleteCommand(Editor editor, String author) {
        this.editor = editor;
        this.author = author;
    }

    @Override
    public String getName() {
        return "Delete";
    }

    @Override
    public void execute() {
        Document document = editor.getDocument();
        document.setDeleted(true);
        Version latestVersion = document.getVersions().peek();
        Version newVersion = new Version(latestVersion.getTitle(), latestVersion.getContent()
                , author, latestVersion.getVersion()+1, document, this);
        document.addVersion(newVersion);
    }
}
