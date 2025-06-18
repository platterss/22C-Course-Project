import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class Statistics {

    public static void showSongStatistics(String filename) {
    	Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the name of the song to view statistics: ");
        String targetName = scanner.nextLine().trim().toLowerCase();

        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");

                if (parts.length != 5) continue;

                String name = parts[0].trim();
                if (name.toLowerCase().equals(targetName)) {
                    String length = parts[1].trim(); // MM:SS format
                    String releaseDate = parts[2].trim();
                    String album = parts[3].trim();
                    int plays = Integer.parseInt(parts[4].trim());

                    System.out.println("\n--- Song Statistics ---");
                    System.out.println("Name: " + name);
                    System.out.println("Length: " + length);
                    System.out.println("Date of Release: " + releaseDate);
                    System.out.println("Album: " + album);
                    System.out.println("Plays: " + String.format("%,d", plays));
                    return;
                }
            }

            System.out.println("Song not found in database.");

        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }
}