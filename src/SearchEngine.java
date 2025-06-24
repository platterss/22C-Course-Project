/**
 * SearchEngine manages the inverted index for keyword search
 *
 * @author Qihang Shen
 */

import java.util.List;
import java.util.ArrayList;

public class SearchEngine<T> {
    private final HashTable<WordID> wordTable;
    private final ArrayList<BST<T>> invertedIndex;
    private int nextId;
    private final int DEFAULT_HASH_CAPACITY = 101;

    // **** CONSTRUCTOR **** //

    /**
     * Constructs a new SearchEngine, initializing the word table and inverted index
     */
    public SearchEngine() {
        wordTable = new HashTable<>(DEFAULT_HASH_CAPACITY);
        invertedIndex = new ArrayList<>();
        nextId = 0;
    }

    /**
     * Retrieves the WordID for the given word, creating and inserting a new one if needed
     * @param word the keyword to index
     * @return the WordID containing the word and its unique id
     */
    public WordID getWordID(String word) {
        WordID dummy = new WordID(word, -1);
        WordID wid = wordTable.search(dummy);
        if (wid == null) {
            wid = new WordID(word, nextId);
            wordTable.insert(wid);
            invertedIndex.add(new BST<>(new TitleComparator<>()));
            nextId++;
        }
        return wid;
    }

    /**
     * Indexes a recorded by inserting it in to the BST corresponding to each word in its text
     * @param record the obj to index
     */
    public void indexRecord(T record) {
        List<String> words = record.getTextWords();
        for (int i = 0; i < words.size(); i++) {
            String w = words.get(i);
            WordID wid = getWordID(w);
            BST<T> bst = invertedIndex.get(wid.getId());
            bst.insert(record);
        }
    }

    /**
     * Searches for records containing the given keyword
     * @param keyword the word to search
     * @return a BST of matching records
     */
    public BST<T> search(String keyword) {
        WordID wid = wordTable.search(new WordID(keyword, -1));
        if (wid == null) {
            return new BST<>(new TitleComparator<>());
        }
        return invertedIndex.get(wid.getId());
    }
}

