import java.text.DecimalFormat;

public class Song {
    private String name;
    private int length;
    private int releaseYear;
    private String album;
    private long plays;
    private String lyrics;

    // **** CONSTRUCTORS **** //
    /**
     * Creates a new Song with the specified name
     * @param name the name of the Song
     */
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
    public void setReleaseYear(int releaseYear) {
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
     * @param plays the number of plays
     */
    public void setPlays(long plays) {
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

    /**
     * Returns a String representation of the given Song
     * @return a String representation of the given Song
     */
    @Override
    public String toString() {
        DecimalFormat df = new DecimalFormat("#,###");
        return name + "\n" +
                "Album: " + album + "\n" +
                "Length: " + getLengthString() + "\n" +
                "Release Date: " + releaseYear + "\n" +
                "Plays: " + df.format(plays) + "\n" +
                "Lyrics: " + lyrics;
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
