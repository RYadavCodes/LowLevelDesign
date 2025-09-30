package DocumentManagementSystem.services;

import DocumentManagementSystem.models.Document;
import DocumentManagementSystem.repositories.DocumentRepo;
import DocumentManagementSystem.responseDtos.DocumentResponse;
import DocumentManagementSystem.search.SearchMode;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class SearchService {
    private final DocumentRepo documentRepo;

    public SearchService() {
        this.documentRepo = DocumentRepo.getInstance();
    }

    public List<DocumentResponse> searchByText(String query) {
        Set<String> matchingDocIds = documentRepo.searchInTextIndex(query);
        return getDocumentResponses(matchingDocIds);
    }

    public List<DocumentResponse> searchWithMode(String query, SearchMode mode) {
        Set<String> matchingDocIds = documentRepo.searchInTextIndexWithMode(query, mode);
        return getDocumentResponses(matchingDocIds);
    }

    private List<DocumentResponse> getDocumentResponses(Set<String> docIds) {
        List<DocumentResponse> results = new ArrayList<>();

        for (String docId : docIds) {
            try {
                Document document = documentRepo.getDocumentById(docId);
                if (document != null && !document.isDeleted()) {
                    results.add(document.getResponse());
                }
            } catch (Exception e) {
                // Document might have been deleted or not found, skip it
                continue;
            }
        }

        return results;
    }
}