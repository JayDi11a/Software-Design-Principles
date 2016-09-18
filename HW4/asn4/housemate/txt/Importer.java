package cscie97.asn4.housemate.txt;

/**
 * Abstract parent class of all txt loading and query implementing child classes.  Provides utility methods
 * that are useful to all implementing classes and centralizes shared methods (such as txt line parsing).
 *
 * @author Gerald Trotman
 * @version 1.0
 * @see cscie97.asn4.housemate.model.CommandImporter
 * @see cscie97.asn4.housemate.txt.Importer
 */
public abstract class Importer {

    /**
     * Splits up the line string based on the supplied separator into an array of strings and ignores any
     * backslash-escaped separator.
     *
     * @param line       the string to parse out and split into an array based on separator
     * @param separator  the character to use for splitting out the line
     * @return           an array of strings that were split by the separator
     */
    public static String[] parseTXTLine(String line, String separator) {
        // need to do a negative lookbehind to properly escape the backslash-preceeding characters that come
        // immediately prior to the passed separator in input strings (help from:
        // http://stackoverflow.com/questions/820172/how-to-split-a-comma-separated-string-while-ignoring-escaped-commas)
        String[] parts = line.split("(?<!\\\\)"+separator);

        // remove any remaining backslash characters from each of the parts if that backslash is immediately
        // followed by a comma (which is the way our txt is formatted to escape inline commas per column
        for(int i=0; i<parts.length; i++) {
            //parts[i] = parts[i].replaceAll("\\\\,+", ",");
            parts[i] = parts[i].replaceAll("\\\\,+", ",").trim();
        }
        return parts;
    }

}
