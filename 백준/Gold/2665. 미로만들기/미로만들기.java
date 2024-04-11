import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class Main {
	
	static int[] dr = {-1, 1, 0, 0};
	static int[] dc = {0, 0, -1, 1};
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int N = Integer.parseInt(br.readLine());
		int[][] arr = new int[N][N];
		int[][] res = new int[N][N];
		for(int i = 0;i<N;i++) {
			String str = br.readLine();
			Arrays.fill(res[i], Integer.MAX_VALUE);
			for(int j = 0;j<N;j++) {
				arr[i][j] = str.charAt(j) - '0';
			}
		}
		
		Queue<int[]> q = new LinkedList<>();
		q.add(new int[] {0, 0, 0});
		res[0][0] = 0;
		
		while(!q.isEmpty()) {
			int[] temp = q.poll();
			int r = temp[0];
			int c = temp[1];
			int change = temp[2];
			
			for(int d = 0;d<4;d++) {
				int nr = r+dr[d];
				int nc = c+dc[d];
				if(nr<0 || nc<0 || nr>=N || nc>=N) continue;
				
				// 1일때는 흰 방이므로 그냥 가기
				if(arr[nr][nc]==1) {
					if(res[nr][nc]>change) {
						q.add(new int[] {nr, nc, change});
						res[nr][nc] = change;
					}
				} else {
					// 0일때는 검은 방이므로 흰방으로 바꿔서 가기
					if(res[nr][nc]>change+1) {
						q.add(new int[] {nr, nc, change+1});
						res[nr][nc] = change+1;
					}
				}
			}
		}
		
		System.out.println(res[N-1][N-1]);
		
	}
}