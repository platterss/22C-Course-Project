/**
 * searchEngine manages the inverted index for keyword search
 * @author Qihang Shen
 */

import java.util.List;
import java.util.ArrayList;

public class SearchEngine<T extends SearchEngine.TextRecord> {
   private HashTable<WordID> wordTable;
   private ArrayList<BST<T>> invertedIndex;
   private int nextId;
   private final int DEFAULT_HASH_CAPACITY = 101;
   
   /**constructor**/
   
   /**
    *constructs a new searchEngine, initializing the word table and inverted index
    */
   public SearchEngine() {
      wordTable = new HashTable<>(DEFAULT_HASH_CAPACITY);
      invertedIndex = new ArrayList<>();
      nextId = 0;
   }
   
   /**
    * retrieves the wordIDfor the given word, creating and inserting a new one if needed
    * @param word the keyword to index
    * @return the WordID obj containing the word and its unique id
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
    * indexes a recorded by inserting it in to the BST corresponding to each word in its text
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
    * searches for records containing the given keyword
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


