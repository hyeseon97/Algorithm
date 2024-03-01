import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main {
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		int N = Integer.parseInt(st.nextToken());
		int K = Integer.parseInt(st.nextToken());
		int[] dist = new int[200001];
		
		PriorityQueue<int[]> q = new PriorityQueue<>(new Comparator<int[]>() {
			@Override
			public int compare(int[] o1, int[] o2) {
				return o1[1]-o2[1];
			}
		});
		
		Arrays.fill(dist, Integer.MAX_VALUE);
		dist[N] = 0;
		q.add(new int[] {N, 0});
		
		while(!q.isEmpty()) {
			int[] temp = q.poll();
			int x = temp[0];
			int time = temp[1];
			
//			System.out.println(x + " " + time);
			
			if(x==K) {
				break;
			}
			
			if(x>0) {
				if(time+1<dist[x-1]) {
					dist[x-1] = time+1;
					q.add(new int[] {x-1, time+1});
				}
			}
			
			if(x<100000) {
				if(time+1<dist[x+1]) {
					dist[x+1] = time+1;
					q.add(new int[] {x+1, time+1});
				}
			}
			
			if(x<=100000) {
				if(time<dist[x*2]) {
					dist[x*2] = time;
					q.add(new int[] {x*2, time});
				}
			}
			
		}
		
		System.out.println(dist[K]);
		
	}
}