import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main {
	
	// 도시의 개수
	static int N;
	// 버스의 개수
	static int M;
	// 버스 정보
	static ArrayList<ArrayList<int[]>> list;
	// 출발지와 도착지 도시 번호
	static int start;
	static int arrive;
	// 최소비용
	static int[] min;
	// 방문체크 배열
	static boolean[] visited;
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.parseInt(br.readLine());
		M = Integer.parseInt(br.readLine());
		list = new ArrayList<>();
		min = new int[N+1];
		visited = new boolean[N+1];
		for(int i = 0;i<=N;i++) {
			list.add(new ArrayList<>());
			min[i] = Integer.MAX_VALUE;
		}
		for(int i = 0;i<M;i++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			int u = Integer.parseInt(st.nextToken());
			int v = Integer.parseInt(st.nextToken());
			int w = Integer.parseInt(st.nextToken());
			list.get(u).add(new int[] {v, w});
		}
		StringTokenizer st = new StringTokenizer(br.readLine());
		start = Integer.parseInt(st.nextToken());
		arrive = Integer.parseInt(st.nextToken());
		
		// 최소비용인 것부터 처리하기 위해 우선순위 큐 사용 -> 시간 단축
		PriorityQueue<int[]> pq = new PriorityQueue<int[]>(new Comparator<int[]>() {
			@Override
			public int compare(int[] o1, int[] o2) {
				return o1[1]-o2[1];
			}
		});
		
		pq.add(new int[] {start, 0});
		min[start] = 0;
		int count = 0;
		while(!pq.isEmpty()) {
			count++;
			int[] temp = pq.poll();
			int num = temp[0];
			int time = temp[1];
            
            // 한번 방문한 정점은 다신 방문하지 않기 위해 방문 체크 -> 시간 단축
			if(visited[num]) continue;
			visited[num] = true;
			
			for(int[] next : list.get(num)) {
				int nextNum = next[0];
				int nextTime = next[1];
				if(time+nextTime < min[nextNum]) {
					min[nextNum] = time+nextTime;
					pq.add(new int[] {nextNum, time+nextTime});
				}
			}
			
		}
		
		System.out.println(min[arrive]);
		
	}
}