package DocumentManagementSystem.models.command;

import DocumentManagementSystem.models.Version;
import DocumentManagementSystem.models.Document;

public class EditCommand implements Command {

    private String title;
    private String author;
    private String content;
    private Editor editor;

    public EditCommand(String title, String author, String content, Editor editor) {
        this.title = title;
        this.author = author;
        this.content = content;
        this.editor = editor;
    }

    @Override
    public String getName() {
        return "Edit";
    }

    @Override
    public void execute() {
        Document document = editor.getDocument();
        Version latestVersion = document.getVersions().peek();
        Version newVersion = new Version(title!=null && !title.isEmpty() ? title : latestVersion.getTitle()
                , content!=null && !content.isEmpty() ? content : latestVersion.getContent()
                , author, latestVersion.getVersion()+1, document, this);
        document.addVersion(newVersion);
    }
}
