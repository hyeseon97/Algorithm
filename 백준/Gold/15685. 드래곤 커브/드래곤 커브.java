import java.util.*;
import java.io.*;

public class Main {

	// 드래곤커브 개수
	static int N;
	
	// 드래곤커브 정보
	static int[][] dragon;
	
	// 격자판
	static int[][] grid;
	
	
	static int[] dy = {0, -1, 0, 1};
	static int[] dx = {1, 0, -1, 0};
	
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.parseInt(br.readLine());
		dragon = new int[N][4];
		grid = new int[101][101];
		for(int i = 0;i<N;i++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			for(int j = 0;j<4;j++) {
				dragon[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		
		// 드래콘커브 개수만큼 반복
		for(int n = 0;n<N;n++) {
			
			int X = dragon[n][0];
			int Y = dragon[n][1];
			int D = dragon[n][2];
			int G = dragon[n][3];
			List<Integer> dList = new ArrayList<>();
			
			// 현재 드래곤커브의 세대만큼 반복
			grid[Y][X] = 1;
			for(int g = 0;g<=G;g++) {
				
				if(g == 0) {
					// 0세대는 D방향으로 한번만 이어나가면 된다
					Y += dy[D];
					X += dx[D];
					grid[Y][X] = 1;
					dList.add(D);
				} else {
					// 1세대부터 방향을 기록해둔 리스트의 뒷 방향부터 +1한 방향으로 이어나가면 된다
					int size = dList.size();
					for(int d = size-1;d>=0;d--) {
						Y += dy[(dList.get(d)+1)%4];
						X += dx[(dList.get(d)+1)%4];
						grid[Y][X] = 1;
						dList.add((dList.get(d)+1)%4);
					}
				}
			}
		}
		
		// 네 꼭짓점이 모두 드래곤 커브의 일부인 것 계산
		int result = 0;
		for(int i = 0;i<100;i++) {
			for(int j = 0;j<100;j++) {
				if(grid[i][j] == 1 && grid[i][j+1] == 1 && grid[i+1][j] == 1 && grid[i+1][j+1] == 1) {
					result++;
				}
			}
		}
		
		System.out.println(result);
	}
}