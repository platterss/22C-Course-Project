import java.io.*;
import java.util.Comparator;
import java.util.Scanner;

class TitleComparator implements Comparator<Song> {
    @Override
    public int compare(Song s1, Song s2) {
        return s1.getName().compareToIgnoreCase(s2.getName());
    }
}

public class TextUI {
    public static void main(String [] args) throws IOException {
        Scanner input = new Scanner(System.in);
        String dataFileName = "Sabrina.txt";

        BST<Song> bst = new BST<>();
        readFile(new File(dataFileName), bst);

        SearchEngine searchEngine = new SearchEngine(bst);

        System.out.println("Welcome to the Sabrina Carpenter Database!\n");

        String choice = "";
        while (!choice.equals("X")) {
            showChoices();

            if (!input.hasNextLine()) {
                choice = "X";
            } else {
                choice = input.nextLine().trim().toUpperCase();
            }

            switch (choice) {
                case "A" -> {
                    addSong(bst, input);
                }
                case "B" -> {
                    removeSong(bst, input);
                }
                case "C" -> {
                    //search songs with keyword
                    // Submenu:
                    // - Find and display one record using the primary key
                    // - Find and display records using keywords (your search engine)
                }
                case "D" -> {
                    modifySong(bst, input);
                }
                case "E" -> {
                    // Statistics
                }
                case "X" -> {
                    System.out.println("What file do you wish to write this to?");
                    String fileName = input.nextLine();
                    writeFile(bst, fileName);
                }
                default -> {
                    System.out.println("\nInvalid menu option. Please enter A-F or X to exit.\n");
                }
            }
        }
    }

    private static void readFile(File file, BST<Song> bst) {
        try {
            if (!file.exists()) {
                throw new FileNotFoundException();
            }
        } catch (FileNotFoundException error) {
            System.out.println(file.getName() + " not found.");
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            Comparator<Song> cmp = new TitleComparator();
            String line;

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");

                if (parts.length < 5) {
                    continue;
                }

                Song song = readSong(parts);
                bst.insert(song, cmp);
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }

    private static Song readSong(String[] parts) {
        String name = parts[0].trim();
        String[] length = parts[1].split(":");
        String releaseDate = parts[2].trim();
        String album = parts[3].trim();
        int playsByThousands = Integer.parseInt(parts[4].trim());

        int minutes = Integer.parseInt(length[0].trim());
        int seconds = Integer.parseInt(length[1].trim());

        return new Song(name, minutes * 60 + seconds, releaseDate, album, playsByThousands);
    }

    // TODO: Complete this method
    public static void writeFile(BST<Song> songs, String fileName) {
        File tempFile = new File(fileName);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {

            writer.write(songs.inOrderString());

        } catch (IOException e) {
            System.out.println("Error found: " + e.getMessage());
            return;
        }
    }

    public static void showChoices() {
        System.out.println("Select from one of the following choices:");
        System.out.println("A: Upload a new song");
        System.out.println("B: Remove a song");
        System.out.println("C: Search for a song");
//        System.out.println("C: Search songs with a keyword");
//        System.out.println("D: Direct search for one song with key");
        System.out.println("D: Update or modify a song entry");
        System.out.println("E: Show statistics");
        System.out.println("X: Quit program\n");
        System.out.print("Enter your choice: ");
    }

    private static void addSong(BST<Song> bst, Scanner input) {
        System.out.print("Enter the song name to add: ");
        String songName = input.nextLine();

        System.out.print("Enter the song length in MM:SS format: ");
        String[] lengthSplit = input.nextLine().split(":");
        int minutes = Integer.parseInt(lengthSplit[0]);
        int seconds = Integer.parseInt(lengthSplit[1]);
        int totalLength = minutes * 60 + seconds;

        System.out.print("Enter the release date (MM/DD/YYYY): ");
        String releaseDate = input.nextLine();

        System.out.print("Enter the album name: ");
        String album = input.nextLine();

        System.out.print("Enter the number of plays (in thousands): ");
        int plays = Integer.parseInt(input.nextLine()) / 1000;

        Song song = new Song(songName, totalLength, releaseDate, album, plays);

        bst.insert(song, new TitleComparator());
        System.out.println("Song added successfully.");
    }

    private static void removeSong(BST<Song> bst, Scanner input) {
        System.out.print("Enter the song name to remove: ");
        String songName = input.nextLine();

        Song dummySong = new Song(songName, 0, "", "", 0);
        Song search = bst.search(dummySong, new TitleComparator());
        if (search != null) {
            bst.remove(search, new TitleComparator());
            System.out.println("Song removed successfully.");
        } else {
            System.out.println("Song not found.");
        }
    }

    private static void modifySong(BST<Song> bst, Scanner input) {
        System.out.print("Enter the song name to modify: ");
        String songName = input.nextLine();

        Song dummySong = new Song(songName, 0, "", "", 0);
        Song search = bst.search(dummySong, new TitleComparator());

        if (search != null) {
            bst.remove(search, new TitleComparator());
            addSong(bst, input);
            System.out.println("Song modified successfully.");
        } else {
            System.out.println("Song not found.");
        }
    }
}