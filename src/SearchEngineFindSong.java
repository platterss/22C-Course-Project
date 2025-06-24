import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class SearchEngineFindSong {

    /**
     * Returns the line index of the first song whose title
     * matches songName (case-insensitive), or –1 if not found.
     */
    public static int findSong(String songName) {
        String target = songName.trim().toLowerCase() + ",";
        try (BufferedReader reader = new BufferedReader(new FileReader("Sabrina.txt"))) {
            String line;
            for (int index = 0; (line = reader.readLine()) != null; index++) {
                if (line.toLowerCase().startsWith(target)) {
                    return index;
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
        return -1;
    }

    // quick test
    public static void main(String[] args) {
        System.out.println(findSong("Nonsense"));    // should print 2
        System.out.println(findSong("Unknown"));     // should print -1
    }
}
