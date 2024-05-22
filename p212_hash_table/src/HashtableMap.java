import java.util.LinkedList;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.Assertions;

/**
 * Represents a collection that maps keys to values,
 * in which duplicate keys are not allowed (each key maps to exactly one value).
 */
public class HashtableMap<KeyType, ValueType> implements MapADT<KeyType, ValueType> {
	protected class Pair {

		public KeyType key;
		public ValueType value;

		public Pair(KeyType key, ValueType value) {
			this.key = key;
			this.value = value;
		}

	}

	private int capacity;


	protected LinkedList<Pair>[] table;

	@SuppressWarnings("unchecked")
	public HashtableMap(int capacity) {
		this.capacity = capacity;
		table = new LinkedList[capacity];
	}

	@SuppressWarnings("unchecked")
	public HashtableMap() {
		capacity = 64;
		table = new LinkedList[capacity];
	}

	// hash function: |key.hashCode()| % capacity

	 /**
     * Adds a new key,value pair/mapping to this collection.
     * @param key the key of the key,value pair
     * @param value the value that key maps to
     * @throws IllegalArgumentException if key already maps to a value
     * @throws NullPointerException if key is null
     */
	@Override
	public void put(KeyType key, ValueType value) throws IllegalArgumentException 
	{
		//check that input is valid, throw exceptions if not
		if (key == null) 
		{
			throw new NullPointerException("null key");
		}

		if (containsKey(key)) 
		{
			throw new IllegalArgumentException("key already in hash table");
		}

		//calculate load factor 
		int count = getSize() + 1;
		
		double loadFactor = (double)count / (double)capacity;
		

		//recursively check load factor and grow table if necessary; add element to table
		if (loadFactor < 0.8) 
		{
			int hash = Math.abs(key.hashCode()) % capacity;
			//System.out.println(hash);
		    if(table[hash] == null) {
		        table[hash] = new LinkedList<Pair>();
		    }
		    table[hash].add(new Pair((KeyType) key, (ValueType) value));
		} 
		else 
		{
			growthHelper();
			put(key, value);
		}
	}

	/**
	 * helper method to resize hash table to double capacity if load factor is reached or exceeded
	 */
	@SuppressWarnings("unchecked")
	private void growthHelper() {
		// create new table with double capacity
		int newCap = capacity * 2;

		LinkedList<Pair>[] newTable = new LinkedList[newCap];

		//rehash
		for (LinkedList<Pair> bucket : table)
		{
			if (bucket != null && !bucket.isEmpty()) 
			{
				//add key-value pairs to new table according to rehashed index
				for (Pair p : bucket) 
				{	
					int hash = Math.abs(p.key.hashCode()) % newCap;
				    if(newTable[hash] == null) 
				    {
				        newTable[hash] = new LinkedList<Pair>();
				    }
				    newTable[hash].add(p);
				}
			}
		}
		
		//assign this new table to the object's table and change capacity
		capacity = newCap;
		System.out.println("new capacity from helper: " + newCap);
		table = newTable;

	}

	/**
     * Checks whether a key maps to a value in this collection.
     * @param key the key to check
     * @return true if the key maps to a value, and false is the
     *         key doesn't map to a value
     */
	@Override
	public boolean containsKey(KeyType key) 
	{
		for (LinkedList<Pair> bucket : table)
		{
			if (bucket != null) 
			{
				for (Pair p : bucket) 
				{
					if (p.key.equals(key)) 
					{
						return true;
					}
				}
			}
		}
		return false;
	}

	/**
     * Retrieves the specific value that a key maps to.
     * @param key the key to look up
     * @return the value that key maps to
     * @throws NoSuchElementException when key is not stored in this
     *         collection
     */
	@Override
	public ValueType get(KeyType key) throws NoSuchElementException 
	{
		//check if key is already in hashtable; throw exception if so
		if(!containsKey(key))
		{
			throw new NoSuchElementException("key not in collection");
		}
		
		//retrieve key-value pair
		for (LinkedList<Pair> bucket : table)
		{
			if (bucket != null) 
			{
				for (Pair p : bucket) 
				{
					if (p.key.equals(key)) 
					{
						return p.value;
					}
				}
			}
		}
		return null;
	}
	
	/**
     * Remove the mapping for a key from this collection.
     * @param key the key whose mapping to remove
     * @return the value that the removed key mapped to
     * @throws NoSuchElementException when key is not stored in this
     *         collection
     */
	@Override
	public ValueType remove(KeyType key) throws NoSuchElementException 
	{
		//check that key-value pair is in hashtable; throw exception if not
		if(!containsKey(key))
		{
			throw new NoSuchElementException("key not in collection");
		}
		
		//the key-value pair to be returned
		ValueType v = get(key);
		
		//traverse table and remove key-value pair
		for (LinkedList<Pair> bucket : table)
		{
			if(bucket != null)
			{
				for(Pair p : bucket)
				{
					if(p.key.equals(key))
					{
						bucket.remove(p);
					}
				}
			}
		}
		
		return v;
	}

