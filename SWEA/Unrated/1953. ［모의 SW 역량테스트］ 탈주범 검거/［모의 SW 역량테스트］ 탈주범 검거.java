import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Solution {
	
	// 맵의 크기 N*M, 맨홀뚜껑위치 (R, C), 탈출후소요시간 L
	static int N;
	static int M;
	static int R;
	static int C;
	static int L;
	
	// 맵
	static int[][] map;
	
	// 방향
	// 상 좌 하 우
	static int[] dr = {-1, 0, 1, 0};
	static int[] dc = {0, -1, 0, 1};
	
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		int T = Integer.parseInt(br.readLine());
		for(int t = 1;t<=T;t++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			N = Integer.parseInt(st.nextToken());
			M = Integer.parseInt(st.nextToken());
			R = Integer.parseInt(st.nextToken());
			C = Integer.parseInt(st.nextToken());
			L = Integer.parseInt(st.nextToken());
			
			map = new int[N][M];
			for(int i = 0;i<N;i++) {
				st = new StringTokenizer(br.readLine());
				for(int j = 0;j<M;j++) {
					map[i][j] = Integer.parseInt(st.nextToken());
				}
			}
			
			int count = 0;
			
			// BFS로 탐색
			Queue<int[]> q = new LinkedList<>();
			q.add(new int[] {R, C, 1});
			boolean[][] visited = new boolean[N][M];
			visited[R][C] = true;
			
			while(!q.isEmpty()) {
				int[] temp = q.poll();
				int r = temp[0];
				int c = temp[1];
				int time = temp[2];
				
				// 시간이 L이 초과했을 때 그만 멈추고 빠져나가기
				if(time > L) break;
				
				// 위치할 수 있는 곳 카운트
				count++;
				
				// 4방향에 대해
				for(int d = 0;d<4;d++) {
					int nr = r+dr[d];
					int nc = c+dc[d];
					// 맵을 벗어나면 건너뛰기
					if(nr<0 || nc<0 || nr>=N || nc>=M || visited[nr][nc]) continue;
					// 현재터널에서 d방향으로 나갈 수 없을 때 건너뛰기
					if(!InAndOut(map[r][c], d)) continue;
					// 다음터널로 (d+2)%4방향으로 들어갈 수 없을 때 건너뛰기
					if(!InAndOut(map[nr][nc], (d+2)%4)) continue;
					
					q.add(new int[] {nr, nc, time+1});
					visited[nr][nc] = true;
				}
			}
			
			sb.append("#" + t + " " + count + "\n");
		}
		
		System.out.println(sb);
		
	}
	
	// shape에 따른 dir에 대해 들어오고 나갈 수 있는지 판별하는 메서드
	static boolean InAndOut(int shape, int dir) {
		if(dir == 0) {
			if(shape==1 || shape==2 || shape==4 || shape==7) return true;
			else return false;
		} else if(dir == 1) {
			if(shape==1 || shape==3 || shape==6 || shape==7) return true;
			else return false;
		} else if(dir == 2) {
			if(shape==1 || shape==2 || shape==5 || shape==6) return true;
			else return false;
		} else {
			if(shape==1 || shape==3 || shape==4 || shape==5) return true;
			else return false;
		}
	}
	
}