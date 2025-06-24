/**
 * SearchEngine manages the inverted index for keyword search
 * @author Qihang Shen
 */

import java.util.Comparator;
import java.util.List;
import java.util.ArrayList;

public class SearchEngine {
    private final HashTable<WordID> wordTable;
    private final ArrayList<BST<Song>> invertedIndex;
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
     * @return the WordID obj containing the word and its unique id
     */
    public WordID getWordID(String word) {
        WordID dummy = new WordID(word, -1);
        WordID wid = wordTable.get(dummy);
        if (wid == null) {
            wid = new WordID(word, nextId);
            wordTable.add(wid);
            invertedIndex.add(new BST<>());
            nextId++;
        }
        return wid;
    }

    /**
     * indexes a recorded by inserting it in to the BST corresponding to each word in its text
     * @param record the obj to index
     */
    public void indexRecord(Song record) {
        List<String> words = record.getTextWords();

        for (String word : words) {
            WordID wid = getWordID(word);
            BST<Song> bst = invertedIndex.get(wid.getId());
            bst.insert(record, new TitleComparator());
        }
    }

    /**
     * Searches for records containing the given keyword
     * @param keyword the word to search
     * @return a BST of matching records
     */
    public BST<Song> search(String keyword) {
        WordID wid = wordTable.get(new WordID(keyword, -1));
        if (wid == null) {
            return new BST<>();
        }

        return new BST<>(invertedIndex.get(wid.getId()), new TitleComparator());
    }
}

class TitleComparator implements Comparator<Song> {
    @Override
    public int compare(Song o1, Song o2) {
        return o1.getName().compareTo(o2.getName());
    }
}