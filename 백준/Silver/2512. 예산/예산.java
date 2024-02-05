import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
	static int N;
	static int[] want;
	static int total;
	
	static int max;
	static int min;
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.parseInt(br.readLine());
		want = new int[N];
		StringTokenizer st = new StringTokenizer(br.readLine());
		min = 1;
		max = 0;
		for(int i = 0;i<N;i++) {
			want[i] = Integer.parseInt(st.nextToken());
			max = Math.max(max, want[i]);
		}
		total = Integer.parseInt(br.readLine());
		
		int mid = 0;
		int sum = 0;
		while(min<=max) {
			
			mid = (min+max)/2;
			sum = 0;
			for(int i = 0;i<N;i++) {
				if(want[i]>mid) {
					sum += mid;
				} else {
					sum += want[i];
				}
			}
			
			if(sum<total) {
				min = mid+1;
			} else if(sum>total) {
				max = mid-1;
			} else {
				break;
			}
		}
		
		if(sum>total) {
			System.out.println(mid-1);
		} else {
			System.out.println(mid);
		}
		
	}
}