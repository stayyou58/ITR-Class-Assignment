// todo: student 1 id & name, student 2 id & name
// todo: see requirements in class slides

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Stack;

public class Ride2023 {
	
	// Queue is from https://algs4.cs.princeton.edu/code/edu/princeton/cs/algs4/Queue.java.html
	// JavaDoc https://algs4.cs.princeton.edu/code/javadoc/edu/princeton/cs/algs4/Queue.html
	public class Queue<Item> implements Iterable<Item> {
	    private Node<Item> first;    // beginning of queue
	    private Node<Item> last;     // end of queue
	    private int n;               // number of elements on queue

	    // helper linked list class
	    private static class Node<Item> {
	        private Item item;
	        private Node<Item> next;
	    }

	    /**
	     * Initializes an empty queue.
	     */
	    public Queue() {
	        first = null;
	        last  = null;
	        n = 0;
	    }

	    /**
	     * Returns true if this queue is empty.
	     *
	     * @return {@code true} if this queue is empty; {@code false} otherwise
	     */
	    public boolean isEmpty() {
	        return first == null;
	    }

	    /**
	     * Returns the number of items in this queue.
	     *
	     * @return the number of items in this queue
	     */
	    public int size() {
	        return n;
	    }

	    /**
	     * Returns the item least recently added to this queue.
	     *
	     * @return the item least recently added to this queue
	     * @throws NoSuchElementException if this queue is empty
	     */
	    public Item peek() {
	        if (isEmpty()) throw new NoSuchElementException("Queue underflow");
	        return first.item;
	    }

	    /**
	     * Adds the item to this queue.
	     *
	     * @param  item the item to add
	     */
	    public void enqueue(Item item) {
	        Node<Item> oldlast = last;
	        last = new Node<Item>();
	        last.item = item;
	        last.next = null;
	        if (isEmpty()) first = last;
	        else           oldlast.next = last;
	        n++;
	    }

	    /**
	     * Removes and returns the item on this queue that was least recently added.
	     *
	     * @return the item on this queue that was least recently added
	     * @throws NoSuchElementException if this queue is empty
	     */
	    public Item dequeue() {
	        if (isEmpty()) throw new NoSuchElementException("Queue underflow");
	        Item item = first.item;
	        first = first.next;
	        n--;
	        if (isEmpty()) last = null;   // to avoid loitering
	        return item;
	    }

	    /**
	     * Returns a string representation of this queue.
	     *
	     * @return the sequence of items in FIFO order, separated by spaces
	     */
	    public String toString() {
	        StringBuilder s = new StringBuilder();
	        for (Item item : this) {
	            s.append(item);
	            s.append(' ');
	        }
	        return s.toString();
	    }

	    /**
	     * Returns an iterator that iterates over the items in this queue in FIFO order.
	     *
	     * @return an iterator that iterates over the items in this queue in FIFO order
	     */
	    public Iterator<Item> iterator()  {
	        return new LinkedIterator(first);
	    }

	    // a linked-list iterator
	    private class LinkedIterator implements Iterator<Item> {
	        private Node<Item> current;

	        public LinkedIterator(Node<Item> first) {
	            current = first;
	        }

	        public boolean hasNext() {
	            return current != null;
	        }

	        public Item next() {
	            if (!hasNext()) throw new NoSuchElementException();
	            Item item = current.item;
	            current = current.next;
	            return item;
	        }
	    }

	}
	
	//Bag is modified from https://algs4.cs.princeton.edu/code/edu/princeton/cs/algs4/Bag.java.html
    //JavaDoc https://algs4.cs.princeton.edu/code/javadoc/edu/princeton/cs/algs4/Bag.html
    public class Bag<Item> implements Iterable<Item> {
        private Node<Item> first;    // beginning of bag
        private int n;               // number of elements in bag

        // helper linked list class
        private static class Node<Item> {
            private Item item;
            private Node<Item> next;
        }

        /**
         * Initializes an empty bag.
         */
        public Bag() {
            first = null;
            n = 0;
        }

        /**
         * Returns true if this bag is empty.
         *
         * @return {@code true} if this bag is empty;
         *         {@code false} otherwise
         */
        public boolean isEmpty() {
            return first == null;
        }

        /**
         * Returns the number of items in this bag.
         *
         * @return the number of items in this bag
         */
        public int size() {
            return n;
        }

