public class Main {
    public static void main(String[] args) {
        System.out.println(findSong("Nonsense"));    // should print 2
        System.out.println(findSong("Unknown"));
    }

    public class SearchEngineFindSong {

    /**
     * Returns the zero-based line index of the first song whose title
     * matches songTitle (case-insensitive), or –1 if not found.
     */
        public static int findSong(String songTitle) {
            String target = songTitle.trim().toLowerCase() + ",";
            
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


    }
}
