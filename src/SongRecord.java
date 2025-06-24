
import java.util.List;
import java.util.Arrays;
/**
 * A additional java file
 * A contract for any record whose text content can be tokenized into words
 * for building an inverted index
 * @author Qihang Shen
 */
public class SongRecord implements TextRecord {
   private String title;
   
   /**
    * constructs a SongRecord with the given title
    * @param title the title of the song
    */
   public SongRecord(String title) {
      this.title = title;
   }
   
   /**
    * returns the song title split into individual words
    * @return a list of words extracted from the song title
    */
   public List<String> getTextWords() {
      return Arrays.asList(title.split("\\W+"));
   }
}
