
public class Song implements SongInterface 
{
	/**
	 * the title of the song
	 */
	private String title;
	
	/**
	 * the song's artist
	 */
	private String artist;
	
	/**
	 * the genre of the song
	 */
	private String genres;
	
	/**
	 * the year the song was released
	 */
	private int year;
	
	/**
	 * the speed of the song (in BPM)
	 */
	private int speed;
	
	/**
	 * the danceability of the song
	 */
	private int dance;
	
	/**
	 * the energy level of the song
	 */
	private int energy;
	
	/**
	 * the loudness of the song (in dB)
	 */
	private int loudness;
	
	/**
	 * the liveliness of the song
	 */
	private int liveliness;

	/**
	 * constructor to make a song object
	 * 
	 * @param title - the title of the song
	 * @param artist - the song's artist
	 * @param genre - the genre(s) of the song
	 * @param year - the year the song was released
	 * @param bpm - the speed of the song
	 * @param energy - the energy of the song
	 * @param dance - the danceability of the song
	 * @param loudness - the loudness of the song (dB)
	 * @param liveliness - the liveliness of the song
	 */
	public Song(String title, String artist, String genre, 
			int year, int bpm, int energy, int dance, int loudness, int liveliness)
	{
		this.title = title;
		
		this.artist = artist;
		
		this.genres = genre;
		
		this.year = year;
		
		this.speed = bpm;
		
		this.dance = dance;
		
		this.energy = energy;
		
		this.loudness = loudness;
		
		this.liveliness = liveliness;
	}

	
	/**
	 * compare this song to another SongInterface object on basis of energy
	 * 
	 * @param o - other SongInterface object to compare
	 * @return 1 if this song's energy is higher than o's energy
	 * 		  -1 if this song's energy is lower than o's energy
	 * 		   0 if this song's energy and o's energy are equal
	 */
	@Override
	public int compareTo(SongInterface o) 
	{
		if(this.getEnergy() > o.getEnergy())
		{
			return 1;
		}
		else if(o.getEnergy() > this.getEnergy())
		{
			return -1;
		}
		return 0;
	}

	/**
	 * gets the title of the song
	 * 
	 * @return title - the title of the song
	 */
	@Override
	public String getTitle() 
	{
		return title;
	}

	/**
	 * gets artist of the song
	 * 
	 * @return artist - the song's artist
	 */
	@Override
	public String getArtist() 
	{
		return artist;
	}

	/**
	 * gets the song's genre(s)
	 * 
	 * @return genres - the genre of the song
	 */
	@Override
	public String getGenres() 
	{
		return genres;
	}

	/**
	 * gets the year the song was released
	 * 
	 * @return year - the year the song was released
	 */
	@Override
	public int getYear() 
	{
		return year;
	}

	/**
	 * gets the speed of the song
	 * 
	 * @return speed - the speed of the song in BPM
	 */
	@Override
	public int getBPM() 
	{
		return speed;
	}

	/**
	 * gets the energy of the song
	 * 
	 * @return energy - the energy of the song
	 */
	@Override
	public int getEnergy() 
	{
		return energy;
	}

	/**
	 * gets the danceability of the song
	 * 
	 * @return dance - the danceability of the song
	 */
	@Override
	public int getDanceability() 
	{
		return dance;
	}

	/**
	 * gets the loudness of the song
	 * 
	 * @return loudness - the loudness of the song (db)
	 */
	@Override
	public int getLoudness() 
	{
		return loudness;
	}

	/**
	 * gets the liveliness of the song
	 * 
	 * @return liveliness - the liveliness of the song
	 */
	@Override
	public int getLiveness() 
	{
		return liveliness;
	}

}
