import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Main {
	
	static int[] dr = {-1, 1, 0, 0};
	static int[] dc = {0, 0, -1, 1};
	
	static int N;
	static int M;
	static List<int[]> hole;
	static int holeAR;
	static int holeAC;
	static int holeBR;
	static int holeBC;
	
	static char[][] miro;
	static boolean[][] visited;
	
	static int min;
	
	static char[][] result;
	
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		
		hole = new ArrayList<>();
		
		miro = new char[N][M];
		for(int i = 0;i<N;i++) {
			String str = br.readLine();
			for(int j = 0;j<M;j++) {
				miro[i][j] = str.charAt(j);
				if((i==0 || j==0 || i==N-1 || j==M-1) && miro[i][j]=='.') {
					hole.add(new int[] {i, j});
				}
			}
		}

		holeAR = hole.get(0)[0];
		holeAC = hole.get(0)[1];
		holeBR = hole.get(1)[0];
		holeBC = hole.get(1)[1];
		
		visited = new boolean[N][M];
		min = Integer.MAX_VALUE;
		
		visited[holeAR][holeAC] = true;
		DFS(holeAR, holeAC, 1);
		
		System.out.println(sb);
	}
	
	static StringBuilder sb;
	static void DFS(int r, int c, int count) {
		
		if(r==holeBR && c==holeBC) {
			if(min>count) {
				min = count;
				sb = new StringBuilder();
				
				for(int i = 0;i<N;i++) {
					for(int j = 0;j<M;j++) {
						if(miro[i][j]=='+') {
							sb.append('+');
						} else if(visited[i][j]) {
							sb.append('.');
						} else {
							sb.append('@');
						}
					}
					sb.append("\n");
				}
			}
			return;
		}
		
		for(int d = 0;d<4;d++) {
			int nr = r+dr[d];
			int nc = c+dc[d];
			
			if(nr<0 || nc<0 || nr>=N || nc>=M || visited[nr][nc] || miro[nr][nc]=='+') continue;
			
			visited[nr][nc] = true;
			DFS(nr, nc, count+1);
			visited[nr][nc] = false;
			
		}
	}
	
	
}