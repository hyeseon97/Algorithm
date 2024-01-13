import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
	
	static int[] dr = {-1, -1, -1, 0, 1, 1, 1, 0};
	static int[] dc = {-1, 0, 1, 1, 1, 0, -1, -1};

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		int M = Integer.parseInt(st.nextToken());
		int N = Integer.parseInt(st.nextToken());
		int[][] arr = new int[M][N];

		for (int i = 0; i < M; i++) {
			st = new StringTokenizer(br.readLine());
			for (int j = 0; j < N; j++) {
				arr[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		
		boolean[][] visited = new boolean[M][N];
		
		Queue<int[]> q = new LinkedList<>();
		int count = 0;
		
		for(int i = 0;i<M;i++) {
			for(int j = 0;j<N;j++) {
				
				if(arr[i][j] == 1 && !visited[i][j]) {
					q.add(new int[] {i, j});
					visited[i][j] = true;
					count++;
					while(!q.isEmpty()) {
						int[] temp = q.poll();
						int r = temp[0];
						int c = temp[1];
						
						for(int d = 0;d<8;d++) {
							int nr = r+dr[d];
							int nc = c+dc[d];
							if(nr<0 || nc<0|| nr>=M || nc>=N || visited[nr][nc] || arr[nr][nc]==0) continue;
							q.add(new int[] {nr, nc});
							visited[nr][nc] = true;
						}
					}
					
				}
			}
		}
		
		System.out.println(count);
		

	}
}
