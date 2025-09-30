package DocumentManagementSystem.models;

import DocumentManagementSystem.models.command.Command;

import java.time.LocalDateTime;

public class Version {
    String title;
    Command command;
    String content;
    Integer version;
    LocalDateTime creationDate;
    String author;
    Document document;

    public Version(String title, String content, String author, Integer version, Document document, Command command) {
        this.title = title;
        this.content = content;
        this.author = author;
        this.version = version;
        this.document = document;
        this.command = command;
        creationDate = LocalDateTime.now();
    }

    public String getTitle() {
        return title;
    }

    public Command getCommand() {
        return command;
    }

    public String getContent() {
        return content;
    }

    public Integer getVersion() {
        return version;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public Document getDocument() {
        return document;
    }


}
