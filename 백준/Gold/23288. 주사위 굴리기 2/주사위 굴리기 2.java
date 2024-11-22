import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
	
	// 지도 크기
	static int N;
	static int M;
	
	// 이동 횟수
	static int K;
	
	// 지도 각 칸의 정수
	static int[][] B;
	
	// 지도 각 칸에서 이동할 수 있는 수
	static int[][] C;
	
	// 주사위 배열
	// 1, 1이 바닥에 있는 숫자 / 0, 1 북 / 1, 2 동 / 2, 1 남 / 1, 0 서 / 0, 0이 위에 있는 숫자
	static int[][] dice;
	
	// 주사위의 현재위치와 방향
	static int Row;
	static int Col;
	static int Dir;
	
	// 점수
	static int score;
	
	static int[] dr = {0, 1, 0, -1};
	static int[] dc = {1, 0, -1, 0};
	
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		K = Integer.parseInt(st.nextToken());
		B = new int[N+1][M+1];
		C = new int[N+1][M+1];
		for(int i = 1;i<=N;i++) {
			st = new StringTokenizer(br.readLine());
			for(int j = 1;j<=M;j++) {
				B[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		initCArr();
		// 입력 끝
		
		// 주사위 초기화
		Row = 1; Col = 1; Dir = 0;
		dice = new int[][] {{1, 2, 0}, {4, 6, 3}, {0, 5, 0}};
		
		// 이동횟수만큼 반복
		score = 0;
		for(int k = 0;k<K;k++) {
			
			// 이동방향으로 한칸 굴리기
			move();
			
			// 점수 획득
			score += (B[Row][Col] * C[Row][Col]);
			
			// 도착칸에 대해 방향 정하기
			if(dice[1][1] > B[Row][Col]) {
				Dir = (Dir+1)%4;
			} else if(dice[1][1] < B[Row][Col]) {
				Dir = Dir-1==-1?3:Dir-1;
			}
			
		}
		
		System.out.println(score);
		
	}
	
	static void print() {
		for(int i = 1;i<=N;i++) {
			for(int j = 1;j<=M;j++) {
				System.out.print(C[i][j] + " ");
			}
			System.out.println();
		}
	}
	
	static void move() {
		
		// 만약 갈 수 없으면 반대방향으로 돌기
		if(Row+dr[Dir]<1 || Col+dc[Dir]<1 || Row+dr[Dir]>N || Col+dc[Dir]>M) {
			Dir = (Dir+2)%4;
		}
		
		// 현재 방향으로 이동
		Row = Row+dr[Dir];
		Col = Col+dc[Dir];
		
		// 주사위 배열 수정
		int temp = dice[0][0];
		dice[0][0] = dice[1-dr[Dir]][1-dc[Dir]]; // 바닥에서 진행방향의 반대방향의 옆에 있는 숫자가 위로 이동
		dice[1-dr[Dir]][1-dc[Dir]] = dice[1][1]; // 바닥부터 돌면서 한칸씩 이동
		dice[1][1] = dice[1+dr[Dir]][1+dc[Dir]];
		dice[1+dr[Dir]][1+dc[Dir]] = temp;
		
	}
	
	static void initCArr(){
		
		// 각 칸을 시작으로 BFS 실행해 같은 숫자로 이어진 갈 수 있는 곳 세서 해당 칸들에 다 저장
		boolean[][] visited = new boolean[N+1][M+1];
		Queue<int[]> numList = new LinkedList<>();
		
		for(int i = 1;i<=N;i++) {
			for(int j = 1;j<=M;j++) {
				if(visited[i][j]) continue;
				
				Queue<int[]> bfs = new LinkedList<>();
				bfs.add(new int[] {i, j});
				visited[i][j] = true;
				int num = B[i][j];
				int count = 0;
				
				while(!bfs.isEmpty()) {
					int[] temp = bfs.poll();
					int r = temp[0];
					int c = temp[1];
					numList.add(new int[] {r, c});
					count++;
					
					for(int d = 0;d<4;d++) {
						int nr = r+dr[d];
						int nc = c+dc[d];
						if(nr<1 || nc<1 || nr>N || nc>M || visited[nr][nc] || B[nr][nc] != num) continue;
						bfs.add(new int[] {nr, nc});
						visited[nr][nc] = true;
					}
				}
				
				while(!numList.isEmpty()) {
					int[] temp = numList.poll();
					C[temp[0]][temp[1]] = count;
				}
				
			}
		}
	}

}