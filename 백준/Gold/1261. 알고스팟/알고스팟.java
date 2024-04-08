import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
	
	static int[] dr = {-1, 1, 0, 0};
	static int[] dc = {0, 0, -1, 1};
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		int M = Integer.parseInt(st.nextToken());
		int N = Integer.parseInt(st.nextToken());
		int[][] arr = new int[N][M];
		int[][] dist = new int[N][M];
		for (int i = 0; i < N; i++) {
			String str = br.readLine();
			for (int j = 0; j < M; j++) {
				arr[i][j] = str.charAt(j)-'0';
			}
			Arrays.fill(dist[i], Integer.MAX_VALUE);
		}
		
		Queue<int[]> q = new LinkedList<>();
		q.add(new int[] {0, 0, 0});
		dist[0][0] = 0;
		
		while(!q.isEmpty()) {
			int[] temp = q.poll();
			int r = temp[0];
			int c = temp[1];
			int boom = temp[2];
			
			for(int d = 0;d<4;d++) {
				int nr = r+dr[d];
				int nc = c+dc[d];
				if(nr<0 || nc<0 || nr>=N || nc>=M) continue;
				
				if(arr[nr][nc] == 1) {
					if(dist[nr][nc] > boom+1) {
						q.add(new int[] {nr, nc, boom+1});
						dist[nr][nc] = boom+1;
					}
				} else {
					if(dist[nr][nc] > boom) {
						q.add(new int[] {nr, nc, boom});
						dist[nr][nc] = boom;
					}
				}
			}
		}
		
		System.out.println(dist[N-1][M-1]);
	}
}