//M114020031 黃宛婷
//todo: write code in the chop(int p, int q) to calculate what happens when a tree branch is chopped. 
//todo: modify union(int p, int q) to maintain add nodes to the tree.
//DO NOT EDIT other functions NOR add global variables.

public class HW1 {

	// ChopTrees is modified from QuickUnionUF
	// https://algs4.cs.princeton.edu/code/edu/princeton/cs/algs4/QuickUnionUF.java.html
	// QuickUnionUF JavaDoc
	// https://algs4.cs.princeton.edu/code/javadoc/edu/princeton/cs/algs4/QuickUnionUF.html
	public static class ChopTrees {
		private int[] parent; // parent[i] = parent of i
		private int count; // number of components
		private int N;
		private boolean collapsed;

		/**
		 * Initializes an empty union-find data structure with
		 * {@code n} elements {@code 0} through {@code n-1}.
		 * Initially, each element is in its own set.
		 *
		 * @param n the number of elements
		 * @throws IllegalArgumentException if {@code n < 0}
		 */
		public ChopTrees(int n) { // 建立以陣列呈現的樹
			count = n;
			N = n;
			collapsed = false; // 是否倒塌預設為否
			parent = new int[n]; // 創一陣列parent，內部n個點值皆設為0
			for (int i = 0; i < n; i++) {
				parent[i] = 0;
			}
		}

		/**
		 * Returns the number of sets.
		 *
		 * @return the number of sets (between {@code 1} and {@code n})
		 */
		public int count() {
			return count;
		}

		/**
		 * Returns the canonical element of the set containing element {@code p}.
		 *
		 * @param p an element
		 * @return the canonical element of the set containing {@code p}
		 * @throws IllegalArgumentException unless {@code 0 <= p < n}
		 */
		public int find(int p) { 						// 找到p的root在哪
			validate(p);								// 確認p點在合法範圍內
			while (parent[p] != 0 && parent[p] != -1) 	// 當p點值不為0(沒這條邊)以及-1(已倒塌)時，一直往上找，返回最後root
				p = parent[p];
			return p;
		}

		/**
		 * Returns true if the two elements are in the same set.
		 * 
		 * @param p one element
		 * @param q the other element
		 * @return {@code true} if {@code p} and {@code q} are in the same set;
		 *         {@code false} otherwise
		 * @throws IllegalArgumentException unless
		 *                                  both {@code 0 <= p < n} and
		 *                                  {@code 0 <= q < n}
		 * @deprecated Replace with two calls to {@link #find(int)}.
		 */
		@Deprecated
		public boolean connected(int p, int q) {
			return find(p) == find(q);
		}

		// validate that p is a valid index 辨識此棵樹是否存在
		private void validate(int p) {
			int n = parent.length; 			// n是parent array的長度(所有節點)
			if (p < 0 || p >= n) { 			// 若輸入的點不在0~n的範圍裡則跳出警告
				System.out.println("index " + p + " is not between 0 and " + (n - 1));
			}
		}

		/**
		 * Adds a branch to the tree, see assignment for details
		 *
		 * @param p one element
		 * @param q the other element
		 * @throws IllegalArgumentException unless
		 *                                  both {@code 0 <= p < n} and
		 *                                  {@code 0 <= q < n}
		 */
		public boolean union(int p, int q) {		//q is root
			int rootP = find(p); 					//分別找到p.q的樹根
			int rootQ = find(q);

			//檢測是否同顆樹但有上下順序關係(p上q下)
			if(rootP == rootQ){
				int tempQ = q;
				int tempP = p;
				boolean p_up = false;
				while (parent[tempP] != 0 && parent[tempP] != -1){
					p = parent[p];
					if(p == tempQ){
						p_up = true;
						break;
					}
					break;
				}
				if(p_up == true) return false;
			}

			if(rootP == rootQ || N > 1000){		//如果兩點在同一樹上or總數量超過1000or，返回false
				return false;
			}
			else if((p > 0 && p < N)||(q > 0 && q < N)){				//如果兩點在同一樹上，則檢驗P是否在合法範圍內
				if(parent[p] != q){					//p的root不是q
					parent[p] = q;
					return true;
				}
				else{								//p的root已經是q了,返回false
					return false;
				}
			}
			return false;
		}

