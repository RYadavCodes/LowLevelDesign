package DocumentManagementSystem.services;

import DocumentManagementSystem.models.Document;
import DocumentManagementSystem.models.command.*;
import DocumentManagementSystem.repositories.DocumentRepo;
import DocumentManagementSystem.responseDtos.DocumentResponse;
import DocumentManagementSystem.exceptions.DocumentNotFoundException;
import DocumentManagementSystem.search.SearchMode;

import java.util.List;

public class DocumentManagerService {

    private final DocumentRepo documentRepo;
    private final SearchService searchService;

    public DocumentManagerService() {
        documentRepo = DocumentRepo.getInstance();
        searchService = new SearchService();
    }

    public DocumentResponse createDocument(String title, String author, String content){
        Editor editor = new Editor(new Document());
        Command command = new CreateCommand(title, author, content, editor);
        editor.execute(command);
        documentRepo.addDocument(editor.getDocument());
        return editor.getDocument().getResponse();
    }

    public DocumentResponse getDocument(String id) throws DocumentNotFoundException {
        return documentRepo.getDocumentById(id).getResponse();
    }

    public DocumentResponse updateDocument(String id, String title, String author, String content) throws DocumentNotFoundException {
        Editor editor = new Editor(documentRepo.getDocumentById(id));
        Command command = new EditCommand(title, author, content, editor);
        editor.execute(command);
        return editor.getDocument().getResponse();
    }

    public DocumentResponse deleteDocument(String id, String author) throws DocumentNotFoundException {
        Editor editor = new Editor(documentRepo.getDocumentById(id));
        Command command = new DeleteCommand(editor, author);
        editor.execute(command);
        return editor.getDocument().getResponse();
    }

    public DocumentResponse restoreDocument(String id, String author, int version) throws DocumentNotFoundException {
        Editor editor = new Editor(documentRepo.getDocumentById(id));
        Command command = new RestoreCommand(editor, author, version);
        editor.execute(command);
        return editor.getDocument().getResponse();
    }

    public List<DocumentResponse> searchByText(String query) {
        return searchService.searchByText(query);
    }

    public List<DocumentResponse> searchWithMode(String query, SearchMode mode) {
        return searchService.searchWithMode(query, mode);
    }
}
