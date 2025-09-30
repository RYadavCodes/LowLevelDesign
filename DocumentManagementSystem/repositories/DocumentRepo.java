package DocumentManagementSystem.repositories;

import DocumentManagementSystem.models.Document;
import DocumentManagementSystem.models.Version;
import DocumentManagementSystem.exceptions.DocumentNotFoundException;
import DocumentManagementSystem.search.InvertedIndex;
import DocumentManagementSystem.search.SearchMode;

import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import java.util.HashSet;

public class DocumentRepo {
    private static volatile DocumentRepo instance;
    private final Map<String, Document> documents;
    private final InvertedIndex textIndex;
    private final Map<String, Set<String>> authorIndex;

    private DocumentRepo() {
        documents = new HashMap<>();
        textIndex = new InvertedIndex();
        authorIndex = new HashMap<>();
    }

    public static DocumentRepo getInstance() {
        if (instance == null) {
            synchronized (DocumentRepo.class) {
                if (instance == null) {
                    instance = new DocumentRepo();
                }
            }
        }
        return instance;
    }

    public synchronized void addDocument(Document document) {
        documents.put(document.getId(), document);
        indexDocument(document);
    }

    public synchronized Document getDocumentById(String id) {
        Document document = documents.get(id);
        if (document == null || document.isDeleted()) {
            throw new DocumentNotFoundException(id);
        }
        return document;
    }

    public synchronized boolean updateDocument(String id, Document updatedDocument) {
        Document existingDocument = documents.get(id);
        if (existingDocument != null && !existingDocument.isDeleted()) {
            // Remove old document from indexes
            removeFromIndexes(existingDocument);

            // Update document
            updatedDocument.setId(id);
            documents.put(id, updatedDocument);

            // Re-index updated document
            indexDocument(updatedDocument);
            return true;
        }
        return false;
    }

    public synchronized boolean softDeleteDocument(String id) {
        Document document = documents.get(id);
        if (document != null && !document.isDeleted()) {
            // Remove from indexes before marking as deleted
            removeFromIndexes(document);
            document.setDeleted(true);
            return true;
        }
        return false;
    }

    public synchronized Set<String> searchInTextIndex(String query) {
        return textIndex.search(query);
    }

    public synchronized Set<String> searchInTextIndexWithMode(String query, SearchMode mode) {
        return textIndex.searchWithMode(query, mode);
    }

    public synchronized Set<String> searchInAuthorIndex(String author) {
        String normalizedAuthor = author.toLowerCase().trim();
        return authorIndex.getOrDefault(normalizedAuthor, new HashSet<>());
    }

    private void indexDocument(Document document) {
        if (document != null && !document.isDeleted() && !document.getVersions().isEmpty()) {
            Version latestVersion = document.getVersions().peek();

            // Index title and content together
            String textToIndex = (latestVersion.getTitle() + " " + latestVersion.getContent()).trim();
            textIndex.indexDocument(document.getId(), textToIndex);

            // Index author
            if (document.getAuthor() != null) {
                String normalizedAuthor = document.getAuthor().toLowerCase().trim();
                authorIndex.computeIfAbsent(normalizedAuthor, k -> new HashSet<>()).add(document.getId());
            }
        }
    }

    private void removeFromIndexes(Document document) {
        if (document != null && !document.getVersions().isEmpty()) {
            Version latestVersion = document.getVersions().peek();

            // Remove from text index
            String textToRemove = (latestVersion.getTitle() + " " + latestVersion.getContent()).trim();
            textIndex.removeDocument(document.getId(), textToRemove);

            // Remove from author index
            if (document.getAuthor() != null) {
                String normalizedAuthor = document.getAuthor().toLowerCase().trim();
                Set<String> authorDocs = authorIndex.get(normalizedAuthor);
                if (authorDocs != null) {
                    authorDocs.remove(document.getId());
                    if (authorDocs.isEmpty()) {
                        authorIndex.remove(normalizedAuthor);
                    }
                }
            }
        }
    }
}
