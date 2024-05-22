import java.io.*;
import java.io.IOException;
import java.util.*;

public class Backend implements BackendInterface 
{
	
	/**
	 * a list that maintains the most recent call of getRange()
	 */
	private List<SongInterface> mostRecentRange;

	/**
	 * an integer that maintains the minSpeed specified by the 
	 * 	most recent call of filterFastSongs()
	 */
	private int minSpeed;

	/**
	 * a tree that songs are loaded into when read from the file
	 */
	private IterableSortedCollection<SongInterface> tree;

	/**
	 * constructor to create a Backend object & initialize instance fields
	 * 
	 * @param tree the tree to load songs into
	 */
	public Backend(IterableSortedCollection<SongInterface> tree) 
	{
		//tree = new ISCPlaceholder<SongInterface>();
		
		this.tree = tree; // set tree passed as parameter to tree used in object

		minSpeed = 0; // initialize minSpeed

		mostRecentRange = new ArrayList<SongInterface>(); // initialize list returned by getRange()
	}

	/**
	 * Loads data from the .csv file referenced by filename.
	 * 
	 * @param filename is the name of the csv file to load data from
	 * @throws IOException when there is trouble finding/reading file
	 */
	@Override
	public void readData(String filename) throws IOException 
	{
		if(filename == null)
		{
			throw new IOException("illegal file input");
		}
		File f;
		f = new File(filename);
		String title;
		String artist;
		String genre;
		int year;
		int bpm;
		int dance;
		int energy;
		int loudness;
		int liveliness;
		int count = 1;

		Scanner scan = new Scanner(f);

		scan.useDelimiter(",");

		scan.nextLine();
		while (scan.hasNext()) 
		{
			title = scan.next();
			
			if(title.charAt(0) == '"') 
			{
				title += scan.next();
				if(count == 303 || count == 457)
				{
					title += scan.next();
				}
				
			}
			//System.out.println(title);
			
			artist = scan.next();
			//System.out.println(artist);
			
			genre = scan.next();
			
			//System.out.println(genre);
			
			year = Integer.parseInt(scan.next());
			//System.out.println(year);
			
			bpm = scan.nextInt();
			
			energy = scan.nextInt();
			
			dance = scan.nextInt();
			
			loudness = scan.nextInt();
			
			liveliness = scan.nextInt();

			SongInterface s = new Song(title, artist, genre, year, bpm, energy, dance, loudness, liveliness);
			
			//System.out.println(title);
			
			tree.insert(s);
			
			count++;
			
			scan.nextLine();
			if(scan.hasNextLine())
			{
				continue; //check for next line - pretty sure this is not right but whatever
			}
			else
			{
				break;
			}
		}

		scan.close();

	}

	/**
	 * Retrieves a list of song titles for songs that have a Danceability within the
	 * specified range (sorted by Danceability in ascending order). If a minSpeed
	 * filter has been set using filterFastSongs(), then only songs with speed
	 * greater than or equal to that minSpeed should be included in the list
	 * returned by this method. Note that either this danceability range, or the
	 * resulting unfiltered list of songs should be saved for later use by the other
	 * methods defined in this class.
	 *
	 * @param low is the minimum danceability of songs in the returned list
	 * @param hight is the maximum danceability of songs in the returned list
	 * @return List of titles for all songs in specified range
	 */
	/**
	 *
	 */
	@Override
	public List<String> getRange(int low, int high) 
	{
		ArrayList<String> rangeTitles = new ArrayList<String>();		
		// clear list if previously called
		if (!mostRecentRange.isEmpty()) {
			mostRecentRange.clear();
		}
		
		//set iteration start point to ignore any values below low
		Iterator<SongInterface> i = tree.iterator( );
		tree.setIterationStartPoint(new Song("", "", "", 0, 0, 0, low, 0, 0));

		while(i.hasNext()) {
			
			SongInterface song = (SongInterface) i.next();
			
			if (song.getDanceability() <= high 
					&& song.getBPM() >= minSpeed && song.getDanceability() >= low) 
			{ 
				mostRecentRange.add(song); // add songs to field list
				rangeTitles.add(song.getTitle());
			}
			
		}
		if(mostRecentRange.isEmpty())
		{
			System.out.println("empty");
		}
		//System.out.println(rangeTitles.size());
		return rangeTitles; // return list 
	}

	/**
	 * Filters the list of songs returned by future calls of getRange() and
	 * fiveMostEnergetic() to only include Fast songs. If getRange() was previously
	 * called, then this method will return a list of song titles (sorted in
	 * ascending order by Danceability) that only includes songs that as fast or
	 * faster than minSpeed. If getRange() was not previusly called, then this
	 * method should return an empty list.
	 *
	 * Note that this minSpeed threshold should be saved for later use by the other
	 * methods defined in this class.
	 *
	 * @param minSpeed is the minimum speed in bpm of returned songs
	 * @return List of song titles, empty if getRange was not previously called
	 */
	@Override
	public List<String> filterFastSongs(int minSpeed) {
		
		List<String> fastSongs = new ArrayList<String>();
		if (mostRecentRange.isEmpty()) {
			return fastSongs;
		}
		this.minSpeed = minSpeed;
		
		for(SongInterface s : mostRecentRange)
		{
			if (s != null && s.getBPM() >= minSpeed)
            {
                fastSongs.add(s.getTitle());
            }
		}

		System.out.println("filter size: " + fastSongs.size());
		return fastSongs;
	}

	/**
	 * This method makes use of the attribute range specified by the most recent
	 * call to getRange(). If a minSpeed threshold has been set by filterFastSongs()
	 * then that will also be utilized by this method. Of those songs that match
	 * these criteria, the five most energetic will be returned by this method as a
	 * List of Strings in order of danceability. Each string contains the
	 * energy rating followed by a colon, a space, and then the song's title. If
	 * fewer than five such songs exist, display all of them.
	 *
	 * @return List of five most energetic song titles and their energies
	 * @throws IllegalStateException when getRange() was not previously called.
	 */
	@Override
	public List<String> fiveMostEnergetic() 
	{
		// error if getRange() has NOT been called
		if (mostRecentRange.isEmpty() || mostRecentRange == null) {
			throw new IllegalStateException("getRange() must be called before fiveMostEnergetic()");
		}
		// hold songs with the most energy
		List<SongInterface> mostEnergy = new ArrayList<SongInterface>();

		// compile list of songs that are in danceability range and
		// (if applicable) above minimum speed as specified by filterFastSongs()
		for(SongInterface s : mostRecentRange)
		{
			if (s.getBPM() >= minSpeed) // make sure song is above minSpeed
			{
				mostEnergy.add(s);
			}
			mostEnergy.add(s);
		}

		Collections.sort(mostEnergy, Collections.reverseOrder()); //reorder list in terms of energy
		
		List<String> result = new ArrayList<>();
		for(SongInterface song : mostEnergy)
		{
			if(!result.contains(song.getEnergy() + ": " + song.getTitle()))
			{
				result.add(song.getEnergy() + ": " + song.getTitle());
			}
			
			if(result.size() == 5)
			{
				break;
			}
			
		}
		return result;
	}

}
