
//M114020031 黃宛婷, M114020037 曾彗瑀
public class UnionFind2023 {
	// QuickUnionUF from https://algs4.cs.princeton.edu/code/edu/princeton/cs/algs4/QuickUnionUF.java.html
				// JavaDoc https://algs4.cs.princeton.edu/code/javadoc/edu/princeton/cs/algs4/QuickUnionUF.html
				public static class QuickUnionUF {
				    private int[] parent;  // parent[i] = parent of i
				    private int count;     // number of components

				    /**
				     * Initializes an empty union-find data structure with
				     * {@code n} elements {@code 0} through {@code n-1}.
				     * Initially, each element is in its own set.
				     *
				     * @param  n the number of elements
				     * @throws IllegalArgumentException if {@code n < 0}
				     */
				    public QuickUnionUF(int n) {
				        parent = new int[n];
				        count = n;
				        for (int i = 0; i < n; i++) {
				            parent[i] = i;
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
				     * @param  p an element
				     * @return the canonical element of the set containing {@code p}
				     * @throws IllegalArgumentException unless {@code 0 <= p < n}
				     */
				    public int find(int p) {
				        validate(p);
				        while (p != parent[p])
				            p = parent[p];
				        return p;
				    }

				    // validate that p is a valid index
				    private void validate(int p) {
				        int n = parent.length;
				        if (p < 0 || p >= n) {
				            throw new IllegalArgumentException("index " + p + " is not between 0 and " + (n-1));
				        }
				    }

				    /**
				     * Returns true if the two elements are in the same set.
				     *
				     * @param  p one element
				     * @param  q the other element
				     * @return {@code true} if {@code p} and {@code q} are in the same set;
				     *         {@code false} otherwise
				     * @throws IllegalArgumentException unless
				     *         both {@code 0 <= p < n} and {@code 0 <= q < n}
				     * @deprecated Replace with two calls to {@link #find(int)}.
				     */
				    @Deprecated
				    public boolean connected(int p, int q) {
				        return find(p) == find(q);
				    }

				    /**
				     * Merges the set containing element {@code p} with the set
				     * containing element {@code q}.
				     *
				     * @param  p one element
				     * @param  q the other element
				     * @throws IllegalArgumentException unless
				     *         both {@code 0 <= p < n} and {@code 0 <= q < n}
				     */
				    public void union(int p, int q) {
				        int rootP = find(p);
				        int rootQ = find(q);
				        if (rootP == rootQ) return;
				        parent[rootP] = rootQ;
				        count--;
				    }
				    
				    // todo: complete the method below to return a list of nodes in the same tree as node p, 
				    // that have the depth of the tree's height. 
				    public int[] nodesWithDepthOfTreeHeight(int p) {	
				    	int[] a = new int[3];
				    	int[] b = new int[10];
				    	
				    	int highest=0;
				    	int j=0;
				    	
					    for(int i=0;i<10;i++) {//every node's height
					    	int t=i;
					    	int height = 0;
					    	if(find(p)==find(i)) { //判斷是否為同棵樹
					    	 while (t != find(p)) {
						        t = parent[t];
						        height++;
						     }
					    	 b[i]=height; //將每個高度存進array b
					    	}
					    }
					    
					    j=0;
					    
					    for(int k=0;k<b.length;k++) {//比較最高節點
					      if(b[k]>highest) {
				    		 highest=b[k];
				    	 }
					    }
					    
					    for(int n=0;n<10;n++) {//取出最高的所有node
					    	if(b[n]==highest && find(p)==find(n)){
					    		a[j++]=n;
					    	}
					    }
				    	return a;
				    }
				    
				    // (optional) todo: complete the method to return the size of the connected component with item p.
				    public int componentSize(int p) {
				    	return 0;
				    }
				    
				    public static void printNodes(int[] nodes, int node) {
				    	int i;
				    	String output = "";
				    	for (i=0; i<nodes.length; i++) {
				    		if(nodes[i] != -1) {
				    			output += nodes[i] + ",";
				    		}
				    	}
				    	if (output != ""){
				    		output = output.substring(0, output.length() - 1);
				    	}
				    	System.out.println("Nodes with depth of tree height in " + node + "'s tree : " + output);
				    }
				}
				
				public static void main(String[] args) {
					QuickUnionUF uf = new QuickUnionUF(10);
					uf.union(1, 2);
					uf.union(3, 2);
					uf.union(5, 4);
					uf.union(4, 0);
					uf.union(2, 0);
					uf.union(7, 6);
					uf.union(8, 6);
					uf.union(9, 6);
					
					uf.printNodes(uf.nodesWithDepthOfTreeHeight(0), 0); // expected output 1,3,5
					uf.printNodes(uf.nodesWithDepthOfTreeHeight(2), 2); // expected output 1,3,5
					uf.printNodes(uf.nodesWithDepthOfTreeHeight(7), 7); // expected output 7,8,9

					System.out.println();
					System.out.println("Component size of 1 = " + uf.componentSize(1)); // expected output 6
					System.out.println("Component size of 6 = " + uf.componentSize(6)); // expected output 4
				}
}
