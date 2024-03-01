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
		
		// 시간이 빠른 순서로 재정렬
		PriorityQueue<int[]> q = new PriorityQueue<>(new Comparator<int[]>() {
			@Override
			public int compare(int[] o1, int[] o2) {
				return o1[1]-o2[1];
			}
		});
		q.add(new int[] {N, 0});
		
		// 최단시간을 모두 최대값으로 초기화
		Arrays.fill(dist, Integer.MAX_VALUE);
		dist[N] = 0;
		
		while(!q.isEmpty()) {
			int[] temp = q.poll();
			int x = temp[0];
			int time = temp[1];
			
			// 다익스트라로 최단시간 구하기
			// 동생에게 도착했을 때 종료하기
			if(x==K) {
				break;
			}
			
			// -1로 이동할때는 현재 시간에서 1초경과한 시간이 -1 위치의 최단시간보다 짧을때 가기
			// 0미만의 좌표로 이동하는것은 의미없으므로 0초과일때만 처리하기
			if(x>0) {
				if(time+1<dist[x-1]) {
					dist[x-1] = time+1;
					q.add(new int[] {x-1, time+1});
				}
			}
			
			// +1로 이동할때는 현재 시간에서 1초경과한 시간이 +1 위치의 최단시간보다 짧을때 가기
			// 100000초과의 좌표로 이동하는것은 의미없으므로 100000미만일때만 처리하기
			if(x<100000) {
				if(time+1<dist[x+1]) {
					dist[x+1] = time+1;
					q.add(new int[] {x+1, time+1});
				}
			}
			
			// *2로 이동할때는 현재 시간으로 바로 비교하기 (*2는 0초가 걸리므로)
			// *2 위치의 최단시간보다 짧을때 가기
			// 100000 초과일때 *2로 이동하는것은 의미없으므로 100000이하일때만 처리하기
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
