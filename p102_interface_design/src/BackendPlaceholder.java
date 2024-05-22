import java.util.List;
import java.io.IOException;

/**
 * BackendPlaceholder - CS400 Project 1: iSongify
 *
 * This class doesn't really work.  The methods are hardcoded to output values
 * as placeholders throughout development.  It demonstrates the architecture
 * of the Backend class that will be implemented in a later week.
 */
public class BackendPlaceholder implements BackendInterface {

    public BackendPlaceholder(IterableSortedCollection<SongInterface> tree) {}

    /**
     * Loads data from the .csv file referenced by filename.
     * @param filename is the name of the csv file to load data from
     * @throws IOException when there is trouble finding/reading file
     */
    public void readData(String filename) throws IOException {
	// Note: this placeholder doesn't need to output anything,
	// it will be implemented by the backend developer in P105.
    }

    /**
     * Retrieves a list of song titles for songs that have a length
     * within the specified range.
     * @param low is the minimum length of songs in the returned list
     * @param hight is the maximum length of songs in the returned list
     * @return List of titles for all songs in specified range
     */
    public List<String> getRange(int low, int high) {
	// placeholder just returns a hard coded list of songs
	return java.util.Arrays.asList(new String[]{
		"Hey, Soul Sister",
		"Love The Way You Lie",
		"TiK ToK",
		"Bad Romance",
		"Just the Way You Are"
	    });
    }

    // TODO: design two addition features for the backend to provide and add
    // a public method for each.  Ensure that the inputs and return values
    // for each are primitives, Strings, or Lists.  And include clear JavaDoc
    // comments describing the purpose and use of each.
    
    /**
     * generates a list of all songs in the tree from a given year
     * 
     * @param year - the year to look for songs from 
     * @return a list of all the songs in the tree that are from the given year
     * @throws IllegalArgumentException if parameter year does not fall in the range of years
     *  included in the tree
     */
    public List<String> getYear(int year)
    {
    	return java.util.Arrays.asList(new String[]{
    			"Hey, Soul Sister",
    			"Love The Way You Lie",
    			"TiK ToK",
    			"Bad Romance",
    			"Just the Way You Are"
    		    });
    }
    
    /**
     * generates a list of songs that fall within the specified energy range
     * 
     * @param low - the minimum energy of songs in the returned list  
     * @param high - the maximum energy of songs in the returned list
     * @return a list of songs that fall within the specified energy range
     * @throws IllegalArgumentException if parameters do not fall in the range of years
     *  included in the tree
     */
    public List<String> getEnergyRange(int low, int high)
    {
    	return java.util.Arrays.asList(new String[]{
    			"Hey, Soul Sister",
    			"Love The Way You Lie",
    			"TiK ToK",
    			"Bad Romance",
    			"Just the Way You Are"
    		    });
    }
    
    
}
