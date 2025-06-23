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

        System.out.println("Do you wish to add or remove a song? (add/remove/modify)");
        String add = scanner.nextLine();
        if (add.equalsIgnoreCase("add")) {
            addSong(file);
        } else if (add.equalsIgnoreCase("remove")) {
            removeSong(file);
        } else if (add.equalsIgnoreCase("modify")) {
            modifySong(file);
        } else {
            System.out.println("Invalid option. Please enter 'add' or 'remove'.");
        }
    }

    public static void addSong(File file) {
        try (FileWriter writer = new FileWriter("Sabrina.txt", true)) {
            Scanner scanner = new Scanner(System.in);
            StringBuilder sb = new StringBuilder();
            // song name, length, date added, album, plays
            System.out.println("Enter the song name to add:");
            String songName = scanner.nextLine();
            // Check if the song already exists
            if (findSong(file, songName) != -1) {
                System.out.println("Song already exists.");
                return;
            }
            sb.append(songName).append(",");

            System.out.println("Song length (00:00):");
            sb.append(scanner.nextLine()).append(",");

            System.out.println("Date added (MM/DD/YYYY):");
            sb.append(scanner.nextLine()).append(",");

            System.out.println("Album name (i.e Espresso) :");
            sb.append(scanner.nextLine()).append(",");
            System.out.println("Number of plays (0000):");
            int plays = scanner.nextInt() / 1000;
            sb.append(plays).append("\n");
            writer.write(sb.toString());
            System.out.println("Song added successfully.");
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }

    public static void removeSong(File file) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the song name to remove:");
        String songName = scanner.nextLine().trim(); // Trim spaces for accurate comparison

        if (findSong(file, songName) == -1) {
            System.out.println("Song not found");
            return;
        }

        File tempFile = new File("Temp.txt");

        try (BufferedReader reader = new BufferedReader(new FileReader(file));
             BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {

            String currLine;

            while ((currLine = reader.readLine()) != null) {
                String compare = currLine.split(",")[0].trim(); // Trim spaces for accurate comparison
                if (!compare.equalsIgnoreCase(songName)) { // Case-insensitive comparison
                    writer.write(currLine + System.lineSeparator());
                }
            }
        } catch (IOException e) {
            System.out.println("Error found: " + e.getMessage());
            return;
        }

        // Delete the original file first, then rename the temp file
        if (file.delete() && tempFile.renameTo(file)) {
            System.out.println("Removed song " + songName);
        } else {
            System.out.println("Error updating the file.");
        }
    }


    public static void removeSong(File file, String songName) {
        if (findSong(file, songName) == -1) {
            System.out.println("Song not found");
            return;
        }

        File tempFile = new File("Temp.txt");

        try (BufferedReader reader = new BufferedReader(new FileReader(file));
             BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {

            String currLine;

            while ((currLine = reader.readLine()) != null) {
                String compare = currLine.split(",")[0].trim(); // Trim spaces for accurate comparison
                if (!compare.equalsIgnoreCase(songName)) { // Case-insensitive comparison
                    writer.write(currLine + System.lineSeparator());
                }
            }
        } catch (IOException e) {
            System.out.println("Error found: " + e.getMessage());
            return;
        }

        // Delete the original file first, then rename the temp file
        if (file.delete() && tempFile.renameTo(file)) {
            System.out.println("Removed song " + songName);
        } else {
            System.out.println("Error updating the file.");
        }
    }

    public static void modifySong(File file) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the song name to remove:");
        String songName = scanner.nextLine().trim(); // Trim spaces for accurate comparison

        removeSong(file, songName);
        System.out.println("Please enter the updated values for the song");
        addSong(file);
    }

    public static int findSong(File file, String songName) {
        String target = songName.trim().toLowerCase() + ",";
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
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
