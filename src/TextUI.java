import java.io.*;
import java.util.Comparator;
import java.util.Scanner;

class TitleComparator implements Comparator<Song> {
    @Override
    public int compare(Song s1, Song s2) {
        return s1.getName().compareToIgnoreCase(s2.getName());
    }
}

class YearComparator implements Comparator<Song> {
    @Override
    public int compare(Song s1, Song s2) {
        return Integer.compare(s1.getReleaseYear(), s2.getReleaseYear());
    }
}


public class TextUI {
    public static void main(String [] args) throws IOException {
        Scanner input = new Scanner(System.in);
        String dataFileName = "songs.txt";

        BST<Song> bst = new BST<>();
        readFile(new File(dataFileName), bst);

        SearchEngine searchEngine = new SearchEngine(bst);

        System.out.println("Welcome to the Sabrina Carpenter Database!");

        String choice = "";
        while (!choice.equals("X")) {
            showChoices();

            if (!input.hasNextLine()) {
                choice = "X";
            } else {
                choice = input.nextLine().trim().toUpperCase();
            }

            switch (choice) {
                case "A" -> addSong(bst, input);
                case "B" -> removeSong(bst, input);
                case "C" -> searchSong(bst, input, searchEngine);
                case "D" -> modifySong(bst, input);
                case "E" -> showStatistics(bst);
                case "X" -> writeFile(bst, input);
                default -> System.out.println("\nInvalid menu option. Please enter A-F or X to exit.\n");
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

        try (Scanner scanner = new Scanner(new FileReader(file))) {
            Comparator<Song> cmp = new TitleComparator();

            while (scanner.hasNextLine()) {
                String name = scanner.nextLine().trim();

                if (name.isEmpty()) {
                    continue;
                }

                String album = scanner.nextLine().split(": ")[1].trim();
                String length = scanner.nextLine().split(": ")[1].trim();
                int releaseYear = Integer.parseInt(scanner.nextLine().split(": ")[1].trim());
                long plays = Long.parseLong(scanner.nextLine().split(": ")[1].trim());
                String lyrics = scanner.nextLine().trim();


                int minutes = Integer.parseInt(length.split(":")[0].trim());
                int seconds = Integer.parseInt(length.split(":")[1].trim());
                int totalLength = minutes * 60 + seconds;

                Song song = new Song(name, totalLength, releaseYear, album, plays, lyrics);
                bst.insert(song, cmp);
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
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
        System.out.println("IMPLEMENT THIS METHOD");
    }

    private static void modifySong(BST<Song> bst, Scanner input) {
        System.out.print("Enter the song name to modify: ");
        String songName = input.nextLine();

        Song dummySong = new Song(songName);
        Song search = bst.search(dummySong, new TitleComparator());

        if (search != null) {
            bst.remove(search, new TitleComparator());
            addSong(bst, input);
            System.out.println("Song modified successfully.");
        } else {
            System.out.println("Song not found.");
        }
    }

    private static void showStatistics(BST<Song> bst) {
        System.out.println("IMPLEMENT THIS METHOD");
    }
}