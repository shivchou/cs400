// === CS400 File Header Information ===
// Name: Shivani Choudhary
// Email: choudhary26@wisc.edu
// Group and Team: <your group name: two letters, and team color>
// Group TA: <name of your group's ta>
// Lecturer: Florian Heimerl
// Notes to Grader: <optional extra notes>

import java.util.PriorityQueue;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.NoSuchElementException;

/**
 * This class extends the BaseGraph data structure with additional methods for
 * computing the total cost and list of node data along the shortest path
 * connecting a provided starting to ending nodes. This class makes use of
 * Dijkstra's shortest path algorithm.
 */
public class DijkstraGraph<NodeType, EdgeType extends Number>
        extends BaseGraph<NodeType, EdgeType>
        implements GraphADT<NodeType, EdgeType> {

    /**
     * While searching for the shortest path between two nodes, a SearchNode
     * contains data about one specific path between the start node and another
     * node in the graph. The final node in this path is stored in its node
     * field. The total cost of this path is stored in its cost field. And the
     * predecessor SearchNode within this path is referened by the predecessor
     * field (this field is null within the SearchNode containing the starting
     * node in its node field).
     *
     * SearchNodes are Comparable and are sorted by cost so that the lowest cost
     * SearchNode has the highest priority within a java.util.PriorityQueue.
     */
    protected class SearchNode implements Comparable<SearchNode> {
        public Node node;
        public double cost;
        public SearchNode predecessor;

        public SearchNode(Node node, double cost, SearchNode predecessor) {
            this.node = node;
            this.cost = cost;
            this.predecessor = predecessor;
        }

        public int compareTo(SearchNode other) {
            if (cost > other.cost)
                return +1;
            if (cost < other.cost)
                return -1;
            return 0;
        }
    }

    /**
     * Constructor that sets the map that the graph uses.
     */
    public DijkstraGraph() {
        super(new PlaceholderMap<>());
    }

    /**
     * This helper method creates a network of SearchNodes while computing the
     * shortest path between the provided start and end locations. The
     * SearchNode that is returned by this method is represents the end of the
     * shortest path that is found: it's cost is the cost of that shortest path,
     * and the nodes linked together through predecessor references represent
     * all of the nodes along that shortest path (ordered from end to start).
     *
     * @param start the data item in the starting node for the path
     * @param end   the data item in the destination node for the path
     * @return SearchNode for the final end node within the shortest path
     * @throws NoSuchElementException when no path from start to end is found
     *                                or when either start or end data do not
     *                                correspond to a graph node
     */
    protected SearchNode computeShortestPath(NodeType start, NodeType end) {
        // implement in step 5.3
    	
    	//check if nodes are in graph
    	if(!containsNode(start) || !containsNode(end)) {
            throw new NoSuchElementException("no path from start to end");
          }
    	
    	//check if nodes are attached to graph
    	if(this.nodes.get(start).edgesLeaving.isEmpty() || 
    			this.nodes.get(end).edgesEntering.isEmpty())
    	{
    		throw new NoSuchElementException("no path");
    	}
    	SearchNode starter = new SearchNode(this.nodes.get(start), 0.0, null);
    	PriorityQueue<SearchNode> pq = new PriorityQueue<SearchNode>();
        
        // Create a map to keep track of predecessor nodes for constructing the path
        PlaceholderMap<NodeType, SearchNode> explored = new PlaceholderMap<NodeType, SearchNode>();
        
        pq.add(starter);
        
        System.out.println("starter node: " + starter.node.data);
        
        
        while(!pq.isEmpty())
        {
        	SearchNode current = pq.poll();
        	if(!explored.containsKey(current.node.data))
        	{
        		System.out.println("top of loop: " + current.node.data);
            	
            	List<Edge> edges = current.node.edgesLeaving;
            	
            	//add edges as SearchNodes to priority queue
            	for(Edge e : edges)
            	{
            		SearchNode add = new SearchNode(e.successor, current.cost + e.data.doubleValue(), current);
            		System.out.println("edge add: " + e.successor.data);
            		pq.add(add);
            	}
            	
            	//add node to set of explored nodes on path
            	explored.put(current.node.data, current);
            	
            	//check if this node is the end node
            	if(current.node.data.equals(end))
            	{
            		return current;
            	}
        	}
        }
       throw new NoSuchElementException("unreachable end");
    }

    /**
     * Returns the list of data values from nodes along the shortest path
     * from the node with the provided start value through the node with the
     * provided end value. This list of data values starts with the start
     * value, ends with the end value, and contains intermediary values in the
     * order they are encountered while traversing this shorteset path. This
     * method uses Dijkstra's shortest path algorithm to find this solution.
     *
     * @param start the data item in the starting node for the path
     * @param end   the data item in the destination node for the path
     * @return list of data item from node along this shortest path
     */
    public List<NodeType> shortestPathData(NodeType start, NodeType end) {
    	SearchNode n = computeShortestPath(start, end);
    	
		
		  List<SearchNode> init = new ArrayList<SearchNode>();
		  
		  SearchNode pred = n.predecessor;
		  
		  System.out.println("pred data initial list: " + pred.node.data);
		  
		  init.add(n);
		  
		  while(pred != null) 
		  { 
			  init.add(pred); 
			  pred = pred.predecessor;
		  }
		  
		  for(SearchNode s : init) { System.out.println("shortest path list initial: "
		  + s.node.data); }
		  
		  List<NodeType> result = new ArrayList<>();
		  
		  for(int i = init.size() - 1; i >= 0; i--)
		  {
			  result.add(init.get(i).node.data); 
		  }
		 
    	
    	return result;
	}

    /**
     * Returns the cost of the path (sum over edge weights) of the shortest
     * path freom the node containing the start data to the node containing the
     * end data. This method uses Dijkstra's shortest path algorithm to find
     * this solution.
     *
     * @param start the data item in the starting node for the path
     * @param end   the data item in the destination node for the path
     * @return the cost of the shortest path between these nodes
     */
    public double shortestPathCost(NodeType start, NodeType end) {
        SearchNode n = computeShortestPath(start, end);
        return n.cost;
    }

    // TODO: implement 3+ tests in step 4.1

    @Test 
    public void testLectureCase()
    {
    	DijkstraGraph<String, Double> graph = new DijkstraGraph<String, Double>();
    	graph.insertNode("a");
        graph.insertNode("b");
        graph.insertNode("c");
        graph.insertNode("d");
        graph.insertNode("e");
        graph.insertNode("f");
        graph.insertNode("g");
        graph.insertNode("h");
        
        graph.insertEdge("a", "b", 4.0);
        graph.insertEdge("a", "c", 2.0);
        graph.insertEdge("b", "e", 10.0);
        graph.insertEdge("b", "d", 1.0);
        graph.insertEdge("c", "d", 5.0);
        graph.insertEdge("a", "e", 15.0);
        graph.insertEdge("d", "f", 0.0);
        graph.insertEdge("f", "d", 2.0);
        graph.insertEdge("a", "b", 4.0);
        graph.insertEdge("a", "c", 2.0);
        graph.insertEdge("f", "h", 4.0);
        graph.insertEdge("g", "h", 4.0);
        
        List<String> path = graph.shortestPathData("a", "e");
        
        for(String s : path)
        {
        	System.out.println(s);
        }
        
        System.out.println("path size: " + path.size());
        
        Assertions.assertTrue(path.size() == 3);
    }

    @Test
    public void differentStartAndEnd()
    {
    	DijkstraGraph<String, Double> graph = new DijkstraGraph<String, Double>();
    	graph.insertNode("a");
        graph.insertNode("b");
        graph.insertNode("c");
        graph.insertNode("d");
        graph.insertNode("e");
        graph.insertNode("f");
        graph.insertNode("g");
        graph.insertNode("h");
        
        graph.insertEdge("a", "b", 4.0);
        graph.insertEdge("a", "c", 2.0);
        graph.insertEdge("b", "e", 10.0);
        graph.insertEdge("b", "d", 1.0);
        graph.insertEdge("c", "d", 5.0);
        graph.insertEdge("a", "e", 15.0);
        graph.insertEdge("d", "f", 0.0);
        graph.insertEdge("f", "d", 2.0);
        graph.insertEdge("a", "b", 4.0);
        graph.insertEdge("a", "c", 2.0);
        graph.insertEdge("f", "h", 4.0);
        graph.insertEdge("g", "h", 4.0);
        
        List<String> path = graph.shortestPathData("a", "h");
        
        Assertions.assertTrue(path.size() == 5);
    }

    @Test
    public void noPathExists()
    {
    	DijkstraGraph<Integer, Double> graph = new DijkstraGraph<Integer, Double>();
    	//BaseGraph<Integer, Double> graph = new BaseGraph<>(new PlaceholderMap<>());
        graph.insertNode(1);
        graph.insertNode(2);
        graph.insertNode(3);
        graph.insertNode(4);
      //  graph.insertEdge(1, null, 1.0);
        graph.insertEdge(2, 3, 2.0);
        graph.insertEdge(3, 4, 3.0);
        
        //graph.computeShortestPath(1,4);
        Assertions.assertThrows(NoSuchElementException.class, () -> graph.computeShortestPath(1,4));
    }
}
