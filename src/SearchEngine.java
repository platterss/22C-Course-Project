import java.util.Comparator;
import java.util.TreeMap;

public class SearchEngine {
    private final BST<Song> bst;

    public SearchEngine(BST<Song> bst) {
        this.bst = bst;
    }

    public static Song searchName(String songName, BST<Song> bst) {
        return null;
    }

    public static Song searchYear(int year, BST<Song> bst) {
        return null;
    }

    public static Song searchLyric(String lyric, BST<Song> bst) {
        return null;
    }

    public static TreeMap<String, Integer> mapify(String lyrics) {
        TreeMap<String, Integer> map = new TreeMap<>();
            if (lyrics == null || lyrics.isEmpty()) {
                return map;
            }
            String[] words = lyrics.split(" ");
            for (String word : words) {
                if (!word.isEmpty()) {
                    map.put(word, 1);
                }
            }
            return map;
    }


}