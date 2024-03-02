import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
	
	// 지도 크기
	static int N;
	// 승객 수
	static int M;
	// 초기 연료
	static int K;
	// 지도
	static int[][] map;
	// 택시 위치
	static int taxiR;
	static int taxiC;
	// 승객 위치와 도착 위치
	static List<int[]> people;
	// 태울 승객 임시 우선순위 큐
	static PriorityQueue<int[]> pq;
	
	static int[] dr = {-1, 1, 0, 0};
	static int[] dc = {0, 0, -1, 1};
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		K = Integer.parseInt(st.nextToken());
		map = new int[N][N];
		for(int i = 0;i<N;i++) {
			st = new StringTokenizer(br.readLine());
			for(int j = 0;j<N;j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		st = new StringTokenizer(br.readLine());
		taxiR = Integer.parseInt(st.nextToken())-1;
		taxiC = Integer.parseInt(st.nextToken())-1;
		people = new ArrayList<>();
		
		// 승객의 위치를 지도에 표시하기 위해 승객번호를 2번부터 해서 저장할 것임 (벽이 1이어서)
		for(int i = 0;i<=M+1;i++) {
			if(i<2) {
				people.add(new int[] {i, -1, -1, -1, -1});
			} else {
				st = new StringTokenizer(br.readLine());
				int peopleR = Integer.parseInt(st.nextToken())-1;
				int peopleC = Integer.parseInt(st.nextToken())-1;
				int arriveR = Integer.parseInt(st.nextToken())-1;
				int arriveC = Integer.parseInt(st.nextToken())-1;
				people.add(new int[] {i, peopleR, peopleC, arriveR, arriveC});
				map[peopleR][peopleC] = i;
			}
		}
		// 입력 끝
		
		
		// 1. 현재 택시 위치에서 가장 가까운 승객을 고른다
		//    - 가까운 승객 여러명일땐 가장 위 -> 가장 왼쪽 기준으로 뽑기
		//    - BFS로 가까운 승객부터 임시 배열에 넣고 거리가 증가할때 임시 배열에 승객이 있으면 진행
		// 2. 승객까지 이동 -> 도착지까지 이동
		//    - 승객에서 도착지까지 갈 때 이동거리 계산하면서 가기
		//    - 도착지에 도착하면 택시 위치 옮기고, 승객수 감소시키고, 승객 없애고, 연료 계산하기
		// 3. 종료시점
		//    - 승객이 남아있지 않을 때
		//    - 연료가 떨어졌을 때
		
		// 승객 수 만큼 반복문
		for(int drive = 0;drive<people.size()-2;drive++) {
			
			pickup();

			if(!pq.isEmpty()) {
				if(!move()) { // 연료가 떨어져서 더이상 운행할 수 없을 때
					System.out.println(-1);
					return;
				}
			} else { // 태울 수 있는 승객이 없을 때
				System.out.println(-1);
				return;
			}
			
		}
		
		System.out.println(K);
		
	}
	
	// 어떤 승객을 태울지 고르는 메서드
	static void pickup() {
		
		definePQ();
		
		Queue<int[]> q = new LinkedList<>();
		q.add(new int[] {taxiR, taxiC, 0});
		boolean[][] visited = new boolean[N][N];
		visited[taxiR][taxiC] = true;
		
		while(!q.isEmpty()) {
			int[] temp = q.poll();
			int r = temp[0];
			int c = temp[1];
			int len = temp[2];
			
			// 이동거리가 달라졌을 때 태울 승객이 있으면 종료
			if(!pq.isEmpty() && pq.peek()[3]!=len) {
				break;
			}
			
			// 승객을 만나면 PQ에 임시저장
			if(map[r][c]>=2) {
				pq.add(new int[] {map[r][c], r, c, len});
			}
			
			for(int d = 0;d<4;d++) {
				int nr = r+dr[d];
				int nc = c+dc[d];
				if(nr<0 || nc<0 || nr>=N || nc>=N || visited[nr][nc] || map[nr][nc]==1) continue;
				q.add(new int[] {nr, nc, len+1});
				visited[nr][nc] = true;
			}
		}
		
	}
	
	// 뽑힌 승객을 데려다주는 메서드
	static boolean move() {
		
		// 뽑힌 승객의 위치와 도착지 위치 정보
		int[] pick = pq.poll();
		int num = pick[0];
		int peopleR = pick[1];
		int peopleC = pick[2];
		int pickLen = pick[3];
		int arriveR = people.get(num)[3];
		int arriveC = people.get(num)[4];
		
		// 택시가 승객을 태우러 갈때동안 연료가 떨어졌을 때 false 반환
		if(K<pickLen) {
			return false;
		}
		
		K -= pickLen;
		
		Queue<int[]> q = new LinkedList<>();
		q.add(new int[] {peopleR, peopleC, 0});
		boolean[][] visited = new boolean[N][N];
		visited[peopleR][peopleC] = true;
		
		while(!q.isEmpty()) {
			int[] temp = q.poll();
			int r = temp[0];
			int c = temp[1];
			int len = temp[2];
			
			// 택시가 승객을 태우고 도착지로 가는동안 연료가 떨어졌을 때 false 반환
			if(K-len<0) {
				return false;
			}
			
			// 도착지에 도착했을 때 처리해주고 true 반환
			if(r==arriveR && c==arriveC) {
				taxiR = r;
				taxiC = c;
				map[peopleR][peopleC] = 0;
				M--;
				K += len;
				return true;
			}
			
			for(int d = 0;d<4;d++) {
				int nr = r+dr[d];
				int nc = c+dc[d];
				if(nr<0 || nc<0 || nr>=N || nc>=N || visited[nr][nc] || map[nr][nc]==1) continue;
				q.add(new int[] {nr, nc, len+1});
				visited[nr][nc] = true;
			}
		}
		
		// 이동할 수 있는 곳이 더이상 없는데 도착지에 도착을 못했을 경우이므로 false 반환
		return false;
		
	}
	
	// 우선순위 큐 기준대로 재정의하는 메서드
	static void definePQ() {
		
		pq = new PriorityQueue<>(new Comparator<int[]>() {
			@Override
			public int compare(int[] o1, int[] o2) {
				if(o1[1]==o2[1]) {
					return o1[2]-o2[2];
				}
				return o1[1]-o2[1];
			}
		});
	}
	
}