	/**
     * Removes all key,value pairs from this collection.
     */
	@Override
	public void clear() 
	{
		for (LinkedList<Pair> bucket : table)
		{
			if(bucket != null)
			{
				bucket.clear();
			}
		}
	}

	 /**
     * Retrieves the number of keys stored in this collection.
     * @return the number of keys stored in this collection
     */
	@Override
	public int getSize() {
		int size = 0;
	    for (LinkedList<Pair> bucket : table) 
	    {
	        if (bucket != null) {
	            size += bucket.size();
	        }
	    }
	    return size;
	}

	/**
     * Retrieves this collection's capacity.
     * @return the size of the underlying array for this collection
     */
	@Override
	public int getCapacity() 
	{
		return capacity;
	}

	
	/**
	 * tests that put() method works as expected
	 */
	@Test
	public void testPut() 
	{
		HashtableMap<String, Integer> map = new HashtableMap<>(5);

		// Test adding a new key-value pair
		map.put("apple", 1);
		LinkedList<HashtableMap<String, Integer>.Pair> bucket = map.table[0];
		//Assertions.assertEquals(1, bucket.size());
		Assertions.assertEquals("apple", bucket.get(0).key);
		Assertions.assertEquals(1, bucket.get(0).value);

		// Test adding a null key
		Assertions.assertThrows(NullPointerException.class, () -> map.put(null, 2));

		// Test adding an existing key
		Assertions.assertThrows(IllegalArgumentException.class, () -> map.put("apple", 3));
	}

	/**
	 * tests that containsKey() method works as expected
	 */
	@Test
	public void testContainsKey() {
		HashtableMap<String, Integer> map = new HashtableMap<>();

		// Test with an empty map
		Assertions.assertFalse(map.containsKey("apple"));

		// Add some key-value pairs
		map.put("apple", 1);
		map.put("banana", 2);
		map.put("cherry", 3);

		// Test with existing keys
		Assertions.assertTrue(map.containsKey("apple"));
		Assertions.assertTrue(map.containsKey("banana"));
		Assertions.assertTrue(map.containsKey("cherry"));

		// Test with a non-existent key
		Assertions.assertFalse(map.containsKey("orange"));

		// Test with null key
		Assertions.assertFalse(map.containsKey(null));
	}

	/**
	 * tests that get() method works as expected
	 */
	@Test
	public void testGet() {
		HashtableMap<String, Integer> map = new HashtableMap<>();

		// Test with an empty map
		Assertions.assertThrows(NoSuchElementException.class, () -> map.get("apple"));

		// Add some key-value pairs
		map.put("apple", 1);
		map.put("banana", 2);
		map.put("cherry", 3);

		// Test with existing keys
		Assertions.assertEquals(1, map.get("apple"));
		Assertions.assertEquals(2, map.get("banana"));
		Assertions.assertEquals(3, map.get("cherry"));

		// Test with a non-existent key
		Assertions.assertThrows(NoSuchElementException.class, () -> map.get("orange"));

		// Test with null key
		Assertions.assertThrows(NoSuchElementException.class, () -> map.get(null));
	}

	/**
	 * tests that remove() method works as expected
	 */
	@Test
	public void testRemove() {
		HashtableMap<String, Integer> map = new HashtableMap<>();

		// Test with an empty map
		Assertions.assertThrows(NoSuchElementException.class, () -> map.remove("apple"));

		// Add some key-value pairs
		map.put("apple", 1);
		map.put("banana", 2);
		map.put("cherry", 3);

		// Test removing an existing key
		Assertions.assertEquals(1, map.remove("apple"));
		Assertions.assertFalse(map.containsKey("apple"));

		// Test removing a non-existent key
		Assertions.assertThrows(NoSuchElementException.class, () -> map.remove("orange"));

		// Test removing a null key
		Assertions.assertThrows(NoSuchElementException.class, () -> map.remove(null));

		// Test removing the remaining keys
		Assertions.assertEquals(2, map.remove("banana"));
		Assertions.assertEquals(3, map.remove("cherry"));
		Assertions.assertTrue(map.getSize() == 0);
	}

	/**
	 * tests that clear() method works as expected
	 */
	@Test
	public void testClear() {
		HashtableMap<String, Integer> map = new HashtableMap<>();

		// Add some key-value pairs
		map.put("apple", 1);
		map.put("banana", 2);
		map.put("cherry", 3);

		// Test removing an existing key
		map.clear();

		// Test removing the remaining keys

		Assertions.assertTrue(map.getSize() == 0);
	}

}
