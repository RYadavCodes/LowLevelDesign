package DocumentManagementSystem.exceptions;

public class DocumentNotFoundException extends RuntimeException {

    public DocumentNotFoundException(String documentId) {
        super("Document not found with ID: " + documentId);
    }

    public DocumentNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}