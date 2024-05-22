import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;

public class BackendPlaceholder {

  public BackendPlaceholder(GraphADT graph) { }

  public void loadGraphData(String filename) throws IOException {}

  public List<String> getListOfAllLocations() {
    return null;
  }

  public List<String> findShortestPath(String startLocation, String endLocation) {
    return null;
  }
  // TODO: Extra feature 1


  /**
   * finds the three places that take the least time to get to from the given starting point
   * 
   * @param startLocation - the location from which to search
   * @return a List of the three closest places
   * 
   * @throws NoSuchElementExcpetion if graph has no nodes
   */
  public List<String> threeClosestPlaces(String startLocation) throws NoSuchElementException
  {
    String s = "law library";
    List<String> list = new ArrayList<String>();
    list.add(s);
    return list;
  }

  // TODO: Extra feature 2

  /**
   * checks whether a path exists between the given start and end points
   * 
   * @param startLocation - the starting point
   * @param EndLocation -  the ending point
   * @return true if a path exists between startLocation and endLocation, false otherwise
   */
  public boolean pathExists(String startLocation, String EndLocation)
  {
    return false;
  }

}
