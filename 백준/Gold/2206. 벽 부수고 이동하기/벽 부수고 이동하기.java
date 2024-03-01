import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
	
	// 맵 크기
	static int N;
	static int M;
	// 맵
	static int[][] arr;
	// 최단거리 저장
	// [i][j][0] : 벽을 부수지 않았을 때 최단거리
	// [i][j][1] : 벽을 부쉈을 때 최단거리
	static int[][][] dist;
	
	static int[] dr = {-1, 1, 0, 0};
	static int[] dc = {0, 0, -1, 1};
	
	static int min;
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		arr = new int[N][M];
		for(int i = 0;i<N;i++) {
			String str = br.readLine();
			for(int j = 0;j<M;j++) {
				arr[i][j] = str.charAt(j)-'0';
			}
		} // 입력 끝
		
		// 최단거리 배열 최대값으로 저장
		// 시작 위치는 1로 저장
		dist = new int[N][M][2];
		for(int i = 0;i<N;i++) {
			for(int j = 0;j<M;j++) {
				if(i==0 && j==0) {
					Arrays.fill(dist[i][j], 1);
				} else {
					Arrays.fill(dist[i][j], Integer.MAX_VALUE);
				}
			}
		}
		// 최단거리 찾기 시작
		run();
		// 부쉈을때랑 부수지 않았을 때 중 최소값 찾고 출력
		min = Math.min(dist[N-1][M-1][0], dist[N-1][M-1][1]);
		if(min == Integer.MAX_VALUE) System.out.println(-1);
		else System.out.println(min);
		
	}
	
	static void run() {
		
		// 다익스트라로 최단거리 구학
		// 부쉈을때랑 부수지 않았을 때, 벽이 있을 때 없을 때 총 4가지 경우에 따라 처리하기
		// 벽을 부순적이 없을 때
		// 벽이 없다면 -> 3차원인덱스가 0인것이랑 비교 -> 그대로 큐에 넣기
		// 벽이 있다면 -> 3차원인덱스가 1인것이랑 비교 -> 벽부쉈다고 표시하고 큐에 넣기
		// 벽을 부순적이 있을 때
		// 벽이 없다면 -> 3차원인덱스가 1인것이랑 비교 -> 그대로 큐에 넣기
		// 벽이 있다면 -> 이미 부쉈기때문에 큐에 넣지 못하고 넘어감
		Queue<int[]> q = new LinkedList<>();
		q.add(new int[] {0, 0, 1, 0});
		
		while(!q.isEmpty()) {
			int[] temp = q.poll();
			int r = temp[0];
			int c = temp[1];
			int len = temp[2];
			int remove = temp[3];
			
			for(int d = 0;d<4;d++) {
				int nr = r+dr[d];
				int nc = c+dc[d];
				
				if(nr<0 || nc<0 || nr>=N || nc>=M) continue;
				if(remove==0) {
					if(arr[nr][nc]==0) {
						if(len+1<dist[nr][nc][0]) {
							dist[nr][nc][0] = len+1;
							q.add(new int[] {nr, nc, len+1, remove});
						}
					} else {
						if(len+1<dist[nr][nc][1]) {
							dist[nr][nc][1] = len+1;
							q.add(new int[] {nr, nc, len+1, 1});
						}
					}
				} else {
					if(arr[nr][nc]==0) {
						if(len+1<dist[nr][nc][1]) {
							dist[nr][nc][1] = len+1;
							q.add(new int[] {nr, nc, len+1, remove});
						}
					}
				}
			}
		}
		
		
	}
}