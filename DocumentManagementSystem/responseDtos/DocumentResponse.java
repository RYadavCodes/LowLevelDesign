package DocumentManagementSystem.responseDtos;

import java.time.LocalDateTime;

public class DocumentResponse {
    String id;
    String author;
    boolean deleted;
    LocalDateTime creationDate;
    String title;
    String content;
    Integer version;
    LocalDateTime lastUpdatedDate;

    public DocumentResponse(String id, String author, boolean deleted, LocalDateTime creationDate, String title, String content, Integer version, LocalDateTime lastUpdatedDate) {
        this.id = id;
        this.author = author;
        this.deleted = deleted;
        this.creationDate = creationDate;
        this.title = title;
        this.content = content;
        this.version = version;
        this.lastUpdatedDate = lastUpdatedDate;
    }
}
