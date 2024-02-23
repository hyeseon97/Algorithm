import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.StringTokenizer;

public class Solution {
	
	// 초기세포크기 N*M
	static int N;
	static int M;
	// 배양시간
	static int K;
	// 비활성 세포 큐
	static PriorityQueue<int[]> inactive;
	// 활성 세포 큐
	static PriorityQueue<int[]> active;
	// 방문체크
	static Set<String> visited;
	
	static int[] dr = {-1, 1, 0, 0};
	static int[] dc = {0, 0, -1, 1};
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		int T = Integer.parseInt(br.readLine());
		for(int t = 1;t<=T;t++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			N = Integer.parseInt(st.nextToken());
			M = Integer.parseInt(st.nextToken());
			K = Integer.parseInt(st.nextToken());
			
			// 우선순위큐를 비활성/활성 으로 진입했을때의 시간 + 생명력 순으로 정렬 기준 정하기
			inactive = new PriorityQueue<int[]>(new Comparator<int[]>() {
				@Override
				public int compare(int[] o1, int[] o2) {
					return (o1[2]+o1[3])-(o2[2]+o2[3]);
				}
			});
			active = new PriorityQueue<int[]>(new Comparator<int[]>() {
				@Override
				public int compare(int[] o1, int[] o2) {
					return (o1[2]+o1[3])-(o2[2]+o2[3]);
				}
			});
			visited = new HashSet<>();
			
			for(int i = 0;i<N;i++) {
				st = new StringTokenizer(br.readLine());
				for(int j = 0;j<M;j++) {
					int hp = Integer.parseInt(st.nextToken());
					if(hp>0) {
						inactive.add(new int[] {i, j, 0, hp});
						visited.add(i + " " + j);
					}
				}
			}
			
			// 한시간 뒤 번식할 세포 임시 저장
			Queue<int[]> breeding = new LinkedList<>();
			
			for(int time = 1;time<=K;time++) {
				
				// 번식 임시 큐에 있는거 비활성 큐로 넣기
				while(!breeding.isEmpty()) {
					inactive.add(breeding.poll());
				}
				
				// 1. 비활성에 있는 세포들 중 활성이 될 수 있는거 활성화 시키기
				//    -> 바로 활성 큐에 넣기
				// 2. 활성화 시킨 세포 한시간 뒤에 번식
				//    -> 임시 큐에 넣고 4방향 탐색 후 파워큰걸로 비활큐에 현재시간+1 해서 넣기 (왜? 한시간뒤 번식하는거니까)
				Queue<int[]> temp = new LinkedList<>();
				
				while(!inactive.isEmpty() && inactive.peek()[2]+inactive.peek()[3]==time) {
					// 활성화될 세포 꺼내기
					int[] cell = inactive.poll();
					active.add(new int[] {cell[0], cell[1], time, cell[3]});
					temp.add(new int[]{cell[0], cell[1], cell[3]});
				}
				// 임시 큐에 있는 것들을 4방향 탐색해서 map에 최대 생명력으로 저장
				// 좌표, 생명력
				Map<String, Integer> map = new HashMap<>();
				// 좌표만 저장
				List<String> list = new ArrayList<>();
				
				while(!temp.isEmpty()) {
					int[] cell = temp.poll();
					int hp = cell[2];
					
					// 4방향 탐색
					for(int d = 0;d<4;d++) {
						int nr = cell[0]+dr[d];
						int nc = cell[1]+dc[d];
						String location = nr + " " + nc;
						
						// 다음 좌표에 이미 세포가 존재할 때 건너뛰기
						if(visited.contains(location)) continue;
						// map에 이미 세포가 있으면 생명력 비교해서 더 큰거 넣기
						if(map.containsKey(location)) {
							int max = Math.max(map.get(location), hp);
							map.replace(location, max);
						} else {
							// 없으면 새로 넣기
							map.put(location, hp);
							list.add(location);
						}
					}
				}
				
				// 최대값으로 저장된 세포들 번식 큐에 넣기
				for(int i = 0;i<list.size();i++) {
					st = new StringTokenizer(list.get(i));
					int r = Integer.parseInt(st.nextToken());
					int c = Integer.parseInt(st.nextToken());
					breeding.add(new int[] {r, c, time+1, map.get(list.get(i))});
					visited.add(r + " " + c);
				}
				
				// 활성에 있는 세포들 중 죽는 것은 빼기
				while(!active.isEmpty() && active.peek()[2]+active.peek()[3]==time) {
					active.poll();
				}
				
			}
			
			sb.append("#" + t + " " + (inactive.size()+active.size()) + "\n");
			
		}
		
		System.out.println(sb);
	}
}