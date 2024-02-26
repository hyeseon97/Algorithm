import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;

public class Solution {
	
	// 지도 크기
	static int N;
	// 땅 깊이 정보
	static int[][] ground;
	// 최적 경로에 대한 시간
	static int[][] min;
	
	static int[] dr = {-1, 1, 0, 0};
	static int[] dc = {0, 0, -1, 1};
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		int T = Integer.parseInt(br.readLine());
		for(int t = 1;t<=T;t++) {
			N = Integer.parseInt(br.readLine());
			ground = new int[N][N];
			min = new int[N][N];
			for(int i = 0;i<N;i++) {
				String str = br.readLine();
				for(int j = 0;j<N;j++) {
					ground[i][j] = str.charAt(j)-'0';
					min[i][j] = Integer.MAX_VALUE;
				}
			}
			
			Queue<int[]> q = new LinkedList<>();
			q.add(new int[] {0, 0, 0});
			min[0][0] = 0;
			
			while(!q.isEmpty()) {
				int[] temp = q.poll();
				int r = temp[0];
				int c = temp[1];
				int time = temp[2];

				for(int d = 0;d<4;d++) {
					int nr = r+dr[d];
					int nc = c+dc[d];
					
					// 배열을 벗어낫거나 현재 온 경로가 이미 저장되어 있는 경로 시간보다 크거나 같을 때 건너뛰기 (=최적이 아님)
					if(nr<0 || nc<0 || nr>=N || nc>=N || time+ground[nr][nc]>=min[nr][nc]) continue;
					min[nr][nc] = time+ground[nr][nc];
					q.add(new int[] {nr, nc, min[nr][nc]});
				}
			}
			
			sb.append("#" + t + " " + min[N-1][N-1] + "\n");
			
		}
		System.out.println(sb);
	}
}