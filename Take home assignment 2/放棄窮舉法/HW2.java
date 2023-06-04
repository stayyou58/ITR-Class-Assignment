package 放棄窮舉法;
// 黃宛婷 M114020031
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
		/* Adds the item to this bag. 新增一個節點到Bag*/
		public void add(Item item) {
			Node<Item> oldfirst = first;
			first = new Node<Item>();
			first.item = item;
			first.next = oldfirst;
			n++;
		}
		/* 用來遍歷 Bag 中的元素。迭代器是一個內部類別 LinkedIterator，實現了 Iterator<Item> 介面，並重載了 hasNext() 和 next() 方法，可以在 Bag 中遍歷元素。 */
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
		public Graph(int V) {	/*建構子，初始化一個具有 V 個頂點和 0 條邊的空圖*/
			if (V < 0) throw new IllegalArgumentException("Number of vertices must be non-negative");
			this.V = V;
			this.E = 0;
			adj = (Bag<Integer>[]) new Bag[V];
			for (int v = 0; v < V; v++) {
				adj[v] = new Bag<Integer>();
			}
		}

		/**始化具有 V 個頂點和 E 條邊的圖，並指定邊的起點和終點，以及每個頂點對應的英雄屬性。
		 * Initializes a random edge-weighted graph with V vertices and E edges.
		 建構子，傳入 V、E、edgeweights和heros。V是頂點數，E是邊數，edgeweights是一個整數陣列，它的長度是E*2，其中包含所有邊的起點和終點；heros是一個字串陣列，它的長度是V，它包含了與每個頂點對應的英雄的屬性*/
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
		public Graph(Graph G) {	/*傳入一個 Graph G，建立一個與 G 完全相同的圖 */
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
		/**	返回圖中頂點的數量
		 * Returns the number of vertices in this graph.
		 */
		public int V() {
			return V;
		}
		/**	返回圖中邊的數量
		 * Returns the number of edges in this graph.
		 */
		public int E() {
			return E;
		}
		/* Returns the list of hero's attribute at the village in this edge-weighted graph. 回傳與每個頂點對應的英雄屬性*/
		public String[] heros() {
			return heros;
		}
		// throw an IllegalArgumentException unless {@code 0 <= v < V} 。檢查v是否是有效的頂點，如果 v 不在 0 到 V-1 之間，則會拋出 IllegalArgumentException
		private void validateVertex(int v) {
			if (v < 0 || v >= V)
				throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V-1));
		}
		/**	添加無向邊 v-w 到圖中
		 * Adds the undirected edge v-w to this graph.
		 */
		public void addEdge(int v, int w) {
			validateVertex(v);
			validateVertex(w);
			E++;
			adj[v].add(w);
			adj[w].add(v);
		}
		/**	回傳 v 的相鄰頂點
		 * Returns the vertices adjacent to vertex {@code v}.
		 */
		public Iterable<Integer> adj(int v) {
			validateVertex(v);
			return adj[v];
		}
		/**	回傳v點的degree
		 * Returns the degree of vertex {@code v}.
		 */
		public int degree(int v) {
			validateVertex(v);
			return adj[v].size();
		}
		/**	回傳一個描述該圖的字串，包括頂點數和邊數，以及每個頂點的相鄰頂點
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
		// helper linked list class 佇列實現為一個鏈結串列（linked list）
		private static class Node<Item> {
			private Item item;
			private Node<Item> next;
		}
		/**
		 * Initializes an empty queue.建立空Queue
		 */
		public Queue() {
			first = null;
			last  = null;
			n = 0;
		}
		/**
		 * Returns true if this queue is empty.判斷佇列是否為空
		 */
		public boolean isEmpty() {
			return first == null;
		}
		/**
		 * Returns the number of items in this queue.返回佇列中元素的個數
		 */
		public int size() {
			return n;
		}
		/**
		 * Returns the item least recently added to this queue.返回最早加入佇列的元素，但不刪除
		 */
		public Item peek() {
			if (isEmpty()) throw new NoSuchElementException("Queue underflow");
			return first.item;
		}
		/**
		 * Adds the item to this queue.將元素加入佇列的尾端
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
		 * Removes and returns the item on this queue that was least recently added.刪除佇列中最早加入的元素並返回
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
		 * Returns a string representation of this queue.返回佇列的字符串表示
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
		 * Returns an iterator that iterates over the items in this queue in FIFO order.用先進先出法返還Queue裡面的所有item
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

		/**	計算從起點 s 到每個頂點 v 的最短路徑
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

		/**	計算從多個起點 sources 中的任意一個起點到每個頂點 v 的最短路徑
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

		//	判斷從起點 s（或 sources 中的任意一個起點）是否存在到頂點 v 的路徑
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

		// breadth-first search from multiple sources	返回從起點 s（或 sources 中的任意一個起點）到頂點 v 的最短路徑
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

		/**	判斷從起點 s（或 sources 中的任意一個起點）是否存在到頂點 v 的路徑
		 * Is there a path between the source vertex {@code s} (or sources) and vertex {@code v}?
		 */
		public boolean hasPathTo(int v) {
			validateVertex(v);
			return marked[v];
		}

		/**	返回最短路徑上有幾條邊
		 * Returns the number of edges in a shortest path between the source vertex {@code s}
		 * (or sources) and vertex {@code v}?
		 */
		public int distTo(int v) {
			validateVertex(v);
			return distTo[v];
		}

		/**	返回從起點 s（或 sources 中的任意一個起點）到頂點 v 的最短路徑，一一列出來
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
		// check optimality conditions for single source	用於檢查從源頂點 s 到每個頂點的最短路徑的優化條件，該方法主要用於檢查最短路徑算法的正確性
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
		int n = enemy.length;					//先看敵軍有幾人
		String[] tempteam = new String[3];		//tempteam用來存此次的排列組合
		int permutations_amount = 1;			//初始化士兵排列組合變數
		int score = 0;
		String[] soldier = {"S", "C", "A"};		//用陣列存兵種
		int p=0;
		int q=0;

		for(int i=1; i<=n; i++){				//算soldier會有幾個組合 3^n
			permutations_amount = (int) Math.pow(3, n);
		}

		String[][] old_result = new String[permutations_amount][n];	//先確認可能有幾個排列組合後再決定最後結果result的陣列大小
		String first,second,third;

		for( int x=0; x<3; x++ ){
			first = soldier[x];
			for( int y=0; y<3; y++ ){
				second = soldier[y];
				for( int z=0; z<3; z++ ){
					third = soldier[z];
						
					q=0;	//初始化q
					//存此次的排列組合
					tempteam[0] = first;
					tempteam[1] = second;
					tempteam[2] = third;

					score = battle(tempteam, enemy);		//比對出分數存進去
					if( score>=1 ){
						//此組合存到result

						for( q=0; q<3; q++){
							old_result[p][q] = tempteam[q];
						}
						p++;
					}	
				}
			}
		}
		int space=permutations_amount;			//計算old_result裡有實際用到的格數
		for( int i=0; i<permutations_amount; i++){
			if(old_result[i][0] == null){
				space--;
			}
		}
		String[][] result = new String[space][n];	//確認使用到的空間後，宣告最後要輸出的result[][]，將舊的assign進去
		for( int i=0; i<space; i++){
			for( int j=0; j<n; j++){
				result[i][j] = old_result[i][j];
			}
		}
		return result;
	}

	// see assignment document for details
	public static HeroRecruitment[] findPlansForRecruitment(Graph G, String[][] hereos, String[] enemy) {
    //TODO: complete method to find all plans for recruitment
		int hero_group_amount = hereos.length;   				//先看要找幾組士兵
		int hero_amount = enemy.length;      					//確認今天要找幾個英雄
		String[] temp_find = new String[hero_amount];  			//因為要一組一組找，每組的士兵暫存區
		int shortest_total_length = 0;      					//把最短的total_length找出來
		int num_of_villages = G.V();      						//找圖裡的村莊數
		HeroRecruitment[] resultArray = new HeroRecruitment[hero_group_amount];

		//最終四個結果要存入的四個陣列
		int[] temp_days = new int[hero_group_amount];     		//存找每組士兵的最短長度/天數 
		int days = 0;            								//最後要輸出的天數
		int grade = 0;            								//用上一個func計分方式，晚點補上
		int[] villages = new int[hero_amount];      			//也就是將所會經過找的村莊存起來，存成陣列
		String[] recruitedHeroes = new String[hero_group_amount]; //也就是每次派出的英雄是哪幾個人()，存成陣列

		String hero_tofind;
		// String heros_to_result;

		for( int i=0; i<hero_group_amount; i++){             		//一次找一組，每組都找hero_group_amount
			String[] temp_result = new String[1];      				//存最後輸給return的heros
			int[] village_result = new int[hero_amount];      		//存最後輸給return的villages
			int flag[] = new int[num_of_villages];         			//看是否徵過此村莊的兵了
			Arrays.fill(flag, 0);            					//初始化為零(沒有徵過)
			int[] fits_village = new int[num_of_villages];       	//將有符合一樣士兵的村莊存進陣列裡

			int start = 0;
			int total_days = 0;
			String hero_result ="";

			for( int j=0; j<hero_amount; j++){          			//把每組的士兵順序存出來，從第一組開始找，總共三次
				temp_find[j] = hereos[i][j];
				hero_tofind = temp_find[j];
				

				//存出來之後，用BFS找每組的最短路徑 
				int[] slice_length = new int[50];          	//等等要裝點跟點之間小段距離的
				int path_count = 0;              			//最短路徑有多長
				int total_length = 0;             			//把每個slice_length[v]加起來用的
				shortest_total_length = 0;            		//把最短的total_length找出來

				int count = 0;               				//拿來跑villages的


				///////////////////////////先找到第一輪有符合的點////////////////
				int x = 0;
				for ( int n=0; n<num_of_villages; n++){         				//每個村莊跑一次，預設跑8個村莊，把所有符合需求兵的村莊都存出來
					String villages_hero = G.heros()[n];        				//圖裡﹐v點上的值，也就是村莊有的那個英雄

					if ((villages_hero == hero_tofind) && (flag[n] == 0)){    	//此村莊符合當前要找的英雄，且是沒召集過的村，才可以進去
						fits_village[x++] = n;          						//找到有符合的所有村，fits_village是暫存array
						flag[n] = 1;
						// System.out.println(fits_village[x-1]);
					}
				}
				int shortest_temp = 0;
				int slice_seslected_village = 0;
					
				for( int y : fits_village){            											//暫存array裡的跑一遍
					BreadthFirstPaths bfs = new BreadthFirstPaths(G, start);   					//從當前起點開始進行BFS
					int shortestPath = bfs.distTo(y);         									//找到從起點s到v點的最短路徑長度
					// System.out.println(y +"村:距離"+ shortestPath);
					if (((shortest_temp == 0) || (shortestPath<=shortest_temp)) && (y != 0)){   //看哪個村的距離最短,存進最終要返回的villages[]裡
						if (shortestPath != 0){
						shortest_temp = shortestPath;
						villages[j] = y;
						slice_seslected_village = y;
						}
						// System.out.println("現在輸出的villages[] "+"第"+j+"格:"+ villages[j]);
					}
				}
				total_days = total_days + shortest_temp;        		//看這個路徑的總長度/天數
				start = slice_seslected_village;          				//下個起點從這邊開始
				
				// System.out.println("選擇村莊: "+start);
				Arrays.fill(flag, 0);            					//清空這次的標誌陣列
				Arrays.fill(fits_village, 0);          			//清空村莊的暫存陣列
				
				////輸出調整部分/////
				village_result[j] = village_result[j] + villages[j];	//把村莊存起來輸出用

				hero_result = hero_result + temp_find[j];				//把S、S、C存成SSC
				temp_result[0] = hero_result;
			}		
				resultArray[i] = new HeroRecruitment(total_days, battle(temp_find, enemy), village_result, temp_result);
					
		}
		return resultArray;
	}	

				

	
	// see assignment document for details
	// public static HeroRecruitment[] top3Plans(HeroRecruitment[] PathToFindHero){
	// //TODO: complete method to find top 3 plan for the recommendation
	// HeroRecruitment[] resultArray = new HeroRecruitment[3];
	// HeroRecruitment first =new HeroRecruitment(PathToFindHero[0].days, PathToFindHero[0].grade,PathToFindHero[0].villages , PathToFindHero[0].heros);
	// 	for( int i=0; i<resultArray.length; i++) {					//用迴圈一個一個條件比較
	// 		for( HeroRecruitment find: PathToFindHero){				//首先找分數最大的，遍歷 PathToFindHero 陣列中的每個 HeroRecruitment 物件
	// 			if( find.grade>first.grade ) {						//如果分數較大直接取代，以找到最大值
	// 				first = find;
	// 			}else if( find.grade == first.grade) {				//分數一樣的組合就比天數
	// 				if( find.days<first.days) {						//找到天數最少的
	// 					first = find;
	// 				} else if( find.days == first.days) {			//天數一樣的組合就比字母排序
	// 					for( int l=0; l<find.heros.length;l++) {
	// 						if( find.heros[l].compareTo(first.heros[l])<0) {		//開頭比較小優先度也會提高
	// 							first = find;
	// 							break;												//最後直接break迴圈
	// 						}
	// 					}
	// 				}
	// 			}
	// 		}
	// 		//目前已經取到最好的方案了，接下來為列印做準備
	// 		//從剛剛找好的最好first選擇，建立一個要回傳的Array物件
	// 		resultArray[i] = new HeroRecruitment(first.days, first.grade,first.villages , first.heros);
	// 		for( HeroRecruitment find: PathToFindHero){							//把被找過的grade設為-1，避免再度找到
	// 			int score = 0;													//用來比較目前方案(find)和最佳方案(first)之間的英雄名稱的暫時用標記
	// 			for( int r=0;r<find.heros.length;r++ ) {						//遍歷目前方案 (find) 的 heros 陣列
	// 				score = score +find.heros[r].compareTo(first.heros[r]);		//比較結果加到score
	// 			}
	// 			if( score == 0 ) {												//比較所有英雄名稱後分數仍為零，代表這是目前的最佳方案，grade設為1再也不考慮
	// 				find.grade=-1;
	// 			}
	// 		}
   	// 	}
    //  	return resultArray;
	// }

	public static HeroRecruitment[] top3Plans(HeroRecruitment[] PathToFindHero) {
		HeroRecruitment[] resultArray = new HeroRecruitment[3];
		
		for (int i = 0; i < resultArray.length; i++) {			//每一筆結果都要做
			HeroRecruitment first = null;							//宣告一個名為first的HeroRecruitment物件，用來存最佳方案
			for (HeroRecruitment find : PathToFindHero) {			//第一個找到最後一個
				//比較當前找到的方案(find)跟最佳方案(first)的優劣
				if (first == null || find.grade > first.grade || (find.grade == first.grade && find.days < first.days) || (find.grade == first.grade && find.days == first.days && find.heros[0].compareTo(first.heros[0]) < 0)) {
					first = find;
				}
			}
			//將找到的最好方案回傳resultarray，取三個
			resultArray[i] = new HeroRecruitment(first.days, first.grade,first.villages , first.heros);
			
			for (HeroRecruitment find : PathToFindHero) {
				boolean sameHeroes = true;										//用來判斷當前找到的組合是否跟最好組合相符
				for (int r = 0; r < find.heros.length; r++) {					//遍歷每個find裡面的每個英雄(預設3個)
					if (find.heros[r].compareTo(first.heros[r]) != 0) {			//比較每個英雄是否相同
						sameHeroes = false;										//不同的話設為false
						break;
					}
				}
				if (sameHeroes) {												//如果已經找到這組最好英雄，就把find裡的設為-1避免再找到
					find.grade = -1;
				}
			}
		}
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