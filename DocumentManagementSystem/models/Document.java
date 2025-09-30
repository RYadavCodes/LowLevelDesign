package DocumentManagementSystem.models;

import DocumentManagementSystem.models.command.Command;
import DocumentManagementSystem.responseDtos.DocumentResponse;

import java.time.LocalDateTime;
import java.util.Stack;
import java.util.UUID;

public class Document {
    String id;
    String author;
    boolean deleted;
    LocalDateTime creationDate;
    Stack<Version> versions;

    public Document(){
        id = UUID.randomUUID().toString();
        versions = new Stack<>();
        creationDate = LocalDateTime.now();
        deleted = false;
    }

    public Document(String author, String title, String content, Command command) {
        id = UUID.randomUUID().toString();
        this.author = author;
        this.creationDate = LocalDateTime.now();
        this.deleted = false;
        this.versions = new Stack<>();
        versions.add(new Version(title, content, author, 0, this, command));
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public Stack<Version> getVersions() {
        return versions;
    }

    public void addVersion(Version version) {
        this.versions.add(version);
    }

    public DocumentResponse getResponse() {
        Version latestVersion = versions.peek();
        return new DocumentResponse(id, author, deleted, creationDate,
                latestVersion.getTitle(), latestVersion.getContent(), latestVersion.getVersion(), latestVersion.getCreationDate());
    }
}
