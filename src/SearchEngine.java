import java.util.Comparator;
import java.util.TreeMap;
import java.util.Arrays;
import java.util.ArrayList;

public class SearchEngine {
    private final BST<Song> bst;
    private static String[] stopWords = {
            "a", "about", "across", "after", "all", "almost", "also", "am", "an", "and", "any", "are",
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
            "while", "who", "whom", "why", "will", "with", "would", "yet", "you", "your", "yours"
            };

    public SearchEngine(BST<Song> bst) {
        this.bst = bst;
    }

    public static Song searchName(String songName, BST<Song> bst) {
        Song temp = new Song(songName);
        return bst.search(temp, new TitleComparator());
    }

    public static Song searchYear(int year, BST<Song> bst) {
        Song temp = new Song(year);
        return bst.search(temp, new YearComparator());
    }

    public static void appendLyrics(String lyrics) {
        String[] words = lyrics.split(" ");
        ArrayList<String> completed = new ArrayList<>(words.length);
        for (String word: words) {
            if (Arrays.asList(stopWords).contains(word)) {
                completed.add(word);
            }
        }

    }

    public static Song searchLyric(String lyric, BST<Song> bst) {
        return null;
    }




}