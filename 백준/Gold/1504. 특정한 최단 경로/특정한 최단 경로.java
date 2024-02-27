import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main {
	
	// 정점 개수
	static int N;
	// 간선 개수
	static int E;
	// 정점 정보
	static ArrayList<ArrayList<int[]>> list;
	// 거쳐야하는 정점 두개
	static int nodeA;
	static int nodeB;
	
	// 거쳐야하는 노드가 두개 있기 때문에
	// 1->A의 최단거리 A->B의 최단거리 B->N의 최단거리를 더한 것 과 
	// 1->B의 최단거리 B->A의 최단거리 A->N의 최단거리를 더한 것을 비교해
	// 더 작은 것이 최종 최단 거리이다
	static int[] min;
	
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		E = Integer.parseInt(st.nextToken());
		list = new ArrayList<>();
		nodeA= 0;
		nodeB= 0;
		for(int i = 0;i<=N;i++) {
			list.add(new ArrayList<>());
		}
		for(int i = 0;i<E;i++) {
			st = new StringTokenizer(br.readLine());
			int u = Integer.parseInt(st.nextToken());
			int v = Integer.parseInt(st.nextToken());
			int w = Integer.parseInt(st.nextToken());
			
			list.get(u).add(new int[] {v, w});
			list.get(v).add(new int[] {u, w});
		}
		
		st = new StringTokenizer(br.readLine());
		nodeA = Integer.parseInt(st.nextToken());
		nodeB = Integer.parseInt(st.nextToken());
		 // 입력 완료
		
		// 1->A->B->N
		int total1 = 0;
		total1 += run(1, nodeA);
		total1 += run(nodeA, nodeB);
		total1 += run(nodeB, N);
		
		// 1->B->A->N
		int total2 = 0;
		total2 += run(1, nodeB);
		total2 += run(nodeB, nodeA);
		total2 += run(nodeA, N);
		
		if(total1>=200000000 && total2>=200000000) System.out.println(-1);
		else System.out.println(Math.min(total1, total2));

	}
	
	// 기본 다익스트라
	static int run(int start, int end) {
		
		min = new int[N+1];
		for(int i = 0;i<=N;i++) {
			min[i] = 200000000; // 거리가 될 수 있는 최대값
		}
		
		PriorityQueue<int[]> pq = new PriorityQueue<int[]>(new Comparator<int[]>() {
			@Override
			public int compare(int[] o1, int[] o2) {
				return o1[1]-o2[1];
			}
		});
		
		// 시작 노드에서 시작
		pq.add(new int[] {start, 0});
		min[start] = 0;
		
		while(!pq.isEmpty()) {
			int[] temp = pq.poll();
			int node = temp[0];
			int len = temp[1];
			
			// 최단거리 아니면 건너뛰기 -> 최적화
			if(len>min[node]) continue;
			
			for(int[] next : list.get(node)) {
				int nextNode = next[0];
				int nextLen = next[1];
				
				if(len+nextLen<min[nextNode]) {
					min[nextNode] = len+nextLen;
					pq.add(new int[] {nextNode, min[nextNode]});
				}
			}
		}
		
		return min[end];
		
	}
}