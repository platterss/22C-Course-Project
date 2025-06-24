/**
 * WordID is used to combine the word with
 * the inverted index list's ID
 * @author Qihang Shen
 */
public class WordID implements Comparable<WordID> {
    private final String word;
    private final int id;

    /**
     * Constructs a new WordID mapping the given word to the specified identifier
     * @param word the word to be indexed
     * @param id the unique integer identifier for this word
     */
    public WordID(String word, int id) {
        this.word = word;
        this.id = id;
    }

    /**
     * Returns the word associated with this WordID
     * @return the indexed word
     */
    public String getWord() {
        return word;
    }

    /**
     * Returns the identifier associated with this word in the inverted index
     * @return the unique integer identifier
     */
    public int getId() {
        return id;
    }

    /**
     * Computes a hash code for this WordID based on its word
     * Two wordID instances with the same word will produce the same hash code
     * @return the hash code of the underlying word
     */
    @Override
    public int hashCode() {
        return word.hashCode();
    }


    /**
     * Compares this wordID to another obj for equality.
     * Two WordIDs are considered equal if they represent the same word,
     * regardless of their identifier values
     * @param o the obj to compare with
     * @return true if the specified obj is a WordID with the same word, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof WordID other)) {
            return false;
        }

        return this.word.equals(other.word);
    }

    /**
     * Compares two WordID instances lexicographically by their word
     * this allows wordID obj to be sorted or stored in ordered collections
     * @param other the other wordID to compare with
     * @return a negative/zero/positive integer as the word is less/equal/greater than the specified word
     */
    @Override
    public int compareTo(WordID other) {
        return this.word.compareTo(other.word);
    }
}
