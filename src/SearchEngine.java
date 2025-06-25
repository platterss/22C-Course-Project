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

    public SearchEngine(ArrayList<Song> allSongs, int tableSize) {
        this.wordIDs = new HashTable<>(tableSize);
        this.invertedIndex = new ArrayList<>(tableSize);
        this.tableSize = tableSize;

        for (Song song : allSongs) {
            indexSong(song);
        }
    }

    public void rebuild(ArrayList<Song> allSongs) {
        this.wordIDs = new HashTable<>(tableSize);
        this.invertedIndex = new ArrayList<>(tableSize);

        for (Song song : allSongs) {
            indexSong(song);
        }
    }

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

    private ArrayList<String> tokenize(String text) {
        text = text.toLowerCase().replaceAll("[^a-z0-9\\s]", " ");
        String[] words = text.split("\\s+");
        HashTable<String> seenWords = new HashTable<>(words.length);
        ArrayList<String> uniqueWords = new ArrayList<>(words.length);

        for (String word : words) {
            if (word.isEmpty() || stopWords.contains(word)) {
                continue;
            }

            if (!seenWords.contains(word)) {
                seenWords.add(word);
                uniqueWords.add(word);
            }
        }

        return uniqueWords;
    }

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