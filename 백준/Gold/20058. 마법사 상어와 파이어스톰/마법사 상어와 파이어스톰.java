import java.io.*;
import java.util.*;

public class Main {
	
	// 격자판 크기
	static int N;
	
	// 마법 횟수
	static int Q;
	
	// 얼음판
	static int[][] board;
	
	// 임시얼음판
	static int[][] tempBoard;
	
	// 남아있는 얼음 합
	static int total;
	// 가장 큰 덩어리
	static int max;
	// BFS 방문배열
	static boolean[][] visited;
	
	static int[] dr = {-1, 0, 1, 0};
	static int[] dc = {0, -1, 0, 1};
	
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		int n = Integer.parseInt(st.nextToken());
		N = (int)Math.pow(2, n);
		int Q = Integer.parseInt(st.nextToken());
		board = new int[N][N];
		for(int i = 0;i<N;i++) {
			st = new StringTokenizer(br.readLine());
			for(int j = 0;j<N;j++) {
				board[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		
		st = new StringTokenizer(br.readLine());
		// 마법횟수만큼 반복
		for(int q = 0;q<Q;q++) {
			int L = Integer.parseInt(st.nextToken());
			int len = (int)Math.pow(2, L);
			
			tempBoard = new int[N][N];
			// 얼음판을 부분격자로 나눠서 90도 회전시키고 임시얼음판에 저장
			// i, j는 각 격자의 왼쪽윗부분
			for(int i = 0;i<N;i+=len) {
				for(int j = 0;j<N;j+=len) {
					// 회전하고
					rotation(i, j, len);
					// 임시에 있는 것을 진짜로 옮기기
					move(i, j, len);
				}
			}
			
			// 인접한 얼음이 2개이하인 곳 얼음 하나 녹이기
			melt();
		}
		
		// 남아있는 얼음 합 구하면서 BFS로 가장 큰 덩어리 구하기
		total = 0;
		max = 0;
		visited = new boolean[N][N];
		for(int i = 0;i<N;i++) {
			for(int j = 0;j<N;j++) {
				total += board[i][j];
				if(visited[i][j] || board[i][j] == 0) continue;
				
				int count = BFS(i, j);
				max = Math.max(max, count);
			}
		}
		
		System.out.println(total);
		System.out.println(max);
		
	}
	
	static void rotation(int startR, int startC, int len) {
		
		// 임시얼음판의 인덱스
		int r = startR;
		int c = startC+len-1;
		
		for(int i = startR;i<startR+len;i++) {
			for(int j = startC;j<startC+len;j++) {
				tempBoard[r++][c] = board[i][j];
				if(r == startR+len) r = startR;
			}
			c--;
		}
	}
	
	static void move(int startR, int startC, int len) {
		
		for(int i = startR;i<startR+len;i++) {
			for(int j = startC;j<startC+len;j++) {
				board[i][j] = tempBoard[i][j];
			}
		}
	}
	
	static void melt() {
		
		// 각 배열 돌면서 인접한 얼음이 2개 이하여서 녹여야할 곳 임시로 저장하고 나중에 한꺼번에 녹이기
		Queue<int[]> tempQueue = new LinkedList<>();
		
		for(int i = 0;i<N;i++) {
			for(int j = 0;j<N;j++) {
				// 이미 얼음이 없으면 건너뛰기
				if(board[i][j] == 0) continue;
				
				int count = 0;
				for(int d = 0;d<4;d++) {
					int nr = i+dr[d];
					int nc = j+dc[d];
					if(nr<0 || nc<0 || nr>=N || nc>=N || board[nr][nc] == 0) continue;
					count++;
				}
				
				if(count <= 2) {
					tempQueue.add(new int[] {i, j});
				}
			}
		}
		
		// 임시큐에 있는 칸 얼음 녹이기
		while(!tempQueue.isEmpty()) {
			int[] temp = tempQueue.poll();
			int r = temp[0];
			int c = temp[1];
			board[r][c]--;
		}
	}
	
	static int BFS(int i, int j) {
		
		int count = 0;
		Queue<int[]> q = new LinkedList<>();
		q.add(new int[] {i, j});
		visited[i][j] = true;
		count++;
		
		while(!q.isEmpty()) {
			int[] temp = q.poll();
			int r = temp[0];
			int c = temp[1];
			
			for(int d = 0;d<4;d++) {
				int nr = r+dr[d];
				int nc = c+dc[d];
				if(nr<0 || nc<0 || nr>=N || nc>=N || visited[nr][nc] || board[nr][nc] == 0) continue;
				q.add(new int[] {nr, nc});
				visited[nr][nc] = true;
				count++;
			}
		}
		
		return count;
	}
}