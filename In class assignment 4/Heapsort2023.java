// M114020031 黃宛婷, M114020037 曾彗瑀
// todo: Modify sort, sink, less, and/or exch functions to implement a heapsort 
// that sorts items from large to small and treat even numbers as if they are twice as large.  
// DO NOT EDIT other functions NOR add global variables.

//Heap is modified from Heap at https://algs4.cs.princeton.edu/code/edu/princeton/cs/algs4/Heap.java.html
//JavaDoc https://algs4.cs.princeton.edu/code/javadoc/edu/princeton/cs/algs4/Heap.html
public class Heapsort2023 {

	public class Heap {

	    // This class should not be instantiated.
	    private Heap() { }

	    /**
	     * Rearranges the array in ascending order, using the natural order.
	     * @param pq the array to be sorted
	     */
	    public static void sort(Comparable[] pq) {
	        int n = pq.length;

	        // heapify phase
	        for (int k = n/2; k >= 1; k--)
	            sink(pq, k, n);
	        
	        // sortdown phase
	        int k = n;
	        while (k > 1) {
	            exch(pq, 1, k--);
	            sink(pq, 1, k);
	        }
	    }
	    
	   /***************************************************************************
	    * Helper functions to restore the heap invariant.
	    ***************************************************************************/

	    private static void sink(Comparable[] pq, int k, int n) {
	        while (2*k <= n) {
	            int j = 2*k;
	            if (j < n && less(pq, j+1, j)) j++; // 比對兩數哪一個小，做交換
	            if (!less(pq, j, k)) break; // parent 沒有小於 child 則 break
	            exch(pq, k, j);
	            k = j;
	        }
	    }
		
	   /***************************************************************************
	    * Helper functions for comparisons and swaps.
	    * Indices are "off-by-one" to support 1-based indexing.
	    ***************************************************************************/
	    private static boolean less(Comparable[] pq, int i, int j) {
			int a = (int)pq[i-1]; // 用int參數暫存pq[i-1]
			int b = (int)pq[j-1]; // 用int參數暫存pq[j-1]
			if(a%2==0 && b%2!=0){ // 情況一：前面的數為偶數，後面為奇數
				a=a*2; // 偶數乘二
				if(a<b){ // 判斷大小
					return true;
				}else{
					return false;
				}
			}
			if(a%2!=0 && b%2==0){ // 情況二：後面的數為偶數，前面為奇數
				b=b*2; // 偶數乘二
				if(a<b){ // 判斷大小
					return true;
				}else{
					return false;
				}
			}
	        return pq[i-1].compareTo(pq[j-1]) < 0;
	    }

	    private static void exch(Object[] pq, int i, int j) {
	        Object swap = pq[i-1];
	        pq[i-1] = pq[j-1];
	        pq[j-1] = swap;
	    }
	    
	    // print array to standard output
	    private static void show(Comparable[] a) {
	        for (int i = 0; i < a.length; i++) {
	            System.out.print(a[i] + " ");
	        }
            System.out.println();
	    }
	    
	    /**
	     * Reads in a sequence of strings from standard input; heapsorts them; 
	     * and prints them to standard output in ascending order. 
	     *
	     * @param args the command-line arguments
	     */
	}
	
    public static void main(String[] args) {
    	Integer[] a = {1,2,3,4,5,6,7};
    	System.out.print("Before Sorting: ");
    	Heap.show(a);
    	Heap.sort(a);
    	System.out.print("After Sorting: ");
    	Heap.show(a);
        
        System.out.println();
        
        Integer[] b = {15,5,3,2,7,9,2,19,12};
        System.out.print("Before Sorting: ");
        Heap.show(b);
    	Heap.sort(b);
    	System.out.print("After Sorting: ");
        Heap.show(b);

        System.out.println();
        
        Integer[] c = {5,3,2,8,10,11,19,1,5,5,27,5,16};
    	System.out.print("Before Sorting: ");
    	Heap.show(c);
    	Heap.sort(c);
    	System.out.print("After Sorting: ");
    	Heap.show(c);

        System.out.println();
    	
    	Integer[] d = {5,3,2,8,10,22,6,7,23,10,15,11,19,1,5,5,27,5,30};
    	System.out.print("Before Sorting: ");
    	Heap.show(d);
    	Heap.sort(d);
    	System.out.print("After Sorting: ");
    	Heap.show(d);
    }
}
