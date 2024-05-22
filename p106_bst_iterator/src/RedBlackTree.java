import org.junit.jupiter.api.Test;
import static org.junit.Assert.*;

//import java.util.ArrayList;

//import org.junit.jupiter.api.Assertions.* ;
/**
 * Red-Black Search Tree implementation with a Node inner class for representing
 * the nodes of the tree. 
 * Extends the BinarySearchTree class.
 */
public class RedBlackTree<T extends Comparable<T>> extends BinarySearchTree<T>
{
	
	 /**
     * This class represents a node holding a single value within a red-black tree.
     * 
     * Extends Node class in BinarySearchTree, added field to indicate red/black.
     */
	protected static class RBTNode<T> extends Node<T> 
	{
	    public boolean isBlack = false;
	    public RBTNode(T data) { super(data); }
	    public RBTNode<T> getUp() { return (RBTNode<T>)this.up; }
	    public RBTNode<T> getDownLeft() { return (RBTNode<T>)this.down[0]; }
	    public RBTNode<T> getDownRight() { return (RBTNode<T>)this.down[1]; }
	}
	
	
	/**
	 * method to check if red-black tree properties are preserved after inserting a node.
	 * perform repair operations if:
	 * 		1. red node has red child
	 * 		2. root node is NOT black (is red)
	 * 		3. black height of tree is different among null leaves
	 * 
	 * @param red - the red node that was inserted into the tree
	 */
	protected void enforceRBTreePropertiesAfterInsert(RBTNode<T> red)
	{
		
		RBTNode<T> parent = (RBTNode<T>) red.up;

    //if root is red --> recolor to black
    if (parent == null) 
    {
        red.isBlack = true; 
        return;
    }

    // parent is black, no violation
    if (parent.isBlack == true) 
    {
        return; 
    }

    // red parent, red aunt
    RBTNode<T> grandparent = (RBTNode<T>) parent.up;
    RBTNode<T> aunt;
    if (grandparent.down[0] == parent) 
    {
        //aunt is right child, parent is left child
        aunt = (RBTNode<T>) grandparent.down[1];
    }
    else 
    {
        //aunt is left child, parent is right child
        aunt = (RBTNode<T>) grandparent.down[0];
    }
    if (aunt != null && aunt.isBlack == false) {
        parent.isBlack = true;
        aunt.isBlack = true;
        grandparent.isBlack = false;
        enforceRBTreePropertiesAfterInsert(grandparent); // Recursively fix the grandparent (not working as per tester4()?
        return;
    }

    // red parent, null/black aunt
    boolean parentIsRightChild = grandparent.down[1] == parent;
    boolean redIsRightChild = parent.down[1] == red;

    if(parentIsRightChild == redIsRightChild)
    {
    	//chain case
    	parent.isBlack = true;
    	grandparent.isBlack = false;
    	rotate(parent,grandparent);
    }
    else
    {
    	//zig-zag case 
    	red.isBlack = true;
    	grandparent.isBlack = false;
        rotate(red, parent);
        rotate(red, grandparent);
        
    }
   ( (RBTNode<T>)root).isBlack = true;
}
	
	
	//fix this error, then do javadocs and submit midweek
	/**
	 * override insert() in BinarySearchTree
	 * add a new (red) node to an existing red-black tree
	 * perform repair operations as needed by calling enforceRBTreePropertiesAfterInsert()
	 * 
	 * @param data - the data to insert
	 * @return true if the value was inserted, false if is was in the tree already
	 * 
	 * @throws NullPointerException when the provided data argument is null
	 */
	@Override
	public boolean insert(T data) throws NullPointerException {
		RBTNode<T> toInsert = new RBTNode<>(data);
		
		boolean added = this.insertHelper(toInsert); //insert node
		enforceRBTreePropertiesAfterInsert(toInsert); //check for violated properties + repair
		if (this.root == null) {
            // add first node to an empty tree
            root = toInsert;
            return true;
		}
		
		return added;
	}
	
	//Case 1 : z = red, parent of z = red, uncle of z = red
	
	
	/**
	 * test case where inserted node is red, parent is red, 
	 * 	and aunt is red
	 * should recolor incorrect nodes
	 * 
	 * @return true if colors match expected, false otherwise
	 */
	@Test
	public void tester1()
	{
		
		//make new tree
		RedBlackTree<Integer> tree = new RedBlackTree<Integer>();
		
        tree.insert(54);
        System.out.println(tree.toLevelOrderString());
        tree.insert(12);
        System.out.println(tree.toLevelOrderString());
        tree.insert(77);
        System.out.println(tree.toLevelOrderString());
        tree.insert(24);
        
        String levelOrder = tree.toLevelOrderString();
        System.out.println(levelOrder); //levelOrder = [ 12, 24, 54, 77]
        
        //autograder expects [ 54, 12, 77, 24]
        
        assertEquals("[ 54, 12, 77, 24 ]", levelOrder);
        
	}
	
