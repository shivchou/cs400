import java.io.IOException;
import java.util.*;
import org.junit.jupiter.api.*;

public class BackendTests 
{
	///Users/shivani/Desktop/cs_400/p105_role_code/bin/songs.csv
	
	/**
	 * tests that readData(filename) works as intended (i.e. takes file input and turns
	 * 		csv file data into tree)
	 * 
	 * @throws IOException if invalid file name passed to method
	 */
	@Test
	public void testReadData() throws IOException
	{
		IterableSortedCollection<SongInterface> tester = new ISCPlaceholder<SongInterface>();
		BackendInterface testerObject = new Backend(tester);
		
		testerObject.readData("songs.csv");
		
		//System.out.println(tester.size());
		//Assertions.fail();
		Assertions.assertTrue(tester.size() == 600);
		
	}
	
	/**
	 * tests that list returned by getRange(low, high) is inclusive of low and high values
	 */
	@Test
	public void testDanceabilityRangeEdgeCases()
	{
		IterableSortedCollection<SongInterface> tester = new ISCPlaceholder<SongInterface>();
		BackendInterface testerObject = new Backend(tester);
		try {
			testerObject.readData("songs.csv");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		List<String> test = testerObject.getRange(50,60);
		//System.out.println(test.size());
		//SongInterface o = new Song("Try Sleeping with a Broken Heart", "Alicia Keys", "hip hop",
			//	2010, 111, 82, 50, -5, 13);
		Assertions.assertTrue(test.contains("Try Sleeping with a Broken Heart")); //test inclusive of low end
		
		Assertions.assertTrue(test.contains("Story of My Life")); //test inclusive of high end
		
	}
	
	/**
	 * tests that only songs at or above minimum speed set by filterFastSongs(minSpeed)
	 * 	are included in getRange() list when minSpeed is not 0. 
	 */
	public void testDanceabilityRangeMinSpeed()
	{
		IterableSortedCollection<SongInterface> tester = new ISCPlaceholder<SongInterface>();
		BackendInterface testerObject = new Backend(tester);
		try {
			testerObject.readData("songs.csv");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		List<String> test = testerObject.getRange(50,60);
		
		//test with minSpeed
		testerObject.filterFastSongs(110); //69
				
		Assertions.assertTrue(test.size() == 69);
		
		
		//test edge case where minSpeed exceeds plausible value
		testerObject.filterFastSongs(12345678);
		
		Assertions.assertTrue(test.size() == 0);
	}
	
	/**
	 * tests if:
	 * 		1) empty list returned if getRange() is NOT called before filterFastSongs()
	 * 		2) returned list only contains songs within high and low danceability range AND above minSpeed
	 */
	@Test
	public void testFilterFast()
	{
		IterableSortedCollection<SongInterface> tester = new ISCPlaceholder<SongInterface>();
		BackendInterface testerObject = new Backend(tester);
		try {
			testerObject.readData("songs.csv");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		List<String> test = testerObject.filterFastSongs(110);
		System.out.println("tester size: " + test.size());
		
		Assertions.assertTrue(test.isEmpty());
				
		testerObject.getRange(50, 60);
		test = testerObject.filterFastSongs(110);
		
		System.out.println(test.size());
		
		Assertions.assertTrue(test.size() == 84);
		

	}
	
	
	/**
	 * tests that a song that is NOT in the getRange() list is 
	 * 	NOT in the list returned by fiveMostEnergetic().
	 */
	@Test
	public void testMostEnergetic()
	{
		IterableSortedCollection<SongInterface> tester = new ISCPlaceholder<SongInterface>();
		BackendInterface testerObject = new Backend(tester);
		try {
			testerObject.readData("songs.csv");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		testerObject.getRange(50, 60);
	    testerObject.filterFastSongs(110);
		
		
		List<String> test = testerObject.fiveMostEnergetic();
		
		System.out.println(test.size());
		
		Assertions.assertTrue(test.size() == 5);

	}

	 /**
     * tests that data can be read into tree even with frontend integration
     */
    @Test
    public void testReadDataIntegration() {
        try
        {
          IterableRedBlackTree<SongInterface> tester = new IterableRedBlackTree<SongInterface>();
          TextUITester input = new TextUITester("songs.csv");
          Frontend frontend1 = new Frontend(new Scanner(System.in), new Backend(tester));
          frontend1.readFile();
          
          Assertions.assertTrue(tester.size() == 600);
        } 
        catch (Exception e) 
        {
          Assertions.fail("Exception was thrown when none should be thrown.");
        }
      }

    /**
     * check if getRange works with frontend integration 
     */
    @Test
    public void testGetRangeIntegration()
    {
	 	IterableRedBlackTree<SongInterface> test = new IterableRedBlackTree<SongInterface>();
		TextUITester tester = new TextUITester("songs.csv\n50 - 60");
		Frontend frontend = new Frontend(new Scanner(System.in), new Backend(test));
		frontend.readFile();
		frontend.getValues();
		Assertions.assertTrue(tester.checkOutput().contains("Try Sleeping with a Broken Heart"));
    }
    
    @Test
    public void testFrontendPartnerGetValues()
    {
    	try {
    		  IterableRedBlackTree<SongInterface> test = new IterableRedBlackTree<SongInterface>();
    	      TextUITester tester = new TextUITester("80 - 90");
    	      FrontendPlaceholder frontend = new FrontendPlaceholder(new Scanner(System.in),
    	          new Backend(test));
    	      frontend.getValues();
    	      String output = tester.checkOutput();

    	      Assertions.assertTrue(output.contains("""
    				   5 songs found between 80 - 90:
    				       Baby
    				       Dynamite
    				       Secrets
    				       Empire State of Mind (Part II) Broken Down
    				       Only Girl (In The World)"""));
    	    } catch (Exception e) {
    	      Assertions.fail("fail");
    	    }



    }
    
    @Test
    public void testFrontendPartnerSetFilter()
    {
    	try {
    	      TextUITester tester = new TextUITester("songs.csv\n80 - 90\n85");
    	      FrontendPlaceholder frontend2 = new FrontendPlaceholder(new Scanner(System.in),
    	          new Backend(null));
    	      frontend2.readFile();
    	      frontend2.getValues();
    	      frontend2.setFilter();
    	      String output = tester.checkOutput();

    	      // checks output
    	      if (!output.contains("""
    				   2 songs found between 80 - 90 with min speed 85:
    				       Baby
    				       Only Girl (In The World)"""))
    	        Assertions.fail("setFilter() didn't return the correct values");

    	    } catch (Exception e) {
    	      Assertions.fail("setFilter() threw an exception for valid input");
    	    }


    }
}
