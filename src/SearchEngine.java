import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;

public class SearchEngine {
    private static final List<String> stopWords = Arrays.asList("a", "about", "across", "after", "all", "almost", "also", "am", "an", "and", "any", "are",
            "as", "at", "be", "because", "been", "before", "being", "between", "big", "both", "but", "by",
            "can", "could", "did", "do", "does", "down", "during", "each", "either", "few", "for", "from",
            "further", "good", "great", "had", "has", "have", "he", "her", "here", "him", "himself", "his",
            "how", "however", "i", "if", "in", "into", "is", "it", "its", "itself", "just", "large",
            "like", "little", "long", "many", "me", "might", "more", "most", "much", "must", "my",
            "myself", "neither", "new", "no", "nor", "not", "now", "of", "off", "often", "old", "on",
            "once", "one", "only", "or", "other", "our", "ours", "out", "over", "own", "quite", "rather",
            "really", "same", "she", "should", "small", "so", "some", "such", "than", "that", "the",
            "their", "them", "then", "there", "these", "they", "this", "those", "through", "to", "too",
            "under", "until", "up", "very", "was", "we", "were", "what", "when", "where", "which",
            "while", "who", "whom", "why", "will", "with", "would", "yet", "you", "your", "yours");
    private HashTable<WordID> wordIDs;
    private ArrayList<BST<Song>> invertedIndex;
    private final int tableSize;

    /**
     * Constructs a new SearchEngine object with the given songs and table size.
     * @param allSongs the list of all songs to index
     * @param tableSize the size of the hash table for word IDs
     */
    public SearchEngine(ArrayList<Song> allSongs, int tableSize) {
        this.wordIDs = new HashTable<>(tableSize);
        this.invertedIndex = new ArrayList<>(tableSize);
        this.tableSize = tableSize;

        for (Song song : allSongs) {
            indexSong(song);
        }
    }

    /**
     * Rebuilds the search engine's index with a new list of songs
     * @param allSongs the list of all songs to index
     */
    public void rebuild(ArrayList<Song> allSongs) {
        this.wordIDs = new HashTable<>(tableSize);
        this.invertedIndex = new ArrayList<>(tableSize);

        for (Song song : allSongs) {
            indexSong(song);
        }
    }

    /**
     * Indexes a song by tokenizing its lyrics, name, album, and release year
     * @param song the song to index
     */
    public void indexSong(Song song) {
        ArrayList<String> tokens = tokenize(song.getLyrics());
        tokens.addAll(tokenize(song.getName()));
        tokens.addAll(tokenize(song.getAlbum()));
        tokens.add(song.getReleaseYear() + "");

        HashTable<String> seenWords = new HashTable<>(tokens.size());

        for (String word : tokens) {
            WordID wordID = new WordID(word);

            if (seenWords.contains(word)) {
                continue;
            }

            if (!wordIDs.contains(wordID)) {
                int newID = invertedIndex.size();
                WordID mapping = new WordID(word, newID);
                wordIDs.add(mapping);
                invertedIndex.add(new BST<>());
            }

            WordID stored = wordIDs.get(wordID);
            int slot = stored.getId();

            invertedIndex.get(slot).insert(song, new TitleComparator());
            seenWords.add(word);
        }
    }

    /**
     * Tokenizes the input text by converting it to lowercase, removing non-alphanumeric characters,
     * and splitting it into words. It also filters out stop words and duplicates
     * @param text the text to tokenize
     * @return an ArrayList of unique, non-stop words from the text
     */
    private ArrayList<String> tokenize(String text) {
        text = text.toLowerCase().replaceAll("[^a-z0-9\\s]", " ");
        String[] words = text.split("\\s+");
        HashTable<String> seenWords = new HashTable<>(words.length);
        ArrayList<String> uniqueWords = new ArrayList<>(words.length);

        for (String word : words) {
            if (word.isEmpty() || stopWordsContains(word)) {
                continue;
            }

            if (!seenWords.contains(word)) {
                seenWords.add(word);
                uniqueWords.add(word);
            }
        }

        return uniqueWords;
    }

    /**
     * Checks if the given word is a stop word using binary search
     * @param word the word to check
     * @return true if the word is a stop word, false otherwise
     */
    public static boolean stopWordsContains(String word) {
        int left = 0;
        int right = stopWords.size() - 1;

        while (left <= right) {
            int mid = left + (right - left) / 2;
            int cmp = stopWords.get(mid).compareTo(word);

            if (cmp == 0) {
                return true;
            } else if (cmp < 0) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }

        return false;
    }

    /**
     * Searches for songs that match the given keyword.
     * @param keyword the keyword to search for
     * @return a BST containing all songs that match the keyword, or an empty BST if no matches are found
     */
    public BST<Song> search(String keyword) {
        String cleanedKeyword = keyword.toLowerCase().replaceAll("[^a-z0-9]", "");
        WordID wordID = new WordID(cleanedKeyword);

        if (!wordIDs.contains(wordID)) {
            return new BST<>();
        }

        int slot = wordIDs.get(wordID).getId();
        return invertedIndex.get(slot);
    }
}