        /**
         * Adds the item to this bag.
         *
         * @param  item the item to add to this bag
         */
        public void add(Item item) {
            Node<Item> oldfirst = first;
            first = new Node<Item>();
            first.item = item;
            first.next = oldfirst;
            n++;
        }
        
        // Removes the item from this bag
        public void remove(Item item) {
        	// if first
        	if(first.item == item) {
        		first = first.next;
        		n--;
        	}else{
        		// find item
        		Node<Item> current;
        		for(current = first; current.next != null; current = current.next) {
        			if(current.next.item == item) {
        				current.next = current.next.next;
        				n--;
        				return;
        			}
        		}
        	}
        	
        }


        /**
         * Returns an iterator that iterates over the items in this bag in arbitrary order.
         *
         * @return an iterator that iterates over the items in this bag in arbitrary order
         */
        public Iterator<Item> iterator()  {
            return new LinkedIterator(first);  
        }

        // an iterator, doesn't implement remove() since it's optional
        private class LinkedIterator implements Iterator<Item> {
            private Node<Item> current;

            public LinkedIterator(Node<Item> first) {
                current = first;
            }

            public boolean hasNext()  { return current != null;                     }
            public void remove()      { throw new UnsupportedOperationException();  }

            public Item next() {
                if (!hasNext()) throw new NoSuchElementException();
                Item item = current.item;
                current = current.next; 
                return item;
            }
        }

    }
	
	
    // Digraph is modified from https://algs4.cs.princeton.edu/code/edu/princeton/cs/algs4/Digraph.java.html
    // JavaDoc https://algs4.cs.princeton.edu/code/javadoc/edu/princeton/cs/algs4/Digraph.html
	public class Digraph {
	    private static final String NEWLINE = System.getProperty("line.separator");

	    private final int V;           // number of vertices in this digraph
	    private int E;                 // number of edges in this digraph
	    private Bag<Integer>[] adj;    // adj[v] = adjacency list for vertex v
	    private int[] indegree;        // indegree[v] = indegree of vertex v

	    /**
	     * Initializes an empty digraph with <em>V</em> vertices.
	     *
	     * @param  V the number of vertices
	     * @throws IllegalArgumentException if {@code V < 0}
	     */
	    public Digraph(int V) {
	        if (V < 0) throw new IllegalArgumentException("Number of vertices in a Digraph must be non-negative");
	        this.V = V;
	        this.E = 0;
	        indegree = new int[V];
	        adj = (Bag<Integer>[]) new Bag[V];
	        for (int v = 0; v < V; v++) {
	            adj[v] = new Bag<Integer>();
	        }
	    }

	    /**
	     * Initializes a new digraph that is a deep copy of the specified digraph.
	     *
	     * @param  G the digraph to copy
	     * @throws IllegalArgumentException if {@code G} is {@code null}
	     */
	    public Digraph(Digraph G) {
	        if (G == null) throw new IllegalArgumentException("argument is null");

	        this.V = G.V();
	        this.E = G.E();
	        if (V < 0) throw new IllegalArgumentException("Number of vertices in a Digraph must be non-negative");

	        // update indegrees
	        indegree = new int[V];
	        for (int v = 0; v < V; v++)
	            this.indegree[v] = G.indegree(v);

	        // update adjacency lists
	        adj = (Bag<Integer>[]) new Bag[V];
	        for (int v = 0; v < V; v++) {
	            adj[v] = new Bag<Integer>();
	        }

	        for (int v = 0; v < G.V(); v++) {
	            // reverse so that adjacency list is in same order as original
	            Stack<Integer> reverse = new Stack<Integer>();
	            for (int w : G.adj[v]) {
	                reverse.push(w);
	            }
	            for (int w : reverse) {
	                adj[v].add(w);
	            }
	        }
	    }

	    /**
	     * Returns the number of vertices in this digraph.
	     *
	     * @return the number of vertices in this digraph
	     */
	    public int V() {
	        return V;
	    }

	    /**
	     * Returns the number of edges in this digraph.
	     *
	     * @return the number of edges in this digraph
	     */
	    public int E() {
	        return E;
	    }


