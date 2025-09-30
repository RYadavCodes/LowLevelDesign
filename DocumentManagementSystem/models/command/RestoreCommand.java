package DocumentManagementSystem.models.command;

import DocumentManagementSystem.models.Document;
import DocumentManagementSystem.models.Version;

public class RestoreCommand implements Command {

    private Editor editor;
    private String author;
    private Integer version;

    public RestoreCommand(Editor editor, String author, Integer version) {
        this.editor = editor;
        this.author = author;
        this.version = version;
    }

    @Override
    public String getName() {
        return "Restore";
    }

    @Override
    public void execute() {
        Document document = editor.getDocument();

        // Find the target version in the versions stack
        Version targetVersion = null;
        for (Version v : document.getVersions()) {
            if (v.getVersion().equals(version)) {
                targetVersion = v;
                break;
            }
        }

        // If target version found, create a new version with its content
        if (targetVersion != null) {
            // Get the current latest version number and increment it
            Version latestVersion = document.getVersions().peek();
            int newVersionNumber = latestVersion.getVersion() + 1;

            // Create new version with target version's content
            Version newVersion = new Version(
                targetVersion.getTitle(),
                targetVersion.getContent(),
                author,
                newVersionNumber,
                document,
                this
            );

            // Add the new version to document
            document.addVersion(newVersion);
        }
    }
}
