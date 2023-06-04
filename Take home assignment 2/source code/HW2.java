// TODO: student id & name
// You are only allowed to add or edit code inside the three methods: waysToWin, findPlansForRecruitment, top3Plans

import java.util.*;

public class HW2 {
	public static class Bag<Item> implements Iterable<Item> {
		//Modified from Bag from https://algs4.cs.princeton.edu/code/edu/princeton/cs/algs4/Bag.java.html
		private Node<Item> first;    // beginning of bag
		private int n;               // number of elements in bag
		// helper linked list class
		private static class Node<Item> {
			private Item item;
			private Node<Item> next;
		}
		/* Initializes an empty bag.*/
		public Bag() {
			first = null;
			n = 0;
		}
		/* Returns true if this bag is empty.*/
		public boolean isEmpty() {
			return first == null;
		}
		/* Returns the number of items in this bag.*/
		public int size() {
			return n;
		}
		/* Adds the item to this bag.*/
		public void add(Item item) {
			Node<Item> oldfirst = first;
			first = new Node<Item>();
			first.item = item;
			first.next = oldfirst;
			n++;
		}
		/* Returns an iterator that iterates over the items in this bag in arbitrary order.*/
		public Iterator<Item> iterator()  {
			return new LinkedIterator(first);
		}
		private class LinkedIterator implements Iterator<Item> {
			private Node<Item> current;
			public LinkedIterator(Node<Item> first) {
				current = first;
			}
			public boolean hasNext()  {
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
	public static class Graph {
		private static final String NEWLINE = System.getProperty("line.separator");		
		private final int V;
		private int E;
		private Bag<Integer>[] adj;
		private String[] heros;

		/**
		 * Initializes an empty graph with {@code V} vertices and 0 edges.
		 * param V the number of vertices
		 *
		 * @param  V number of vertices
		 * @throws IllegalArgumentException if {@code V < 0}
		 */
		public Graph(int V) {
			if (V < 0) throw new IllegalArgumentException("Number of vertices must be non-negative");
			this.V = V;
			this.E = 0;
			adj = (Bag<Integer>[]) new Bag[V];
			for (int v = 0; v < V; v++) {
				adj[v] = new Bag<Integer>();
			}
		}

		/**
		 * Initializes a random edge-weighted graph with V vertices and E edges.
		 */
		public Graph(int V, int E, int[] edgeweights, String[] heros) {
			this(V);
			if (E < 0) throw new IllegalArgumentException("Number of edges must be non-negative");
			for(int i = 0; i < E * 2; i += 2){
				int v = edgeweights[i];
				int w = edgeweights[i+1];
				addEdge(v, w);
			}
			this.heros = heros;
		}

		/**
		 * Initializes a new graph that is a deep copy of {@code G}.
		 *
		 * @param  G the graph to copy
		 * @throws IllegalArgumentException if {@code G} is {@code null}
		 */
		public Graph(Graph G) {
			this.V = G.V();
			this.E = G.E();
			if (V < 0) throw new IllegalArgumentException("Number of vertices must be non-negative");

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
		 * Returns the number of vertices in this graph.
		 */
		public int V() {
			return V;
		}
		/**
		 * Returns the number of edges in this graph.
		 */
		public int E() {
			return E;
		}
		/* Returns the list of hero's attribute at the village in this edge-weighted graph. */
		public String[] heros() {
			return heros;
		}
		// throw an IllegalArgumentException unless {@code 0 <= v < V}
		private void validateVertex(int v) {
			if (v < 0 || v >= V)
				throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V-1));
		}
		/**
		 * Adds the undirected edge v-w to this graph.
		 */
		public void addEdge(int v, int w) {
			validateVertex(v);
			validateVertex(w);
			E++;
			adj[v].add(w);
			adj[w].add(v);
		}
		/**
		 * Returns the vertices adjacent to vertex {@code v}.
		 */
		public Iterable<Integer> adj(int v) {
			validateVertex(v);
			return adj[v];
		}
		/**
		 * Returns the degree of vertex {@code v}.
		 */
		public int degree(int v) {
			validateVertex(v);
			return adj[v].size();
		}
		/**
		 * Returns a string representation of this graph.
		 */
		public String toString() {
			StringBuilder s = new StringBuilder();
			s.append(V + " vertices, " + E + " edges " + NEWLINE);
			for (int v = 0; v < V; v++) {
				s.append(v + ": ");
				for (int w : adj[v]) {
					s.append(w + " ");
				}
				s.append(NEWLINE);
			}
			return s.toString();
		} 
	}
	public static class Queue<Item> implements Iterable<Item> {
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
		 */
		public boolean isEmpty() {
			return first == null;
		}
		/**
		 * Returns the number of items in this queue.
		 */
		public int size() {
			return n;
		}
		/**
		 * Returns the item least recently added to this queue.
		 */
		public Item peek() {
			if (isEmpty()) throw new NoSuchElementException("Queue underflow");
			return first.item;
		}
		/**
		 * Adds the item to this queue.
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
	public static class BreadthFirstPaths {
		private static final int INFINITY = Integer.MAX_VALUE;
		private boolean[] marked;  // marked[v] = is there an s-v path
		private int[] edgeTo;      // edgeTo[v] = previous edge on shortest s-v path
		private int[] distTo;      // distTo[v] = number of edges shortest s-v path

		/**
		 * Computes the shortest path between the source vertex {@code s}
		 * and every other vertex in the graph {@code G}.
		 */
		public BreadthFirstPaths(Graph G, int s) {
			marked = new boolean[G.V()];
			distTo = new int[G.V()];
			edgeTo = new int[G.V()];
			validateVertex(s);
			bfs(G, s);

			assert check(G, s);
		}

