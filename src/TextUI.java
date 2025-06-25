import java.io.*;
import java.util.Comparator;
import java.util.Scanner;
import java.util.ArrayList;

class TitleComparator implements Comparator<Song> {
    @Override
    public int compare(Song s1, Song s2) {
        return s1.getName().compareToIgnoreCase(s2.getName());
    }
}

class PlayCountComparator implements Comparator<Song> {
    @Override
    public int compare(Song s1, Song s2) {
        return Long.compare(s1.getPlays(), s2.getPlays());
    }
}

public class TextUI {
    private static ArrayList<Song> songList;
    private static BST<Song> songBST;
    private static SearchEngine searchEngine;

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        String dataFileName = "songs.txt";

        songList = readFile(new File(dataFileName));
        songList.sort(new TitleComparator());

        songBST = new BST<>(songList, new TitleComparator());
        searchEngine = new SearchEngine(songList, 2000);

        System.out.println("Welcome to the Sabrina Carpenter Database!");

        String choice = "";
        while (!choice.equals("X")) {
            showChoices();

            if (!input.hasNextLine()) {
                choice = "X";
            } else {
                choice = input.nextLine().trim().toUpperCase();
            }

            System.out.println();

            switch (choice) {
                case "A" -> addSong(input);
                case "B" -> removeSong(input);
                case "C" -> searchSong(input);
                case "D" -> modifySong(input);
                case "E" -> showStatistics();
                case "F" -> displaySongs();
                case "X" -> writeFile(input);
                default -> System.out.println("\nInvalid menu option. Please enter A-E or X to exit.\n");
            }
        }
    }

    /**
     * Reads the file containing song data and returns an ArrayList of Song objects
     * @param file the file to read from
     * @return an ArrayList of Song objects
     */
    private static ArrayList<Song> readFile(File file) {
        ArrayList<Song> songs = new ArrayList<>();

        try {
            if (!file.exists()) {
                throw new FileNotFoundException();
            }
        } catch (FileNotFoundException error) {
            throw new RuntimeException("File not found: " + file.getName());
        }

        try (Scanner scanner = new Scanner(new FileReader(file))) {
            while (scanner.hasNextLine()) {
                String name = scanner.nextLine().trim();

                if (name.isEmpty()) {
                    continue;
                }

                String album = scanner.nextLine().split(": ")[1].trim();
                String length = scanner.nextLine().split(": ")[1].trim();
                int releaseYear = Integer.parseInt(scanner.nextLine().split(": ")[1].trim());
                long plays = Long.parseLong(scanner.nextLine().split(": ")[1].trim().replaceAll(",", ""));
                String lyrics = scanner.nextLine().split(": ")[1].trim();


                int minutes = Integer.parseInt(length.split(":")[0].trim());
                int seconds = Integer.parseInt(length.split(":")[1].trim());
                int totalLength = minutes * 60 + seconds;

                Song song = new Song(name, totalLength, releaseYear, album, plays, lyrics);
                songs.add(song);
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }

        return songs;
    }

    /**
     * Writes the current song list to a file specified by the user
     * @param input the Scanner object to read user input
     */
    private static void writeFile(Scanner input) {
        System.out.print("Enter the file name to write to: ");
        String fileName = input.nextLine().trim();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write(songBST.inOrderString());
            System.out.println("Songs written to " + fileName + " successfully.");
            System.out.println("\nThank you for using the Sabrina Carpenter Database!");
        } catch (IOException e) {
            System.out.println("Error found: " + e.getMessage());
        }
    }

    /**
     * Displays the menu choices to the user
     */
    private static void showChoices() {
        System.out.println();
        System.out.println("Select from one of the following choices:");
        System.out.println("A: Upload a new song");
        System.out.println("B: Remove a song");
        System.out.println("C: Search for a song");
        System.out.println("D: Update or modify a song entry");
        System.out.println("E: Show statistics");
        System.out.println("F: Show all songs currently in the database");
        System.out.println("X: Quit program\n");
        System.out.print("Enter your choice: ");
    }

    /**
     * Adds a new song to the database by prompting the user for details
     * @param input the Scanner object to read user input
     */
    private static void addSong(Scanner input) {
        System.out.print("Enter the song name to add: ");
        String songName = input.nextLine();

        System.out.print("Enter the song length in MM:SS format: ");
        String[] lengthSplit = input.nextLine().split(":");
        int minutes = Integer.parseInt(lengthSplit[0]);
        int seconds = Integer.parseInt(lengthSplit[1]);
        int totalLength = minutes * 60 + seconds;

        System.out.print("Enter the release year: ");
        int releaseYear = Integer.parseInt(input.nextLine());

        System.out.print("Enter the album name: ");
        String album = input.nextLine();

        System.out.print("Enter the number of plays: ");
        long plays = Long.parseLong(input.nextLine().trim().replaceAll(",", ""));

        System.out.print("Enter the lyrics in a single line: ");
        String lyrics = input.nextLine();

        Song song = new Song(songName, totalLength, releaseYear, album, plays, lyrics);

        songList.add(song);
        songList.sort(new TitleComparator());
        songBST.insert(song, new TitleComparator());
        searchEngine.rebuild(songList);
        System.out.println("Song added successfully.");
    }

    /**
     * Removes a song from the database by prompting the user for the song name
     * @param input the Scanner object to read user input
     */
    private static void removeSong(Scanner input) {
        System.out.print("Enter the song name to remove: ");
        String songName = input.nextLine();

        Song dummySong = new Song(songName);
        Song search = songBST.search(dummySong, new TitleComparator());

        if (search == null) {
            System.out.println("Song '" + songName + "' not found.");
            return;
        }

        songBST.remove(search, new TitleComparator());
        songList.remove(search);
        searchEngine.rebuild(songList);
        System.out.println("Song removed successfully.");
    }

    /**
     * Prompts the user to search for a song by either name or keyword
     * @param input the Scanner object to read user input
     */
    private static void searchSong(Scanner input) {
        System.out.println("Would you like to:");
        System.out.println("A: Find and display one song by name");
        System.out.println("B: Find and display all songs that contain a keyword");
        System.out.println();

        String choice = "";
        while (!choice.equals("A") && !choice.equals("B")) {
            System.out.print("Enter your choice: ");
            choice = input.nextLine().trim().toUpperCase();

            switch (choice) {
                case "A" -> searchBySongName(input);
                case "B" -> searchByKeyword(input);
                default -> System.out.println("Invalid choice. Please enter A or B.");
            }
        }
    }

    /**
     * Searches for a song by its name and displays the details if found
     * Helper method for searchSong
     * @param input the Scanner object to read user input
     */
    private static void searchBySongName(Scanner input) {
        System.out.print("Enter the song name to search for: ");
        String songName = input.nextLine().trim();
        System.out.println();

        Song song = songBST.search(new Song(songName), new TitleComparator());
        if (song != null) {
            System.out.println("Song found: " + song);
        } else {
            System.out.println("Song not found.");
        }
    }

    /**
     * Searches for songs that contain a keyword in their lyrics, name, or album
     * Helper method for searchSong
     * @param input the Scanner object to read user input
     */
    private static void searchByKeyword(Scanner input) {
        System.out.print("Enter the keyword to search for: ");
        String keyword = input.nextLine().trim();
        System.out.println();

        BST<Song> search = searchEngine.search(keyword);
        if (search.isEmpty()) {
            System.out.println("No songs found with the keyword: " + keyword);

            if (SearchEngine.stopWordsContains(keyword)) {
                System.out.println("Note: The keyword '" + keyword + "' is a stop word and may not yield results.");
            }
        } else {
            System.out.println("Songs found with the keyword '" + keyword + "':");
            System.out.print(search.inOrderString());
        }
    }

    /**
     * Modifies the details of an existing song by prompting the user for changes
     * @param input the Scanner object to read user input
     */
    private static void modifySong(Scanner input) {
        System.out.print("Enter the song name to modify: ");
        String songName = input.nextLine();

        Song dummySong = new Song(songName);
        Song search = songBST.search(dummySong, new TitleComparator());

        if (search == null) {
            System.out.println("Song '" + songName + "' not found.");
            return;
        }

        System.out.println();
        System.out.println("Current details of the song:");
        System.out.println(search);
        System.out.println();

        System.out.println("What would you like to modify?");
        System.out.println("A: Name");
        System.out.println("B: Length");
        System.out.println("C: Release Year");
        System.out.println("D: Album");
        System.out.println("E: Plays");
        System.out.println("F: Lyrics");
        System.out.println();

        String choice = "";
        while (!choice.matches("[A-F]")) {
            System.out.print("Enter your choice: ");
            choice = input.nextLine().trim().toUpperCase();

            switch (choice) {
                case "A" -> {
                    System.out.print("Enter the new song name: ");
                    String newName = input.nextLine();
                    search.setName(newName);
                }
                case "B" -> {
                    System.out.print("Enter the new length in MM:SS format: ");
                    search.setLength(input.nextLine());
                }
                case "C" -> {
                    System.out.print("Enter the new release year: ");
                    int newYear = Integer.parseInt(input.nextLine());
                    search.setReleaseYear(newYear);
                }
                case "D" -> {
                    System.out.print("Enter the new album name: ");
                    String newAlbum = input.nextLine();
                    search.setAlbum(newAlbum);
                }
                case "E" -> {
                    System.out.print("Enter the new number of plays: ");
                    long newPlays = Long.parseLong(input.nextLine().trim().replaceAll(",", ""));
                    search.setPlays(newPlays);
                }
                case "F" -> {
                    System.out.print("Enter the new lyrics: ");
                    String newLyrics = input.nextLine();
                    search.setLyrics(newLyrics);
                }
                default -> System.out.println("Invalid choice. Please enter A-F.");
            }
        }

        System.out.println("Song modified successfully.");
        songBST.rebuild(new TitleComparator());
        searchEngine.rebuild(songList);
    }

    /**
     * Displays statistics about the song database, including the total number of songs,
     */
    private static void showStatistics() {
        BST<Song> sortedByPlays = new BST<>(songBST, new PlayCountComparator());

        System.out.println("Database Statistics");
        System.out.println("-------------------");

        System.out.println("Total number of songs: " + songBST.getSize());
        System.out.println();

        System.out.println("Most played song:");
        System.out.println(sortedByPlays.findMax());

        System.out.println("Least played song:");
        System.out.println(sortedByPlays.findMin());
    }

    /**
     * Displays all songs currently in the database
     */
    private static void displaySongs() {
        System.out.println("All Songs in the Database:");
        if (songList.isEmpty()) {
            System.out.println("No songs available.");
        } else {
            System.out.println(songBST.inOrderString());
        }
    }
}