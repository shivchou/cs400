import java.util.Iterator;
import java.util.Stack;

import org.junit.jupiter.api.*;

import java.util.NoSuchElementException;

/**
 * create an instance of an Iterable red-black tree, to make sorting/searching easier
 * 
 * @param <T> the type of Comparable element
 */
public class IterableRedBlackTree<T extends Comparable<T>>
    extends RedBlackTree<T> implements IterableSortedCollection<T> 
{
	/**
	 * field to save the iteration start point
	 */
	private Comparable<T> iterationStart = new Comparable<T>() { public int compareTo(T o) {return -1;}};

    /**
     * sets the iteration start point to the given startPoint
     * 
     * @param startPoint - a Comparable value that sets the lowermost value included in the iteration stack
     */
    public void setIterationStartPoint(Comparable<T> startPoint) 
    {
    	iterationStart = startPoint;
    }

    /**
     * creates an Iterator object
     * 
     * @return an Iterator of type RBTIterator to iterate through this tree
     */
    public Iterator<T> iterator()
    {
    	return new RBTIterator<T>(root, iterationStart);
    }
    
    //This should cause any duplicate values 
    //to be stored within the left subtree of any ancestor nodes containing the same value.
    
    
    /**
     * Performs a naive insertion into a binary search tree: adding the new node
     * in a leaf position within the tree. After this insertion, no attempt is made
     * to restructure or balance the tree.
     * 
     * This override allows duplicate values to be added to the tree
     * 
     * 
     * @param node the new node to be inserted
     * @return true if the value was inserted, false if is was in the tree already
     * @throws NullPointerException when the provided node is null
     */
    @Override
    protected boolean insertHelper(Node<T> newNode) throws NullPointerException {
        if(newNode == null) throw new NullPointerException("new node cannot be null");

        
        if (this.root == null) {
            // add first node to an empty tree
            root = (RedBlackTree.RBTNode<T>) newNode;
            size++;
            return true;
        } else {
            // insert into subtree
            Node<T> current = this.root;
            while (true) {
                int compare = newNode.data.compareTo(current.data);
//                if (compare == 0) 
//                {
//                    return false;
//            }
                  if (compare <= 0) {
                    // insert in left subtree
                    if (current.down[0] == null) {
                        // empty space to insert into
                        current.down[0] = newNode;
                        newNode.up = current;
                        this.size++;
                        return true;
                    } else {
                        // no empty space, keep moving down the tree
                        current = current.down[0];
                    }
                } else {
                    // insert in right subtree
                    if (current.down[1] == null) {
                        // empty space to insert into
                        current.down[1] = newNode;
                        newNode.up = current;
                        this.size++;
                        return true;
                    } else {
                        // no empty space, keep moving down the tree
                        current = current.down[1]; 
                    }
                }
            }
        }
    }


    /**
     * creates an Iterator object to iterate through red-black tree
     * 
     * @param <R> the Comparable type this iterator uses 
     */
    private static class RBTIterator<R> implements Iterator<R> 
    {
    	/**
    	 * the start point passed from IterableRedBlackTree
    	 */
    	private Comparable<R> start;
    	
    	/**
    	 * the root node of the tree being iterated
    	 */
    	private Node<R> root;
    	
    	/**
    	 * the stack used to iterate through the tree
    	 */
    	private Stack<Node<R>> s;
    	
    
    	/**
    	 * creates a RBTIterator object
    	 * 
    	 * @param root - the root of the tree to be iterated
    	 * @param startPoint - the lowest value to be iterated through
    	 */
    	public RBTIterator(Node<R> root, Comparable<R> startPoint) 
    	{
    		//super();
    		this.root = root;
    		
    		if(startPoint != null)
    		{
    			start = startPoint;
    		}
    		
    		s = new Stack<Node<R>>();
    		
    		buildStackHelper(root);
    	}

    	/**
    	 * method to build a stack of ordered nodes to go through when iterating
    	 * 
    	 * @param node - the node to start at
    	 */
    	private void buildStackHelper(Node<R> node) 
    	{
    	
    		if(node == null) 
    		{
    			return;
    		}
    		
    		if(this.start.compareTo(node.data) > 0) 
    		{
    			buildStackHelper(node.down[1]);
    		} 
    		else 
    		{
    			buildStackHelper(node.down[1]);
    			this.s.add(node);
    			buildStackHelper(node.down[0]);
    		}
          }

		/**
		 * check if the iteration stack has another value
		 * 
		 * @return true if stack is not empty, false otherwise
		 */
		@Override
		public boolean hasNext() {
			// TODO Auto-generated method stub
			return !s.isEmpty();
		}

		/**
		 * returns the next node in the stack
		 * 
		 * @return the node on the top of the stack
		 */
		@Override
		public R next() {
			boolean b = hasNext();
    		
    		if(b == false)
    		{
    			throw new NoSuchElementException("empty stack");
    		}
    		
    		if(b && !s.isEmpty())
    		{
    			return s.pop().data;
    		}
    		
    		return null;
		}
            
           
    }
    
  	/**
  	 * test different Comparable types
  	 */
  	@Test
  	public static void testTypes()
  	{
  		IterableRedBlackTree<Integer> testInt = new IterableRedBlackTree<Integer>();
  		
  		IterableRedBlackTree<String> testString = new IterableRedBlackTree<String>();
  		
  		testInt.insert(1);
  		testInt.insert(2); 
  		testInt.insert(5);
  		testInt.insert(10);
  		testInt.insert(15);
  		
  		testString.insert("stark");
  		testString.insert("targaryen");
  		testString.insert("greyjoy");
  		testString.insert("baratheon");
  		testString.insert("lannister");
  		testString.insert("tully");
  		testString.insert("arryn");
  		testString.insert("tyrell");
  		testString.insert("martell");

		RBTIterator<Integer> intIt = (RBTIterator<Integer>) testInt.iterator();
  		
  		RBTIterator<String> strIt = (RBTIterator<String>) testString.iterator();
  		
  		Assertions.assertTrue(intIt.next() == 1);
  		
  		Assertions.assertTrue(strIt.next().equals("arryn"));
  		
  		
  	}

  	/**
  	 * check that duplicates are handled correctly
  	 */
  	@Test
  	public static void testDups()
  	{
  		IterableRedBlackTree<Integer> test = new IterableRedBlackTree<Integer>();
  		
  		//RBTNode<Integer> dup1 = new RBTNode<Integer>(2);
  		
  		test.insert(1);
  		test.insert(2); //duplicated
  		test.insert(5); //duplicated
  		test.insert(2);
  		test.insert(10);
  		test.insert(15);
  		
  		Node<Integer> dup = test.findNode(2);
  		Node<Integer> checkDup = dup.down[0];
  		
  		Assertions.assertTrue(checkDup.data == 2);
  		
  	}
  
  	/**
  	 * test iterators
  	 * 	a) some iterators created with specified start points - should iterate through elements ≥ startpoint ONLY
  	 * 	b) some iterators created without startpoints - should step through every element in this case
  	 */
  	@Test
  	public static void testIterator()
  	{
  		IterableRedBlackTree<Integer> test = new IterableRedBlackTree<Integer>();
  		Iterator<Integer> it = test.iterator(); //returns new RBTIterator
  		
  		test.insert(1);
  		test.insert(2); 
  		test.insert(5);
  		test.insert(10);
  		test.insert(15);
  		
  		Assertions.assertTrue(it.next() == 1);
  		
  		test.setIterationStartPoint(5);
  		
  		Assertions.assertTrue(it.next() == 10);
  		
  	}
  	
  	/**
  	 * run all tests
  	 * 
  	 * @param args
  	 */
  	public static void main(String[] args)
  	{
  		testTypes();
  		
  		testDups();
  		
  		testIterator();
  	}

}
