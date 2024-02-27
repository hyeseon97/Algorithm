import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
	
	// 정점개수
	static int V;
	// 간선개수
	static int E;
	// 간선 정보 저장
	// {다음 정점, 가중치}
	static ArrayList<ArrayList<int[]>> list;
	// 시작 정점
	static int start;
	// 최단 경로 저장
	static int[] min;
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		StringTokenizer st = new StringTokenizer(br.readLine());
		V = Integer.parseInt(st.nextToken());
		E = Integer.parseInt(st.nextToken());
		list = new ArrayList<>();
		start = Integer.parseInt(br.readLine());
		min = new int[V+1];
		for(int i = 0;i<=V;i++) {
			list.add(new ArrayList<>());
			min[i] = Integer.MAX_VALUE;
		}
		
		for(int i = 0;i<E;i++) {
			st = new StringTokenizer(br.readLine());
			int u = Integer.parseInt(st.nextToken());
			int v = Integer.parseInt(st.nextToken());
			int w = Integer.parseInt(st.nextToken());
			list.get(u).add(new int[] {v, w});
		}
		
		PriorityQueue<int[]> q = new PriorityQueue<int[]>(new Comparator<int[]>() {
			@Override
			public int compare(int[] o1, int[] o2) {
				return o1[1]-o2[1];
			}
		});
		q.add(new int[] {start, 0});
		min[start] = 0;
		
		while(!q.isEmpty()) {
			int[] temp = q.poll();
			int num = temp[0];
			int len = temp[1];
            
            if(len>min[num]) continue;
			
			for(int[] next : list.get(num)) {
				int nextNum = next[0];
				int w = next[1];
				if(len+w<min[nextNum]) {
					q.add(new int[] {nextNum, len+w});
					min[nextNum] = len+w;
				}
			}
		}
		
		for(int i = 1;i<=V;i++) {
			if(min[i]==Integer.MAX_VALUE) sb.append("INF\n");
			else sb.append(min[i] + "\n");
		}
		
		System.out.println(sb);
	}
}