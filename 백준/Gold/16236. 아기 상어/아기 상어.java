import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
	
	// 공간 크기
	static int N;
	// 공간
	static int[][] sea;
	// 상어 위치
	static int sharkR;
	static int sharkC;
	// 물고기 총 수
	static int total;
	// 상어 크기
	static int size;
	// 상어가 먹은 물고기 수
	static int count;
	// 시간
	static int time;
	// 먹을 수 있는 물고기 임시 저장 우선순위 큐
	static PriorityQueue<int[]> pq;
	// 먹을 수 있는 물고기가 있는지 여부
	static boolean eat;
	
	static int[] dr = {-1, 0, 0, 1};
	static int[] dc = {0, -1, 1, 0};
	
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.parseInt(br.readLine());
		sea = new int[N][N];
		sharkR = 0; sharkC = 0;
		for(int i = 0;i<N;i++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			for(int j = 0;j<N;j++) {
				sea[i][j] = Integer.parseInt(st.nextToken());
				if(sea[i][j]==9) {
					sharkR = i;
					sharkC = j;
					sea[i][j] = 0;
				} else if(sea[i][j]!=0 && sea[i][j]!=9) {
					total++;
				}
			}
		} // 입력 끝
		
		size = 2;
		count = 0;
		time = 0;
		
		while(true) {
			
			// 1. BFS를 진행하면서 같은 거리에 있으면서 먹을 수 있는 물고기들을 PQ에 넣는다
			// 2. 거리가 달라지면 PQ에 있는 물고기들 중 우선순위 기준에 따라 맨앞의 물고기 꺼내서 먹는다
			//                               (위에 있고 -> 왼쪽에 있고)
			// 3. 물고기를 먹을 때 다음의 처리를 해준다
			//    - 물고기 수 감소시키고, 먹을 물고기 수 증가시키고
			//    - 상어 위치 옮기고, 이동한 만큼 시간 더해주고
			//    - sea 배열에서 물고기 없애고(0으로 저장), 먹은 여부 true로 바꾸기
			//    - 먹은 후 먹은 물고기 개수가 상어 크기와 같으면 크기 증가시키고 먹은 물고기 개수 초기화
			// 4. 종료시점
			//    - 1-3 시작 전에 물고기 총 개수가 0이면 종료
			//    - 1-3 끝난 후 먹은 여부 false이면 종료
			
			
			if(total == 0) {
				break;
			}
			
			eat = false;
			searchFish();
			
			if(!pq.isEmpty()) {
				eatFish();
				eat = true;
			}
			
			if(!eat) {
				break;
			}
			
		}
		
		System.out.println(time);
		
	}
	
	static void searchFish() {
		
		Queue<int[]> q = new LinkedList<>();
		q.add(new int[] {sharkR, sharkC, 0});
		boolean[][] visited = new boolean[N][N];
		visited[sharkR][sharkC] = true;
		
		definePQ();
		
		while(!q.isEmpty()) {
			int[] temp = q.poll();
			int r = temp[0];
			int c = temp[1];
			int len = temp[2];
			
			// 거리가 달라졌을 때 먹을 수 있는 물고기가 저장되어있으면 종료
			if(!pq.isEmpty() && pq.peek()[2]!=len) {
				break;
			}
			
			// 먹을 수 있는 물고기가 나타났을 때 pq에 임시저장
			if(sea[r][c]!=0 && size>sea[r][c]) {
				pq.add(new int[] {r, c, len});
			}
			
			for(int d = 0;d<4;d++) {
				int nr = r+dr[d];
				int nc = c+dc[d];
				if(nr<0 || nc<0 || nr>=N || nc>=N || visited[nr][nc]) continue;
				if(size<sea[nr][nc]) continue;
				q.add(new int[] {nr, nc, len+1});
				visited[nr][nc] = true;
			}
		}
		
	}
	
	static void eatFish() {
		
		int[] fish = pq.poll();
		int r = fish[0];
		int c = fish[1];
		int len = fish[2];
		
		total--; count++;
		sharkR = r; sharkC = c;
		time += len;
		sea[r][c] = 0;

		if(size == count) {
			size++;
			count = 0;
		}
		
	}
	
	static void definePQ() {
		
		pq = new PriorityQueue<>(new Comparator<int[]>() {
			@Override
			public int compare(int[] o1, int[] o2) {
				if(o1[0]<o2[0]) {
					return -1;
				} else if(o1[0]==o2[0]) {
					if(o1[1]<o2[1]) {
						return -1;
					} else {
						return 1;
					}
				} else {
					return 1;
				}
			}
		});
		
	}
}