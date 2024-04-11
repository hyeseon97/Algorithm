import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main {
	
	// 지역 개수
	static int N;
	// 수색 범위
	static int M;
	// 길의 개수
	static int R;
	
	// 각 구역의 아이템 수
	static int[] item;
	
	// 길 정보
	static ArrayList<ArrayList<int[]>> line;
	
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		R = Integer.parseInt(st.nextToken());
		item = new int[N+1];
		st = new StringTokenizer(br.readLine());
		for(int i = 1;i<=N;i++) {
			item[i] = Integer.parseInt(st.nextToken());
		}
		line = new ArrayList<>();
		for(int i = 0;i<=N;i++) {
			line.add(new ArrayList<>());
		}
		for(int i = 0;i<R;i++) {
			st = new StringTokenizer(br.readLine());
			int u = Integer.parseInt(st.nextToken());
			int v = Integer.parseInt(st.nextToken());
			int w = Integer.parseInt(st.nextToken());
			
			line.get(u).add(new int[] {v, w});
			line.get(v).add(new int[] {u, w});
		}
		
		// 최대값
		int max = 0;
		
		// 모든 노드에서 시작해보기
		for(int start = 1;start<=N;start++) {
			
			
			PriorityQueue<int[]> pq = new PriorityQueue<int[]>(new Comparator<int[]>() {

				@Override
				public int compare(int[] o1, int[] o2) {
					return o1[1]-o2[1];
				}
				
			});
			
			pq.add(new int[] {start, 0});
			int total = item[start];
			boolean[] visited = new boolean[N+1];
			visited[start] = true;
			
			while(!pq.isEmpty()) {
				int[] temp = pq.poll();
				int node = temp[0];
				int len = temp[1];
				
				for(int[] next : line.get(node)) {
					int nextNode = next[0];
					int l = next[1];
					
					// 범위가 넘어갈 경우에만 건너뛰기
					if(len+l > M) continue;
					
					// 다음 큐에 넣기
					pq.add(new int[] {nextNode, len+l});
					// 방문하지 않은 노드일 경우에만 총합에 더해주기
					if(!visited[nextNode]) {
						total += item[nextNode];					
					}					
					// 방문했으니까 체크
					visited[nextNode] = true;
					
				}
			}
			max = Math.max(max, total);
			
		}
		System.out.println(max);
	}
}