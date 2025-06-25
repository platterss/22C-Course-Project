import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Song {
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("MM/dd/yyyy");

    private String name;
    private int length;
    private int releaseYear;
    private String album;
    private long plays;
    private String lyrics;

    // **** CONSTRUCTORS **** //
    /**
     * Creates a new empty Song
     */
    public Song() {
        this.name = "N/A";
        this.length = -1;
        this.releaseYear = -1;
        this.album = "N/A";
        this.plays = -1;
        this.lyrics = "";
    }

    public Song(String name) {
        this.name = name;
        this.length = -1;
        this.releaseYear = -1;
        this.album = "N/A";
        this.plays = -1;
        this.lyrics = "";
    }

    /**
     * Creates a new Song with the specified information
     * @param name the name of the Song
     * @param length the length in seconds
     * @param releaseYear the Song's release date
     * @param album the album of the SOng
     * @param plays the number of plays in thousands
     */
    public Song(String name, int length, int releaseYear, String album, long plays, String lyrics) {
        this.name = name;
        this.length = length;
        this.releaseYear = releaseYear;
        this.album = album;
        this.plays = plays;
        this.lyrics = lyrics;
    }

    // **** MUTATORS **** //
    /**
     * Sets the name of the Song
     * @param name the name of the Song
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Sets the length of the Song
     * @param length the length of the Song in seconds
     */
    public void setLength(int length) {
        this.length = length;
    }

    /**
     * Sets the length of the Song
     * @param length the length of the song as a String in MM:SS format
     */
    public void setLength(String length) {
        String[] split = length.split(":");

        int minutes = Integer.parseInt(split[0]);
        int seconds = Integer.parseInt(split[1]);

        this.length = (minutes * 60) + seconds;
    }

    /**
     * Sets the release date of the Song
     * @param releaseYear the release date of the Song as a String in MM/DD/YYYY format
     */
    public void releaseYear(int releaseYear) {
        this.releaseYear = releaseYear;
    }

    /**
     * Sets the name of the album of the Song
     * @param album the name of the album
     */
    public void setAlbum(String album) {
        this.album = album;
    }

    /**
     * Sets the number of plays of the Song
     * @param plays the number of plays in thousands
     */
    public void setPlays(int plays) {
        this.plays = plays;
    }

    /**
     * Sets the lyrics of the Song
     * @param lyrics the lyrics of the Song
     */
    public void setLyrics(String lyrics) {
        this.lyrics = lyrics;
    }

    // **** ACCESSORS **** //
    /**
     * Returns the name of the Song
     * @return the name of the Song
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the length of the Song in seconds
     * @return the length of the Song in seconds
     */
    public int getLength() {
        return length;
    }

    /**
     * Returns the length of the Song as a String in the format MM:SS
     * @return the length of the Song as a String in the format MM:SS
     */
    public String getLengthString() {
        return String.format("%02d:%02d", length / 60, length % 60);
    }

    /**
     * Returns the release year of the Song
     * @return the release year of the Song
     */
    public int getReleaseYear() {
        return releaseYear;
    }

    /**
     * Returns the name of the Song's album
     * @return the name of the Song's album
     */
    public String getAlbum() {
        return album;
    }

    /**
     * Returns the number of plays of the Song
     * @return the number of plays of the Song
     */
    public long getPlays() {
        return plays;
    }

    /**
     * Returns the lyrics of the Song
     * @return the lyrics of the Song
     */
    public String getLyrics() {
        return lyrics;
    }

    // **** ADDITIONAL METHODS **** //
    /**
     * Helper to parse a date given as a String
     * @param date a date given in MM/DD/YYYY
     * @return a LocalDate representation of the given String
     * @throws IllegalArgumentException if the String isn't in MM/dd/yyyy format
     */
    private static LocalDate parseReleaseDate(String date) throws IllegalArgumentException {
        if (date == null || date.isEmpty()) {
            throw new IllegalArgumentException("parseReleaseDate(" + date + "): must be a valid date in MM/dd/yyyy.");
        }

        try {
            return LocalDate.parse(date, DATE_FORMATTER);
        } catch (Exception e) {
            throw new IllegalArgumentException("parseReleaseDate(" + date + "): must be a valid date in MM/dd/yyyy.");
        }
    }

    /**
     * Returns a String representation of the given Song
     * @return a String representation of the given Song
     */
    @Override
    public String toString() {
        return name + "\n" +
                "Album: " + album + "\n" +
                "Length: " + getLengthString() + "\n" +
                "Release Date: " + releaseYear + "\n" +
                "Plays (thousands): " + plays + "\n";
    }

    /**
     * Checks if two Songs are equal
     * @param obj the object to compare with
     * @return true if the two Songs are equal, false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (!(obj instanceof Song)) {
            return false;
        }

        Song other = (Song) obj;

        return this.name.equals(other.name)
                && this.length == other.length
                && this.releaseYear == other.releaseYear
                && this.album.equals(other.album)
                && this.plays == other.plays;
    }
}
