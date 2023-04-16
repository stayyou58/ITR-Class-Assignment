import java.util.concurrent.Exchanger;

//todo: student 1 id & name, student 2 id & name
//todo: write code in the sort(int[] a) function in InclassSort.java to sort the input array in the way shown in the expected output. 
//DO NOT EDIT other functions NOR add global variables. 

public class InclassSort {
	
	// todo: write code in this function
	public int[] sort(int[] a) {
		int N = a.length;
		for (int i=0; i<N; i++){
			for (int j=i; j>0; j--){
				if ((a[j]<a[j-1])){		//小到大排序 insertion
					int temp = a[j];	//交換
					a[j] = a[j-1];
					a[j-1] = temp;
				}
			}
		}
	
		int[] new_array = new int[a.length];		//新的array可以放
		int min = 0;			//min,max指標指向原始array頭尾
		int max = N-1;
		
		int i = 0;
		while(min < max){		//當min<max，則繼續移動
			
			new_array[i] = a[max--];	//max往前一格
			i++;

			new_array[i] = a[min++];	//min往後一格
			i++;
			
		}
		if(min==max){					//當min=max時，min或max值放到新序列
			new_array[i] = a[min];
		}
		a = new_array;					//新序列assign到a array
		return a;
			
		}
	
	public static void printSorted(int[] sorted) {
		int i;
    	String output = "";
    	for (i=0; i<sorted.length; i++) {
    		output += sorted[i] + ",";
    	}
    	if (output != ""){
    		output = output.substring(0, output.length() - 1);
    	}
    	System.out.println(output);
	}

	public static void main(String[] args) {
		InclassSort ics = new InclassSort();
		printSorted(ics.sort(new int[]{1,3,6})); // expected output: 6,1,3 
		printSorted(ics.sort(new int[]{1,2,3,4,5,6})); // expected output: 6,1,5,2,4,3
		printSorted(ics.sort(new int[]{7,6,3,5,1,2,9})); // expected output: 9,1,7,2,6,3,5
		printSorted(ics.sort(new int[]{100,1,10,8,6,2,5,10,1})); // expected output: 100,1,10,1,10,2,8,5,6
	}
}
