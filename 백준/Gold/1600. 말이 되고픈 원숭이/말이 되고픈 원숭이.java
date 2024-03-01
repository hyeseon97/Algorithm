import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main {
	
	// 말처럼 이동할 수 있는 횟수
	static int K;
	// 배열 크기
	static int W;
	static int H;
	// 배열
	static int[][] arr;
	// 최단횟수
	static int[][][] dist;
	
	// 말처럼 이동하는 방향
	static int[] horseDr = {-2, -1, 1, 2, 2, 1, -1, -2};
	static int[] horseDc = {1, 2, 2, 1, -1, -2, -2, -1};
	
	// 인접한 방향
	static int[] dr = {-1, 1, 0, 0};
	static int[] dc = {0, 0, -1, 1};
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		K = Integer.parseInt(br.readLine());
		StringTokenizer st = new StringTokenizer(br.readLine());
		W = Integer.parseInt(st.nextToken());
		H = Integer.parseInt(st.nextToken());
		arr = new int[H][W];
		dist = new int[H][W][K+1];
		for(int i = 0;i<H;i++) {
			st = new StringTokenizer(br.readLine());
			for(int j = 0;j<W;j++) {
				arr[i][j] = Integer.parseInt(st.nextToken());
				if(i==0 && j==0) continue;
				Arrays.fill(dist[i][j], Integer.MAX_VALUE);
			}
		} // 입력 끝
		
		wannaBeHorse();
		
		// 마지막 칸에 대해 min 값 구하고 출력
		int min = Integer.MAX_VALUE;
		for(int i = 0;i<=K;i++) {
			min = Math.min(min, dist[H-1][W-1][i]);
		}
		if(min == Integer.MAX_VALUE) System.out.println(-1);
		else System.out.println(min);
		
	}
	
	// 말이 되고픈 원숭이 탈출 시작
	static void wannaBeHorse() {
		
		// 이동횟수가 적은것부터 처리해서 최적화
		PriorityQueue<int[]> q = new PriorityQueue<>(new Comparator<int[]>() {
			@Override
			public int compare(int[] o1, int[] o2) {
				return o1[2]-o2[2];
			}
		});
		q.add(new int[] {0, 0, 0, 0}); // 가로위치, 세로위치, 이동횟수, 말처럼 이동한 횟수
		
		// 다익스트라로 풀기
		// 말처럼 이동할 수 있는 횟수가 남아있다면 말처럼 이동하기
		// 인접한 방향으로도 이동해보기
		while(!q.isEmpty()) {
			int[] temp = q.poll();
			int r = temp[0];
			int c = temp[1];
			int count = temp[2];
			int horse = temp[3];
			
			if(horse<K) {
				for(int d = 0;d<8;d++) {
					int nr = r+horseDr[d];
					int nc = c+horseDc[d];
					if(nr<0 || nc<0 || nr>=H || nc>=W || arr[nr][nc]==1) continue;
					if(count+1<dist[nr][nc][horse+1]) {
						dist[nr][nc][horse+1] = count+1;
						q.add(new int[] {nr, nc, count+1, horse+1});
					}
				}
			}
			
			for(int d = 0;d<4;d++) {
				int nr = r+dr[d];
				int nc = c+dc[d];
				if(nr<0 || nc<0 || nr>=H || nc>=W || arr[nr][nc]==1) continue;
				if(count+1<dist[nr][nc][horse]) {
					dist[nr][nc][horse] = count+1;
					q.add(new int[] {nr, nc, count+1, horse});
				}
			}
		}
		
	}
}