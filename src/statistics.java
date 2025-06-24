import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.NumberFormat;

public class Statistics {

    public static void showOverallStats(String filename) {
        int totalSongs = 0;
        long totalPlays = 0;
        
        String mostPlayedSong = "";
        long mostPlays = 0;

        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length != 5) continue;

                String name = parts[0].trim();
                int plays = Integer.parseInt(parts[4].trim());

                totalSongs++;
                totalPlays += plays;

                if (plays > mostPlays) {
                    mostPlays = plays;
                    mostPlayedSong = name;
                }
            }

            System.out.println("\n--- Database Statistics ---");
            System.out.println("Total songs: " + totalSongs);
            System.out.println("Total plays: " + NumberFormat.getIntegerInstance().format(totalPlays));
            if (totalSongs > 0) {
                System.out.printf("Average plays per song: %,d\n", totalPlays / totalSongs);
            }
            if (!mostPlayedSong.isEmpty()) {
                System.out.printf("Most played song: \"%s\" with %,d plays\n", mostPlayedSong, mostPlays);
            }

        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }
}