	//Case 2 : z = red, parent of z = red, z = right child, uncle of z = black
	
	
	/**
	 * test case where inserted node is red, parent is red, 
	 * 	inserted node is right child, and aunt is black
	 * should recolor incorrect nodes
	 * 
	 * @return true if colors match expected, false otherwise
	 */
	@Test
	public  void tester2()
	{
		//make new tree
		RedBlackTree<Integer> tree = new RedBlackTree<Integer>();
		
		//insert nodes
		tree.insert(54);
        tree.insert(12);
        tree.insert(77);
        tree.insert(65);
        
       // System.out.println(tree.root.getDownRight().getDownLeft().isBlack);
        
        //check if colors match expected
        assertEquals(((RBTNode<T>)tree.root).isBlack, true);
        assertEquals(((RBTNode<T>)tree.root).getDownLeft().isBlack, true);
        assertEquals(((RBTNode<T>)tree.root).getDownRight().isBlack, true);
        assertEquals(((RBTNode<T>)tree.root).getDownRight().getDownLeft().isBlack, false);
        
        
		
	}
	
	//Case 3 : z = red, parent of z = red, z = left child, uncle of z = black
	
	/**
	 * test case where inserted node is red, parent is red, 
	 * 	inserted node is left child, and aunt is black
	 * should recolor incorrect nodes
	 * 
	 * @return true if colors match expected, false otherwise
	 */
	@Test
	public  void tester3()
	{
		//make new tree
		RedBlackTree<Integer> tree = new RedBlackTree<Integer>();
		
		//insert nodes
        tree.insert(34); // black
        tree.insert(9); // red
        tree.insert(63); // black
        tree.insert(7);
        
        //expected level order: [ 9, 7, 34, 63 ]
        
        System.out.println(((RBTNode<T>)tree.root).getDownRight().isBlack);
        
        //root's children are not red but should be - how to fix??????
	}
	
	@Test
	public  void tester4()
	{
		//make new tree
		RedBlackTree<Integer> tree = new RedBlackTree<Integer>();
		
		//insert nodes
        tree.insert(34); // black
        System.out.println(tree.toLevelOrderString());
        //tree.insert(9); // red
        tree.insert(63); // black
        System.out.println(tree.toLevelOrderString());
        tree.insert(37);
        
        //expected level order: [ 37, 34, 63 ]
        
        //System.out.println(tree.root.getDownLeft().isBlack); 
        
        System.out.println(tree.toLevelOrderString());
        
        //visited more nodes during traversal than there are keys in the tree
        
        //children not getting inserted????? root's children seem to be null
	}
	
	@Test
	public  void tester5()
	{
		//make new tree
		RedBlackTree<Integer> tree = new RedBlackTree<Integer>();
		
		//insert nodes
        tree.insert(34); 
        tree.insert(9); 
        tree.insert(3); 
        
        
        //expected level order: [ 9, 3, 34 ] 
        
        //System.out.println(tree.root.getDownRight().isBlack); --> root's children are null?
        
        System.out.println(tree.toLevelOrderString());
        
        assertEquals("[ 9, 3, 34 ]", tree.toLevelOrderString());
        
        
	}
	
	
	@Test
	public  void tester6()
	{
		//make new tree
		RedBlackTree<Integer> tree = new RedBlackTree<Integer>();
		
		//insert nodes
        tree.insert(32); 
        tree.insert(41); 
        tree.insert(57); 
        tree.insert(62);
        tree.insert(79);
        tree.insert(81);
        tree.insert(93);
        tree.insert(97);
        
        //expected level order: [ 62, 41, 81, 32, 57, 79, 93, 97 ]
        
        //System.out.println(tree.root.getDownLeft().isBlack); 
        
        System.out.println(tree.toLevelOrderString());
        
        
	}
	
	@Test
	public  void tester7()
	{
		//make new tree
		RedBlackTree<Integer> tree = new RedBlackTree<Integer>();
		
		//insert nodes
        tree.insert(93); 
        tree.insert(97); 
        tree.insert(87); 
        tree.insert(72);
        tree.insert(66);
        tree.insert(51);
        tree.insert(43);
        tree.insert(37);
        
        //expected level order: [ 72, 51, 93, 43, 66, 87, 97, 37 ] 
        
        //System.out.println(tree.root.getDownLeft().isBlack); 
        
        System.out.println(tree.toLevelOrderString());
        
        assertEquals("[ 72, 51, 93, 43, 66, 87, 97, 37 ]", tree.toLevelOrderString());
        
        
	}
}
