import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Arrays;

public class Song {
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("MM/dd/yyyy");

    private String name;
    private int length;
    private LocalDate releaseDate;
    private String album;
    private int playsByThousands;

    // **** CONSTRUCTORS **** //
    /**
     * Creates a new empty Song
     */
    public Song() {
        this.name = "N/A";
        this.length = -1;
        this.releaseDate = parseReleaseDate("01/01/0001");
        this.album = "N/A";
        this.playsByThousands = -1;
    }

    /**
     * Creates a new Song with the specified information
     * @param name the name of the Song
     * @param length the length in seconds
     * @param releaseDate the Song's release date
     * @param album the album of the SOng
     * @param playsByThousands the number of plays in thousands
     */
    public Song(String name, int length, String releaseDate, String album, int playsByThousands) {
        this.name = name;
        this.length = length;
        this.releaseDate = parseReleaseDate(releaseDate);
        this.album = album;
        this.playsByThousands = playsByThousands;
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
     * @param releaseDate the release date of the Song as a String in MM/DD/YYYY format
     */
    public void setReleaseDate(String releaseDate) {
        this.releaseDate = parseReleaseDate(releaseDate);
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
     * @param playsByThousands the number of plays in thousands
     */
    public void setPlaysByThousands(int playsByThousands) {
        this.playsByThousands = playsByThousands;
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
     * Returns the release date of the Song as a LocalDate object
     * @return the release date of the Song as a LocalDate object
     */
    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    /**
     * Returns the release date of the Song as a String
     * @return the release date of the Song as a String
     */
    public String getReleaseDateString() {
        return releaseDate.format(DATE_FORMATTER);
    }

    /**
     * Returns the name of the Song's album
     * @return the name of the Song's album
     */
    public String getAlbum() {
        return album;
    }

    /**
     * Returns the number of plays of the Song in thousands
     * @return the number of plays of the Song in thousands
     */
    public int getPlaysByThousands() {
        return playsByThousands;
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
            throw new IllegalArgumentException("parseReleaseDate(" + date +"): must be a valid date in MM/dd/yyyy.");
        }
    }

    /**
    * splits this song's texual fields into a list of words
    * @return a List of words extracted from name and album
    */
    @Override
    public List<String> getTextWords() {
        String combined = name + " " + album;
        return Arrays.asList(combined.split("\\W+");
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
                "Release Date: " + getReleaseDateString() + "\n" +
                "Plays (thousands): " + playsByThousands;
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
                && this.releaseDate.equals(other.releaseDate)
                && this.album.equals(other.album)
                && this.playsByThousands == other.playsByThousands;
    }
}