		// For chopping tree branches, see assignment for details
		public int chop(int p, int q) {
			int highest = 0;
			int[] tall = new int[N+1];

			if (collapsed) return -1;						//如果樹倒了return -1
			
			for(int i=0; i<N; i++){
				int height = 0;
				int x = i; 
				while(parent[x]!=0 && parent[x]!=-1)		//找到目前每個點的高度並存進tall[]
				{
					x = parent[x];
					height++;
				}
				tall[i] = height;
			}
			for(int i=0; i<N; i++){							//找到哪個點最高
				if (tall[i] > highest){
					highest = tall[i];
				}
			}

			if(parent[p] == q || parent[q] == p){	//要相鄰邊才可以進去
				int numofchopnodes = 0;

				if(parent[p] == q){					//q是父			
					parent[p] = -1;
					tall[p] = 0;
					numofchopnodes+=1;
										
					for(int i=0; i<N; i++){			//找到哪些點連在p上
						int t = i;
						while(parent[t] != 0 && parent[t] != -1){
							t = parent[t];
							if(t == p){
								numofchopnodes+=1;
								parent[i] = -1;		//被砍下來的點parent改為-1
								tall[i] = 0;
								break;
								}
							}
							
					}
				}
		
				else if(parent[q] == p){			//p是父			
					parent[q] = -1;
					tall[q] = 0;
					numofchopnodes+=1;
										
					for(int i=0; i<N; i++){			//找到哪些點連在p上
						int t = i;
						while(parent[t] != 0 && parent[t] != -1){
							t = parent[t];
							if(t == q){
								numofchopnodes+=1;
								parent[i] = -1;		//被砍下來的點parent改為-1
								tall[i] = 0;
								break;
								}
							}
							
						}
				}

				int temp = 0;				//剛剛設了最高點array，找到他
				for(int i=0; i<N; i++){
					if(tall[i] > temp){
						temp = tall[i];
					}
				}
				if(temp == highest){		//如果剛剛的最高點跟原始的最高點相比有下降的話，就會倒塌，否的話返回掉下的node數量
					return numofchopnodes;	//回傳因為切斷樹枝而掉下來的node數量
				}
				else{
					collapsed = true;
					return -1;
				}
				
			}
			return 0;
		}
		public int testunion(int i){		//檢測union是否正確
			return parent[i];	
		}
	}

	public static void main(String[] args) {
		ChopTrees ct = new ChopTrees(25); // 創一棵25節點的樹
		ct.union(1, 2);
		ct.union(3, 4);
		ct.union(1, 3);
		ct.union(7, 2);
		ct.union(7, 3);
		ct.union(1, 6);
		ct.union(10, 11);
		ct.union(15, 12);
		ct.union(2, 17);
		ct.union(3, 15);
		ct.union(4, 11);
		ct.union(1, 3);
		ct.union(6, 8);
		ct.union(8, 19);
		ct.union(11, 17);
		ct.union(12, 15); // 已相連
		ct.union(11, 18);
		ct.union(5, 14);
		
		// for(int i=0; i<26; i++){
		// 	System.out.println((i)+":"+ct.testunion(i));
		// };

		System.out.println("After Chop 8, 6 => " + ct.chop(8, 6)); // expected output: After Chop 8, 6 => 1
		System.out.println("After Chop 11, 18 => " + ct.chop(11, 18));// expected output: After Chop 11, 18 => 3
		System.out.println("After Chop 1, 3 => " + ct.chop(1, 3));// expected output: After Chop 1, 3 => 1

		System.out.println("Union of 20, 9 => " + ct.union(20, 9)); // expected output: Union of 20, 9 => true

		System.out.println("After Chop 15, 3 => " + ct.chop(15, 3));// expected output: After Chop 15, 3 => -1
		System.out.println("After Chop 14, 9 => " + ct.chop(14, 9));// expected output: After Chop 14, 9 => -1

		System.out.println("Union of 20, 9 => " + ct.union(20, 9)); // expected output: Union of 20, 9 => false

	}
}