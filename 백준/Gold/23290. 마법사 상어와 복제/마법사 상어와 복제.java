import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
	
	// 물고기 수
	static int M;
	
	// 연습횟수
	static int S;
	
	// 상어위치
	static int R;
	static int C;
	
	// 물고기가 있는 격자
	static Queue<Integer>[][] board;
	
	// 복제시킨 물고기 큐
	static Queue<int[]> copyFish;
	
	// 물고기 이동시킬때 임시 큐에 저장하고 다 이동시키면 격자판에 저장
	static Queue<int[]> tempFish;
	
	// 냄새배열
	static int[][] smell;

	// 물고기 방향 배열
	static int[] drF = {0, 1, 1, 1, 0, -1, -1, -1};
	static int[] dcF = {-1, -1, 0, 1, 1, 1, 0, -1};
	
	// 상어 방향 배열
	static int[] drS = {-1, 0, 1, 0};
	static int[] dcS = {0, -1, 0, 1};
	
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		M = Integer.parseInt(st.nextToken());
		S = Integer.parseInt(st.nextToken());
		board = new LinkedList[5][5];
		smell = new int[5][5];
		for(int i = 0;i<M;i++) {
			st = new StringTokenizer(br.readLine());
			int r = Integer.parseInt(st.nextToken());
			int c = Integer.parseInt(st.nextToken());
			int d = Integer.parseInt(st.nextToken());
			d = (9-d)==8?0:(9-d); // 방향을 반시계로 변환
			
			if(board[r][c] == null) {
				board[r][c] = new LinkedList<>();
			}
			board[r][c].add(d);
		}
		st = new StringTokenizer(br.readLine());
		R = Integer.parseInt(st.nextToken());
		C = Integer.parseInt(st.nextToken());
		
		// 연습횟수만큼 반복
		for(int s = 1;s<=S;s++) {
			
			// 1. 물고기 복제 마법
			// 2. 물고기 이동
			// 물고기 복제해서 임시 큐에 넣고 물고기 이동시켜서 반대쪽 격자판에 저장
			magicAndMoveFish();		
			
			// 3. 상어 이동
			// 반대쪽으로 넘어간 격자판에서 물고기 잡기
			moveShark();				
			
			// 4. 2회전 연습의 냄새 삭제 = 모든 냄새 배열 -1
			removeSmell();
			
			// 5. 복제한 물고기 저장
			saveFish();	
			
		}
		
		// 남은 물고기 계산
		int answer = 0;
		for(int i = 1;i<=4;i++) {
			for(int j = 1;j<=4;j++) {
				if(board[i][j] == null) continue;
				answer += board[i][j].size();
			}
		}
		
		System.out.println(answer);
		
		
	}

    static void magicAndMoveFish() {
		
		copyFish = new LinkedList<>();
		tempFish = new LinkedList<>();
		
		for(int i = 1;i<=4;i++) {
			for(int j = 1;j<=4;j++) {
				if(board[i][j] == null) continue;
				
				// 현재 칸에 물고기가 있을 때
				while(!board[i][j].isEmpty()) {
					int d = board[i][j].poll();
					
					// 복제 큐에 먼저 바로 저장
					copyFish.add(new int[] {i, j, d});
					
					// 이동시켜서 임시 큐에 저장
					boolean can = false;
					// 현재 방향을 시작으로 8방향 중 갈 수 있는 방향 찾기
					for(int dir = 0;dir<8;dir++) {
						int nr = i+drF[d];
						int nc = j+dcF[d];
						// (배열 밖을 벗어나거나 냄새가 있는 곳이거나 상어가 있는 곳) 이 아니면 갈 수 있으므로 for문 종료
						if(!(nr<1 || nc<1 || nr>4 || nc>4 || smell[nr][nc]>=1 || (nr==R && nc==C))) {
							can = true;
							break;
						}
						d = (d+1)%8;
					}
					
					// 갈 수 있으면 이동하고 아니면 현재 위치 그대로 임시 큐에 저장
					if(can) {
						int nr = i+drF[d];
						int nc = j+dcF[d];
						tempFish.add(new int[] {nr, nc, d});
					} else {
						tempFish.add(new int[] {i, j, d});
					}
				} // 현재 칸 물고기 순회 끝
			}
		} // 4*4배열 순회 끝
		
		// 모든 물고기 이동 위치 임시큐에 저장했으니 격자판에 저장
		board = new LinkedList[5][5];
		while(!tempFish.isEmpty()) {
			int[] temp = tempFish.poll();
			int r = temp[0];
			int c = temp[1];
			int d = temp[2];
			if(board[r][c] == null) {
				board[r][c] = new LinkedList<>();
			}
			board[r][c].add(d);
		}
		
	}
	
	static int max;
	static String maxRoute;
	static boolean[][] visited;
	
	static void moveShark() {
		
		// DFS로 상어가 갈 이동경로 찾기
		// 우선순위대로 먼저 갈 것이기 때문에 물고기 수가 많을때만 갱신
		max = -1;
		maxRoute = "";
		visited = new boolean[5][5];
		dfs(R, C, "", 0, 0);
		
		// 정해진 경로로 상어 이동하고 물고기가 있으면 물고기는 삭제하고 냄새 남기기
		StringTokenizer st = new StringTokenizer(maxRoute);
		while(st.hasMoreTokens()) {
			int dir = Integer.parseInt(st.nextToken());
			R += drS[dir];
			C += dcS[dir];
			if(board[R][C] != null) {
				board[R][C] = null;
				smell[R][C] = 3;				
			}
		}
		
	}
	
	static void dfs(int r, int c, String route, int seq, int count) {
		
		// 3칸 이동했을 때 갱신하기
		if(seq == 3) {
			if(max < count) {
				max = count;
				maxRoute = route;
			}
			return;
		}
		
		for(int d = 0;d<4;d++) {
			int nr = r+drS[d];
			int nc = c+dcS[d];
			// 배열 밖을 벗어나면 갈 수 없는 경로이므로 건너뛰기
			if(nr<1 || nc<1 || nr>4 || nc>4) continue;
			// 물고기가 없거나 이미 먹은 물고기면 count그대로 다음으로 이동, 있으면 더해줘서 이동
			if(board[nr][nc] == null || visited[nr][nc]) {
				dfs(nr, nc, route+" "+d, seq+1, count);
			} else {
				visited[nr][nc] = true;
				dfs(nr, nc, route+" "+d, seq+1, count+board[nr][nc].size());
				visited[nr][nc] = false;
			}
		}
	}

	static void removeSmell() {
		
		// 4*4배열 돌면서 -1해주기
		for(int i = 1;i<=4;i++) {
			for(int j = 1;j<=4;j++) {
				smell[i][j]--;
			}
		}
	}
	
	static void saveFish() {
		
		// 복제 큐에 넣어놨던 물고기 board에 저장
		while(!copyFish.isEmpty()) {
			int[] temp = copyFish.poll();
			int r = temp[0];
			int c = temp[1];
			int d = temp[2];
			if(board[r][c] == null) {
				board[r][c] = new LinkedList<>();
			}
			board[r][c].add(d);
		}
	}

}