		/**
		 * Computes the shortest path between any one of the source vertices in {@code sources}
		 * and every other vertex in graph {@code G}.
		 */
		public BreadthFirstPaths(Graph G, Iterable<Integer> sources) {
			marked = new boolean[G.V()];
			distTo = new int[G.V()];
			edgeTo = new int[G.V()];
			for (int v = 0; v < G.V(); v++)
				distTo[v] = INFINITY;
			validateVertices(sources);
			bfs(G, sources);
		}


		// breadth-first search from a single source
		private void bfs(Graph G, int s) {
			Queue<Integer> q = new Queue<Integer>();
			for (int v = 0; v < G.V(); v++)
				distTo[v] = INFINITY;
			distTo[s] = 0;
			marked[s] = true;
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

		// breadth-first search from multiple sources
		private void bfs(Graph G, Iterable<Integer> sources) {
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
		 * Is there a path between the source vertex {@code s} (or sources) and vertex {@code v}?
		 */
		public boolean hasPathTo(int v) {
			validateVertex(v);
			return marked[v];
		}

		/**
		 * Returns the number of edges in a shortest path between the source vertex {@code s}
		 * (or sources) and vertex {@code v}?
		 */
		public int distTo(int v) {
			validateVertex(v);
			return distTo[v];
		}

		/**
		 * Returns a shortest path between the source vertex {@code s} (or sources)
		 * and {@code v}, or {@code null} if no such path.
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
		// check optimality conditions for single source
		private boolean check(Graph G, int s) {

			// check that the distance of s = 0
			if (distTo[s] != 0) {
				System.out.println("distance of source " + s + " to itself = " + distTo[s]);
				return false;
			}

			// check that for each edge v-w dist[w] <= dist[v] + 1
			// provided v is reachable from s
			for (int v = 0; v < G.V(); v++) {
				for (int w : G.adj(v)) {
					if (hasPathTo(v) != hasPathTo(w)) {
						System.out.println("edge " + v + "-" + w);
						System.out.println("hasPathTo(" + v + ") = " + hasPathTo(v));
						System.out.println("hasPathTo(" + w + ") = " + hasPathTo(w));
						return false;
					}
					if (hasPathTo(v) && (distTo[w] > distTo[v] + 1)) {
						System.out.println("edge " + v + "-" + w);
						System.out.println("distTo[" + v + "] = " + distTo[v]);
						System.out.println("distTo[" + w + "] = " + distTo[w]);
						return false;
					}
				}
			}

			// check that v = edgeTo[w] satisfies distTo[w] = distTo[v] + 1
			// provided v is reachable from s
			for (int w = 0; w < G.V(); w++) {
				if (!hasPathTo(w) || w == s) continue;
				int v = edgeTo[w];
				if (distTo[w] != distTo[v] + 1) {
					System.out.println("shortest path edge " + v + "-" + w);
					System.out.println("distTo[" + v + "] = " + distTo[v]);
					System.out.println("distTo[" + w + "] = " + distTo[w]);
					return false;
				}
			}
			return true;
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

	// class for storing a path to recruit heroes with its grade from battle
	public static class HeroRecruitment{
		int days; // days to recruit heroes
		int grade; // grade from battle
		int[] villages; // villages on path in order
		String[] heros; // heroes in the team in order
		public HeroRecruitment(int days, int grade, int[] villages, String[] heros){
			this.days = days;
			this.grade = grade;
			this.villages = villages;
			this.heros = heros;
		}
		public String toString() {
			StringBuilder s = new StringBuilder();
			s.append("Grade = "+grade+", days = " + days + ", villages = ");
			for(int i: villages){
				s.append(i);
			}
			s.append(", heros = ");
			for(String h: heros){
				s.append(h);
			}

			return s.toString();
		} 
	}
	/*helper function.  Given two team, return grade*/
	public static int battle(String[] team, String[] enemy) {
		int amountOfWin = 0;
		for(int i = 0; i < team.length; i++) {
			String a = team[i], b = enemy[i];
			if(a == "S" && b == "C" || a == "C" && b == "A" || a == "A" && b == "S") amountOfWin++;
			if(a == "C" && b == "S" || a == "A" && b == "C" || a == "S" && b == "A") amountOfWin--;
		}
		return amountOfWin;
	}	
	
	// see assignment document for details
	public static String[][] waysToWin(String[] enemy){
        //TODO: complete method to find all ways to win
        String[][] result = new String[][];
        return result;
	}

	// see assignment document for details
	public static HeroRecruitment[] findPlansForRecruitment(Graph G, String[][] hereos, String[] enemy) {
        //TODO: complete method to find all plans for recruitment
        HeroRecruitment[] resultArray = new HeroRecruitment[];
        return resultArray;
	}
	
	// see assignment document for details
	public static HeroRecruitment[] top3Plans(HeroRecruitment[] PathToFindHero){
        //TODO: complete method to find top 3 plan for the recommendation
        HeroRecruitment[] resultArray = new HeroRecruitment[];
        return resultArray;
	}

	public static void main(String[] args) {
		int v = 8, e = 16;
		int[] edges = {	4, 5, 4, 7, 5, 7, 0, 7, 1, 5, 0, 4, 2, 3, 1, 7, 0, 2, 1, 2, 1, 3, 2, 7, 6, 2, 3, 6, 6, 0, 6, 4,};
		String[] heros = {"start", "S", "C", "A", "S", "C", "A", "S"} ;
		Graph G = new Graph(v, e, edges, heros);
		System.out.println(G);

		String[] enemy = {"S", "C", "A"};
		String[][] waysToWin = waysToWin(enemy);
		System.out.print("[Test case 1-1] Ways to win S, C, A are : ");
		for(String[] i: waysToWin){
			for(String j: i) System.out.print(j);
			System.out.print(" ");
		}
		/*
		Expected output:
		[Test case 1-1] Ways to win S, C, A are : SSC SSA SCC CSC ASS ASC ASA ACC ACA AAC
		*/

		/*Test case for findPlansForRecruitment*/
		HeroRecruitment[] result = findPlansForRecruitment(G, waysToWin, enemy);
		System.out.println("\n[Test case 1-2] Find plans for recruitment: ");
		for(HeroRecruitment plan: result){
			System.out.println(plan);
		}
		/*
		[Test case 1-2] Find plans for recruitment: 
		Grade = 1, days = 3, villages = 647, heros = ASS
		Grade = 2, days = 3, villages = 475, heros = SSC
		Grade = 1, days = 3, villages = 275, heros = CSC
		Grade = 3, days = 3, villages = 645, heros = ASC
		Grade = 1, days = 4, villages = 452, heros = SCC
		Grade = 2, days = 4, villages = 625, heros = ACC
		Grade = 1, days = 3, villages = 632, heros = AAC
		Grade = 1, days = 3, villages = 713, heros = SSA
		Grade = 2, days = 4, villages = 643, heros = ASA
		Grade = 1, days = 3, villages = 623, heros = ACA
		*/
		
		/*Test case for top3Plans*/
		HeroRecruitment[] top3 = top3Plans(result);
		System.out.println("\n[Test case 1-3] Recommended three best plans: ");
		for(HeroRecruitment plan: top3){
			System.out.println(plan);
		}
		/*
		[Test case 1-3] Recommended three best plans: 
		Grade = 3, days = 3, villages = 645, heros = ASC
		Grade = 2, days = 3, villages = 475, heros = SSC
		Grade = 2, days = 4, villages = 625, heros = ACC
		*/
	}	
}