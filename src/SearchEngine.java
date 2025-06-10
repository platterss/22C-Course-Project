import java.io.*;
import java.util.Scanner;

public class SearchEngine {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        File file = new File("Sabrina.txt");

        String filename = "Sabrina.txt";

        try {
            if (!file.exists()) {
                throw new FileNotFoundException();
            }
        } catch (FileNotFoundException error) {
            System.out.println(filename + " not found.");
            return;
        }

        System.out.println("Do you wish to add or remove a song? (add/remove)");
        String add = scanner.nextLine();
        if (add.equalsIgnoreCase("add")) {
            addSong();
        } else if (add.equalsIgnoreCase("remove")) {
            removeSong();
        } else {
            System.out.println("Invalid option. Please enter 'add' or 'remove'.");
        }
    }

    public static void addSong() {
        try (FileWriter writer = new FileWriter("Sabrina.txt", true)) {
            Scanner scanner = new Scanner(System.in);
            StringBuilder sb = new StringBuilder();
            // song name, length, date added, album, plays
            System.out.println("Enter the song name to add:");
            String songName = scanner.nextLine();
            // Check if the song already exists
            if (findSong(songName) == -1) {
                System.out.println("Song already exists.");
                return;
            }
            sb.append(songName).append(",");

            System.out.println("Song length (00.00):");
            sb.append(scanner.nextLine()).append(",");

            System.out.println("Date added (MM/DD/YYYY):");
            sb.append(scanner.nextLine()).append(",");

            System.out.println("Album name:");
            sb.append(scanner.nextLine()).append(",");

            System.out.println("Number of plays:");
            sb.append(scanner.nextLine()).append("\n");
            writer.write(sb.toString());
            System.out.println("Song added successfully.");
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }

    public static void removeSong() {
        Scanner scanner = new Scanner(System.in);
        // Logic to remove a song from the file
        System.out.println("Enter the song name to add:");
        String songName = scanner.nextLine();
        // search for the song in the file and remove it

        System.out.println("Song " + songName + " added.");
    }
}
