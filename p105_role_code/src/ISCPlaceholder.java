import java.util.ArrayList;
import java.util.Iterator;

public class ISCPlaceholder<T extends Comparable<T>>
    implements IterableSortedCollection<T> {

    /**
     * a value to be stored
     */
    private T value;
    
    /**
     *  for-now implementation of the tree used by Backend
     */
    private ArrayList<SongInterface> tree = new ArrayList<>();
    
    /**
     * adds a value to the tree
     * 
     * @param data - the data to be inserted
     * @return true if data successfully inserted, false otherwise
     * 
     * @throws NullPointerException if data is null
     * @throws IllegalArgumentException if value of data is already stored in the tree
     */
    public boolean insert(T data) throws NullPointerException, IllegalArgumentException 
    {
    	if(data == null)
    	{
    		throw new NullPointerException("null value");
    	}
    	else if(tree.contains(data)) 
    	{
    		throw new IllegalArgumentException("data already in tree");
    	}
    	value = data;
    	tree.add((SongInterface) value);
    	return true;
    }

    /**
     * checks whether tree contains the specified value
     * 
     * @param data - the value to be searched for in the tree
     * @return true if tree contains data, false otherwise
     */
    public boolean contains(Comparable<T> data) 
    {
    	for(SongInterface s : tree)
    	{
    		if(s.equals(data))
    		{
    			return true;
    		}
    	}
    	return false;
    }

    /**
     * checks to see if tree is empty or not
     * 
     * @return true if tree is empty, false otherwise
     */
    public boolean isEmpty() 
    {
    	return tree.isEmpty();
    }
    
    /**
     * finds the size of the tree
     * 
     * @return the size of the tree (integer)
     */
    public int size()
    {
    	return tree.size();
    }

    /**
     * clears the tree
     */
    public void clear() 
    {
    	tree.clear();
    }
    
    public SongInterface get(int index)
    {
    	return tree.get(index);
    }
    

    public void setIterationStartPoint(Comparable<T> startPoint) {	
    }

    public Iterator<T> iterator() 
    {
	
	return java.util.Arrays.asList(value, value, value).iterator();
    }
}
