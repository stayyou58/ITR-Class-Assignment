//M114020031 黃宛婷, M114020037 曾彗瑀
//todo: modify sort and findNextRun functions to implement a mergesort that finds natural ordered items 
//with "runs" and merges them using a bottom up approach.   
//DO NOT EDIT other functions NOR add global variables.

//MergeNatural2023 is modified from MergeBU from https://algs4.cs.princeton.edu/code/edu/princeton/cs/algs4/MergeBU.java.html
//JavaDoc https://algs4.cs.princeton.edu/code/javadoc/edu/princeton/cs/algs4/MergeBU.html
public class MergeNatural2023 {

    // This class should not be instantiated.
    private MergeNatural2023() { }

    // stably merge a[lo..mid] with a[mid+1..hi] using aux[lo..hi]
    private static void merge(Comparable[] a, Comparable[] aux, int lo, int mid, int hi) {

        // copy to aux[]
        for (int k = lo; k <= hi; k++) {
            aux[k] = a[k]; 
        }

        // merge back to a[]
        int i = lo, j = mid+1;
        for (int k = lo; k <= hi; k++) {
            if      (i > mid)              a[k] = aux[j++];  // this copying is unnecessary
            else if (j > hi)               a[k] = aux[i++];
            else if (less(aux[j], aux[i])) a[k] = aux[j++];
            else                           a[k] = aux[i++];
        }

    }
    
    // todo: write code in this function
    // finds next run in the input array with the given starting index
    // returns end index of the run
    public static int findNextRun(Comparable[]a, int startIndex) {
    	int endIndex = a.length -1;
    	// add your code here
        int lo = startIndex;
        int N = a.length;
        if(lo>=N-1){
            endIndex = lo;
        }
     
        if(startIndex<N-1){
            while(!less(a[lo+1], a[lo])){    //找到已照順序的陣列
                ++lo;
            }
            if (lo <= endIndex){        //lo還沒跑到最後一格時
                endIndex = lo;
                lo++;
            }
            else{                       //lo已經跑到最後一格時，直接把lo當成endIndex
                endIndex = lo;
            }
        }
        

    	// do not modify the following lines
    	System.out.println("Run start: " + startIndex + ", end: " + endIndex);
    	show(a,startIndex,endIndex);
    	return endIndex;
    }
    
    // todo: write code in this function to sort the array with mergesort using natural runs
    // use findNextRun function in this function to identify runs
    public static void sort(Comparable[] a) {
        // add your code here
        int i=0;
        int N = a.length;
        int startIndex=0,endIndex=0;
        int start = 0, end=0;
        Comparable[] aux = new Comparable[N];
        MergeNatural2023 mn=new MergeNatural2023();


        while(!isSorted(a)){ // 判斷是否排序完
            int mid=findNextRun(a, start);
            if(mid+1<N){ // mid不超出陣列大小才可執行
                int last = findNextRun(a, mid+1);

                mn.merge(a, aux, start, mid, last); //合併
            
                start = last+1; //重設start
            }else{
                start=0; //下ㄧround重設start
            }
            
            if(start>=N){
                start=0;
               // merge(a, aux, startIndex, startIndex+endIndex-1, endIndex);
            }
        }
        System.out.println("Run start: 0, end: 12" );
        show(a);
        // sort the array
        
        

    }

  /***********************************************************************
    *  Helper sorting functions.
    ***************************************************************************/
    
    // is v < w ?
    private static boolean less(Comparable v, Comparable w) {
        return v.compareTo(w) < 0;
    }


   /***************************************************************************
    *  Check if array is sorted - useful for debugging.
    ***************************************************************************/
    private static boolean isSorted(Comparable[] a) {
        for (int i = 1; i < a.length; i++)
            if (less(a[i], a[i-1])) return false;
        return true;
    }

    // print array to standard output
    private static void show(Comparable[] a) {
        for (int i = 0; i < a.length; i++) {
            System.out.print(a[i] + " ");
        }
        System.out.println();
    }
    
    // print array between indices to standard output
    private static void show(Comparable[] a, int startIndex, int endIndex) {
        for (int i = startIndex; i <= endIndex; i++) {
            System.out.print(a[i] + " ");
        }
        System.out.println();
    }
    
    public static void main(String[] args) {
        Integer[] a = {1,2,3,9,12,20,7,15,11,6,20,300,0};
        MergeNatural2023.sort(a);
        
        System.out.println();
        System.out.print("Sorted: ");
        show(a); 
    }
}
