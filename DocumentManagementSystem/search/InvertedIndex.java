package DocumentManagementSystem.search;

import java.util.*;

public class InvertedIndex {
    private final Map<String, Set<String>> index;

    public InvertedIndex() {
        this.index = new HashMap<>();
    }

    public synchronized void indexDocument(String docId, String text) {
        if (text == null || text.trim().isEmpty()) {
            return;
        }

        List<String> terms = tokenize(text);
        for (String term : terms) {
            String normalizedTerm = normalize(term);
            if (!normalizedTerm.isEmpty()) {
                index.computeIfAbsent(normalizedTerm, k -> new HashSet<>()).add(docId);
            }
        }
    }

    public synchronized void removeDocument(String docId, String text) {
        if (text == null || text.trim().isEmpty()) {
            return;
        }

        List<String> terms = tokenize(text);
        for (String term : terms) {
            String normalizedTerm = normalize(term);
            Set<String> docIds = index.get(normalizedTerm);
            if (docIds != null) {
                docIds.remove(docId);
                if (docIds.isEmpty()) {
                    index.remove(normalizedTerm);
                }
            }
        }
    }

    public synchronized Set<String> search(String query) {
        if (query == null || query.trim().isEmpty()) {
            return new HashSet<>();
        }

        List<String> terms = tokenize(query);
        if (terms.isEmpty()) {
            return new HashSet<>();
        }

        Set<String> result = null;
        for (String term : terms) {
            String normalizedTerm = normalize(term);
            Set<String> docIds = index.getOrDefault(normalizedTerm, new HashSet<>());

            if (result == null) {
                result = new HashSet<>(docIds);
            } else {
                result.retainAll(docIds); // AND operation
            }
        }

        return result != null ? result : new HashSet<>();
    }

    public synchronized Set<String> searchWithMode(String query, SearchMode mode) {
        if (query == null || query.trim().isEmpty()) {
            return new HashSet<>();
        }

        List<String> terms = tokenize(query);
        if (terms.isEmpty()) {
            return new HashSet<>();
        }

        Set<String> result = new HashSet<>();
        boolean firstTerm = true;

        for (String term : terms) {
            String normalizedTerm = normalize(term);
            Set<String> docIds = index.getOrDefault(normalizedTerm, new HashSet<>());

            if (firstTerm) {
                result.addAll(docIds);
                firstTerm = false;
            } else {
                if (mode == SearchMode.AND) {
                    result.retainAll(docIds);
                } else { // OR
                    result.addAll(docIds);
                }
            }
        }

        return result;
    }

    private List<String> tokenize(String text) {
        if (text == null) {
            return new ArrayList<>();
        }

        String[] words = text.toLowerCase()
                            .replaceAll("[^a-zA-Z0-9\\s]", " ")
                            .split("\\s+");

        List<String> tokens = new ArrayList<>();
        for (String word : words) {
            if (!word.trim().isEmpty()) {
                tokens.add(word.trim());
            }
        }
        return tokens;
    }

    private String normalize(String term) {
        if (term == null) {
            return "";
        }
        return term.toLowerCase().trim();
    }

    public synchronized int getIndexSize() {
        return index.size();
    }

    public synchronized boolean isEmpty() {
        return index.isEmpty();
    }
}