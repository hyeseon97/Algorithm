import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
	
	// 연구소 크기
	static int N;
	static int M;
	// 연구소
	static int[][] arr;
	// 바이러스 위치
	static List<int[]> virus;
	// 총 안전지역
	static int safeTotal;
	// 안전지역 최대값
	static int max;
	
	static int[] dr = {-1, 1, 0, 0};
	static int[] dc = {0, 0, -1, 1};
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		arr = new int[N][M];
		virus = new ArrayList<>();
		safeTotal = 0;
		for(int i = 0;i<N;i++) {
			st = new StringTokenizer(br.readLine());
			for(int j = 0;j<M;j++) {
				arr[i][j] = Integer.parseInt(st.nextToken());
				if(arr[i][j] == 2) {
					virus.add(new int[] {i, j});
				} else if(arr[i][j] == 0) {
					safeTotal++;
				}
			}
		} // 입력 끝
		
		// 벽 3개 세울거니까 뺴주기
		safeTotal -= 3;
		max = 0;
		wall(0, 0, 0);
		System.out.println(max);
		
	}
	
	// 바이러스 퍼뜨리기
	static void spread() {
		int safe = safeTotal;
		
		// 바이러스를 BFS로 큐에 넣고 퍼뜨리기
		// 퍼뜨릴때마다 안전지역(safe) 하나씩 감소시키기
		// BFS 종료 후 안전지역 최대값 갱신시키기
		Queue<int[]> q = new LinkedList<>();
		for(int i = 0;i<virus.size();i++) {
			q.add(new int[] {virus.get(i)[0], virus.get(i)[1]});
		}
		boolean[][] visited = new boolean[N][M];
		
		while(!q.isEmpty()) {
			int[] temp = q.poll();
			int r = temp[0];
			int c = temp[1];
			
			for(int d = 0;d<4;d++) {
				int nr = r+dr[d];
				int nc = c+dc[d];
				
				if(nr<0 || nc<0 || nr>=N || nc>=M || visited[nr][nc] || !(arr[nr][nc]==0)) continue;
				visited[nr][nc] = true;
				safe--;
				q.add(new int[] {nr, nc});
			}
		}
		
		max = Math.max(max, safe);
	}
	
	// 벽 세우기
	static void wall(int idx, int r, int c) {
		
		// 벽 3개 세웠을 때 바이러스 퍼뜨리기
		if(idx == 3) {
			spread();
			return;
		}
		
		// 현재 행과 열 이후부터 세울 수 있는 곳에 벽 세우기
		// 현재 행일때는 현재 열부터 반복문 돌리고
		// 그 다음부터는 0열부터 반복문 돌려야함
		// 마지막 열에 세울 수 있을 땐 다음행의 첫번째 열로 넘어가고
		// 그 외에는 현재 행의 다음 열로 넘어가기
		for(int i = r;i<N;i++) {
			int start = i==r?c:0;
			for(int j = start;j<M;j++) {
				if(!(arr[i][j]==0)) continue;
				arr[i][j] = 1;
				if(j==M-1) {
					wall(idx+1, i+1, 0);
				} else {
					wall(idx+1, i, j+1);
				}
				arr[i][j] = 0;
			}
		}
	}
	
}