	    // throw an IllegalArgumentException unless {@code 0 <= v < V}
	    private void validateVertex(int v) {
	        if (v < 0 || v >= V)
	            throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V-1));
	    }

	    /**
	     * Adds the directed edge v→w to this digraph.
	     *
	     * @param  v the tail vertex
	     * @param  w the head vertex
	     * @throws IllegalArgumentException unless both {@code 0 <= v < V} and {@code 0 <= w < V}
	     */
	    public void addEdge(int v, int w) {
	        validateVertex(v);
	        validateVertex(w);
	        adj[v].add(w);
	        indegree[w]++;
	        E++;
	    }

	    /**
	     * Returns the vertices adjacent from vertex {@code v} in this digraph.
	     *
	     * @param  v the vertex
	     * @return the vertices adjacent from vertex {@code v} in this digraph, as an iterable
	     * @throws IllegalArgumentException unless {@code 0 <= v < V}
	     */
	    public Iterable<Integer> adj(int v) {
	        validateVertex(v);
	        return adj[v];
	    }

	    /**
	     * Returns the number of directed edges incident from vertex {@code v}.
	     * This is known as the <em>outdegree</em> of vertex {@code v}.
	     *
	     * @param  v the vertex
	     * @return the outdegree of vertex {@code v}
	     * @throws IllegalArgumentException unless {@code 0 <= v < V}
	     */
	    public int outdegree(int v) {
	        validateVertex(v);
	        return adj[v].size();
	    }

	    /**
	     * Returns the number of directed edges incident to vertex {@code v}.
	     * This is known as the <em>indegree</em> of vertex {@code v}.
	     *
	     * @param  v the vertex
	     * @return the indegree of vertex {@code v}
	     * @throws IllegalArgumentException unless {@code 0 <= v < V}
	     */
	    public int indegree(int v) {
	        validateVertex(v);
	        return indegree[v];
	    }

	    /**
	     * Returns the reverse of the digraph.
	     *
	     * @return the reverse of the digraph
	     */
	    public Digraph reverse() {
	        Digraph reverse = new Digraph(V);
	        for (int v = 0; v < V; v++) {
	            for (int w : adj(v)) {
	                reverse.addEdge(w, v);
	            }
	        }
	        return reverse;
	    }

	    /**
	     * Returns a string representation of the graph.
	     *
	     * @return the number of vertices <em>V</em>, followed by the number of edges <em>E</em>,
	     *         followed by the <em>V</em> adjacency lists
	     */
	    public String toString() {
	        StringBuilder s = new StringBuilder();
	        s.append(V + " vertices, " + E + " edges " + NEWLINE);
	        for (int v = 0; v < V; v++) {
	            s.append(String.format("%d: ", v));
	            for (int w : adj[v]) {
	                s.append(String.format("%d ", w));
	            }
	            s.append(NEWLINE);
	        }
	        return s.toString();
	    }

	}
	
	//BreadthFirstDirectedPaths is modified from https://algs4.cs.princeton.edu/code/edu/princeton/cs/algs4/BreadthFirstDirectedPaths.java.html
	//JavaDoc https://algs4.cs.princeton.edu/code/javadoc/edu/princeton/cs/algs4/BreadthFirstDirectedPaths.html
	public class BreadthFirstDirectedPaths {
	    private static final int INFINITY = Integer.MAX_VALUE;
	    private boolean[] marked;  // marked[v] = is there an s->v path?
	    private int[] edgeTo;      // edgeTo[v] = last edge on shortest s->v path
	    private int[] distTo;      // distTo[v] = length of shortest s->v path

	    /**
	     * Computes the shortest path from {@code s} and every other vertex in graph {@code G}.
	     * @param G the digraph
	     * @param s the source vertex
	     * @throws IllegalArgumentException unless {@code 0 <= v < V}
	     */
	    public BreadthFirstDirectedPaths(Digraph G, int s) {
	        marked = new boolean[G.V()];
	        distTo = new int[G.V()];
	        edgeTo = new int[G.V()];
	        for (int v = 0; v < G.V(); v++)
	            distTo[v] = INFINITY;
	        validateVertex(s);
	        bfs(G, s);
	    }

	    /**
	     * Computes the shortest path from any one of the source vertices in {@code sources}
	     * to every other vertex in graph {@code G}.
	     * @param G the digraph
	     * @param sources the source vertices
	     * @throws IllegalArgumentException if {@code sources} is {@code null}
	     * @throws IllegalArgumentException if {@code sources} contains no vertices
	     * @throws IllegalArgumentException unless each vertex {@code v} in
	     *         {@code sources} satisfies {@code 0 <= v < V}
	     */
	    public BreadthFirstDirectedPaths(Digraph G, Iterable<Integer> sources) {
	        marked = new boolean[G.V()];
	        distTo = new int[G.V()];
	        edgeTo = new int[G.V()];
	        for (int v = 0; v < G.V(); v++)
	            distTo[v] = INFINITY;
	        validateVertices(sources);
	        bfs(G, sources);
	    }

	    // BFS from single source
	    private void bfs(Digraph G, int s) {
	        Queue<Integer> q = new Queue<Integer>();
	        marked[s] = true;
	        distTo[s] = 0;
	        q.enqueue(s);
	        while (!q.isEmpty()) {
	            int v = q.dequeue();
	            for (int w : G.adj(v)) {
	                if (!marked[w]) {
	                    edgeTo[w] = v;
	                    distTo[w] = distTo[v] + 1;
	                    marked[w] = true;
	                    q.enqueue(w);
	                }
	            }
	        }
	    }

	    // BFS from multiple sources
	    private void bfs(Digraph G, Iterable<Integer> sources) {
	        Queue<Integer> q = new Queue<Integer>();
	        for (int s : sources) {
	            marked[s] = true;
	            distTo[s] = 0;
	            q.enqueue(s);
	        }
	        while (!q.isEmpty()) {
	            int v = q.dequeue();
	            for (int w : G.adj(v)) {
	                if (!marked[w]) {
	                    edgeTo[w] = v;
	                    distTo[w] = distTo[v] + 1;
	                    marked[w] = true;
	                    q.enqueue(w);
	                }
	            }
	        }
	    }

	    /**
	     * Is there a directed path from the source {@code s} (or sources) to vertex {@code v}?
	     * @param v the vertex
	     * @return {@code true} if there is a directed path, {@code false} otherwise
	     * @throws IllegalArgumentException unless {@code 0 <= v < V}
	     */
	    public boolean hasPathTo(int v) {
	        validateVertex(v);
	        return marked[v];
	    }

	    /**
	     * Returns the number of edges in a shortest path from the source {@code s}
	     * (or sources) to vertex {@code v}?
	     * @param v the vertex
	     * @return the number of edges in such a shortest path
	     *         (or {@code Integer.MAX_VALUE} if there is no such path)
	     * @throws IllegalArgumentException unless {@code 0 <= v < V}
	     */
	    public int distTo(int v) {
	        validateVertex(v);
	        return distTo[v];
	    }

	    /**
	     * Returns a shortest path from {@code s} (or sources) to {@code v}, or
	     * {@code null} if no such path.
	     * @param v the vertex
	     * @return the sequence of vertices on a shortest path, as an Iterable
	     * @throws IllegalArgumentException unless {@code 0 <= v < V}
	     */
	    public Iterable<Integer> pathTo(int v) {
	        validateVertex(v);

	        if (!hasPathTo(v)) return null;
	        Stack<Integer> path = new Stack<Integer>();
	        int x;
	        for (x = v; distTo[x] != 0; x = edgeTo[x])
	            path.push(x);
	        path.push(x);
	        return path;
	    }

	    // throw an IllegalArgumentException unless {@code 0 <= v < V}
	    private void validateVertex(int v) {
	        int V = marked.length;
	        if (v < 0 || v >= V)
	            throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V-1));
	    }

	    // throw an IllegalArgumentException if vertices is null, has zero vertices,
	    // or has a vertex not between 0 and V-1
	    private void validateVertices(Iterable<Integer> vertices) {
	        if (vertices == null) {
	            throw new IllegalArgumentException("argument is null");
	        }
	        int vertexCount = 0;
	        for (Integer v : vertices) {
	            vertexCount++;
	            if (v == null) {
	                throw new IllegalArgumentException("vertex is null");
	            }
	            validateVertex(v);
	        }
	        if (vertexCount == 0) {
	            throw new IllegalArgumentException("zero vertices");
	        }
	    }
	}
	
	// todo: complete this method to find ride, see details in class slides
	public Integer findRide(Digraph g, Bag<Integer> friendLocations, Integer yourLocation, Integer destination) {
		// write your code here

		return -1;
	}

	public static void main(String[] args) {
		Ride2023 rd = new Ride2023();
		
		Digraph dg = rd.new Digraph(16);
    	dg.addEdge(0, 12);
    	dg.addEdge(0, 13);
    	dg.addEdge(1, 0);
    	dg.addEdge(1, 3);
    	dg.addEdge(1, 14);
    	dg.addEdge(2, 0);
    	dg.addEdge(2, 7);
    	dg.addEdge(2, 8);
    	dg.addEdge(2, 11);
    	dg.addEdge(3, 11);
    	dg.addEdge(4, 1);
    	dg.addEdge(5, 11);
    	dg.addEdge(5, 13);
    	dg.addEdge(6, 7);
    	dg.addEdge(6, 12);
    	dg.addEdge(7, 0);
    	dg.addEdge(7, 15);
    	dg.addEdge(8, 1);
    	dg.addEdge(8, 15);
    	dg.addEdge(9, 12);
    	dg.addEdge(9, 15);
    	dg.addEdge(10, 4);
    	dg.addEdge(10, 5);
    	dg.addEdge(10, 6);
    	dg.addEdge(10, 12);
    	dg.addEdge(10, 14);
    	dg.addEdge(11, 2);
    	dg.addEdge(11, 5);
    	dg.addEdge(11, 6);
    	dg.addEdge(12, 6);
    	dg.addEdge(12, 7);
    	dg.addEdge(12, 9);
    	dg.addEdge(13, 6);
    	dg.addEdge(13, 10);
    	dg.addEdge(14, 10);
    	dg.addEdge(15, 2);
    	
    	Bag<Integer> friendLocations = rd.new Bag<Integer>();
    	friendLocations.add(3);
    	friendLocations.add(4);
    	friendLocations.add(5);
    	
    	System.out.print("Friend Locations: ");
		for(int i: friendLocations) {
			System.out.print(i+" ");
		}
		System.out.println();
		
		int destination = 15;
		System.out.println("Destination: " + destination);
    	
    	int friendForRide = rd.findRide(dg, friendLocations, 0, destination);
    	if (friendForRide == -1) {
    		System.out.print("No friend can give a ride on shortest path to " + destination + ".");
    	}else {
    		System.out.print("Friend located at " + friendForRide + " can give a ride on shortest path to " + destination + ".");
    	}
    	
    	System.out.println();
    	System.out.println();
    	
    	friendLocations = rd.new Bag<Integer>();
    	friendLocations.add(9);
    	friendLocations.add(7);
    	friendLocations.add(8);
    	
    	System.out.print("Friend Locations: ");
		for(int i: friendLocations) {
			System.out.print(i+" ");
		}
		System.out.println();
		
		destination = 15;
		System.out.println("Destination: " + destination);
    	
    	friendForRide = rd.findRide(dg, friendLocations, 0, destination);
    	if (friendForRide == -1) {
    		System.out.print("No friend can give a ride on shortest path to " + destination + ".");
    	}else {
    		System.out.print("Friend located at " + friendForRide + " can give a ride on shortest path to " + destination + ".");
    	}
    	
    	System.out.println();
    	System.out.println();
    	
    	friendLocations = rd.new Bag<Integer>();
    	friendLocations.add(7);
    	friendLocations.add(8);
    	friendLocations.add(9);
    	
    	System.out.print("Friend Locations: ");
		for(int i: friendLocations) {
			System.out.print(i+" ");
		}
		System.out.println();
		
		destination = 6;
		System.out.println("Destination: " + destination);
    	
    	friendForRide = rd.findRide(dg, friendLocations, 0, destination);
    	if (friendForRide == -1) {
    		System.out.print("No friend can give a ride on shortest path to " + destination + ".");
    	}else {
    		System.out.print("Friend located at " + friendForRide + " can give a ride on shortest path to " + destination + ".");
    	}
    	
    	System.out.println();
    	System.out.println();
    	
    	friendLocations = rd.new Bag<Integer>();
    	friendLocations.add(10);
    	friendLocations.add(11);
    	friendLocations.add(12);
    	
    	System.out.print("Friend Locations: ");
		for(int i: friendLocations) {
			System.out.print(i+" ");
		}
		System.out.println();
		
		destination = 14;
		System.out.println("Destination: " + destination);
    	
    	friendForRide = rd.findRide(dg, friendLocations, 0, destination);
    	if (friendForRide == -1) {
    		System.out.print("No friend can give a ride on shortest path to " + destination + ".");
    	}else {
    		System.out.print("Friend " + friendForRide + " can give a ride on shortest path to " + destination + ".");
    	}

	}

}
