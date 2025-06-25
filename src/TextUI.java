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
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        String dataFileName = "songs.txt";
        ArrayList<Song> songs = readFile(new File(dataFileName));
        songs.sort(new TitleComparator());

        BST<Song> bst = new BST<>(songs, new TitleComparator());
        SearchEngine searchEngine = new SearchEngine(songs, 2000);

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
                case "A" -> addSong(bst, input);
                case "B" -> removeSong(bst, input);
                case "C" -> searchSong(bst, input, searchEngine);
                case "D" -> modifySong(bst, input, songs, searchEngine);
                case "E" -> showStatistics(bst);
                case "X" -> writeFile(bst, input);
                default -> System.out.println("\nInvalid menu option. Please enter A-F or X to exit.\n");
            }
        }
    }

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

    private static void writeFile(BST<Song> songs, Scanner input) {
        System.out.print("Enter the file name to write to: ");
        String fileName = input.nextLine().trim();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write(songs.inOrderString());
        } catch (IOException e) {
            System.out.println("Error found: " + e.getMessage());
        }
    }

    private static void showChoices() {
        System.out.println();
        System.out.println("Select from one of the following choices:");
        System.out.println("A: Upload a new song");
        System.out.println("B: Remove a song");
        System.out.println("C: Search for a song");
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

        System.out.print("Enter the release year: ");
        int releaseYear = Integer.parseInt(input.nextLine());

        System.out.print("Enter the album name: ");
        String album = input.nextLine();

        System.out.print("Enter the number of plays: ");
        int plays = Integer.parseInt(input.nextLine()) / 1000;

        System.out.print("Enter the lyrics in a single line: ");
        String lyrics = input.nextLine();

        Song song = new Song(songName, totalLength, releaseYear, album, plays, lyrics);

        bst.insert(song, new TitleComparator());
        System.out.println("Song added successfully.");
    }

    private static void removeSong(BST<Song> bst, Scanner input) {
        System.out.print("Enter the song name to remove: ");
        String songName = input.nextLine();

        Song dummySong = new Song(songName);
        Song search = bst.search(dummySong, new TitleComparator());
        if (search != null) {
            bst.remove(search, new TitleComparator());
            System.out.println("Song removed successfully.");
        } else {
            System.out.println("Song not found.");
        }
    }

    private static void searchSong(BST<Song> bst, Scanner input, SearchEngine searchEngine) {
        System.out.println("Would you like to:");
        System.out.println("A: Find and display one song by name");
        System.out.println("B: Find and display all songs that contain a keyword");
        System.out.println();

        String choice = "";
        while (!choice.equals("A") && !choice.equals("B")) {
            System.out.print("Enter your choice: ");
            choice = input.nextLine().trim().toUpperCase();

            switch (choice) {
                case "A" -> searchBySongName(bst, input);
                case "B" -> searchByKeyword(searchEngine, input);
                default -> System.out.println("Invalid choice. Please enter A or B.");
            }
        }
    }

    private static void searchBySongName(BST<Song> bst, Scanner input) {
        System.out.print("Enter the song name to search for: ");
        String songName = input.nextLine().trim();
        System.out.println();

        Song song = bst.search(new Song(songName), new TitleComparator());
        if (song != null) {
            System.out.println("Song found: " + song);
        } else {
            System.out.println("Song not found.");
        }
    }

    private static void searchByKeyword(SearchEngine searchEngine, Scanner input) {
        System.out.print("Enter the keyword to search for: ");
        String keyword = input.nextLine().trim();
        System.out.println();

        BST<Song> songs = searchEngine.search(keyword);
        if (songs.isEmpty()) {
            System.out.println("No songs found with the keyword: " + keyword);
        } else {
            System.out.println("Songs found with the keyword '" + keyword + "':");
            System.out.print(songs.inOrderString());
        }
    }

    private static void modifySong(BST<Song> bst, Scanner input, ArrayList<Song> songList, SearchEngine searchEngine) {
        System.out.print("Enter the song name to modify: ");
        String songName = input.nextLine();

        Song dummySong = new Song(songName);
        Song search = bst.search(dummySong, new TitleComparator());

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
                    long newPlays = Long.parseLong(input.nextLine());
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
        bst.rebuild(new TitleComparator());
        searchEngine.rebuild(songList);
    }

    private static void showStatistics(BST<Song> bst) {
        BST<Song> sortedByPlays = new BST<>(bst, new PlayCountComparator());

        System.out.println("Database Statistics");
        System.out.println("-------------------");

        System.out.println("Total number of songs: " + bst.getSize());
        System.out.println();

        System.out.println("Most played song:");
        System.out.println(sortedByPlays.findMax());

        System.out.println("Least played song:");
        System.out.println(sortedByPlays.findMin());
    }
}