import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Solution {
	
	// 멕시노스 크기
	static int N;
	// 멕시노스
	static int[][] cell;
	// 선연결 체크
	static boolean[][] visited;
	// 코어 위치 정보
	static List<int[]> core;
	// 현재 연결한 코어 갯수
	static int count;
	// 최대로 연결한 코어 갯수
	static int countMax;
	// 현재 연결한 선 길이
	static int length;
	// 최소로 연결한 선 길이
	static int lengthMin;
	
	// 연결하지 않았을 경우부터 해서 4방향 탐색까지
	static int[] dr = {0, -1, 1, 0, 0};
	static int[] dc = {0, 0, 0, -1, 1};
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		int T = Integer.parseInt(br.readLine());
		for(int t = 1;t<=T;t++) {
			N = Integer.parseInt(br.readLine());
			cell = new int[N][N];
			visited = new boolean[N][N];
			core = new ArrayList<>();
			for(int i = 0;i<N;i++) {
				StringTokenizer st = new StringTokenizer(br.readLine());
				for(int j = 0;j<N;j++) {
					cell[i][j] = Integer.parseInt(st.nextToken());
					if(cell[i][j]==1) {
						visited[i][j] = true;
					}
					if(!(i==0 || j==0 || i==N-1 || j==N-1) && cell[i][j]==1) {
						core.add(new int[] {i, j});
						count++;
					}
				}
			} // 입력 완료
			
			// 최대값 최소값 초기화
			countMax = 0;
			lengthMin = Integer.MAX_VALUE;
			link(0, 0);
			
			sb.append("#" + t + " " + lengthMin + "\n");
			
		}
		
		System.out.println(sb);
	}
	
	// 코어들 연결 메서드
	static void link(int idx, int v) {
		
		// 모든 코어들의 연결을 다 해주었을 때
		if(idx == core.size()) {
			// 연결한 코어의 갯수가 최대값보다 크면 최대값 갱신해주고 선 길이 수정
			if(countMax<count) {
				countMax = count;
				lengthMin = length;
			} else if(countMax == count) { // 연결한 코어의 갯수가 같을 때는 더 짧은 선 길이로 수정
				lengthMin = Math.min(lengthMin, length);
			}
			return;
		}
		
		// 코어를 연결하지 않을 때(d=0) 부터 4방향(d=1~4) 모든 경우 고려
		for(int d = 0;d<5;d++) {
			int r = core.get(idx)[0];
			int c = core.get(idx)[1];
			
			// 연결할때
			if(d!=0) {
				int nr = r+dr[d];
				int nc = c+dc[d];
				
				// 멕시노스의 가장가리에 도달하거나 다른 코어가 닿기 직전까지 현재 방향으로 나아가면서 연결하기
				while(nr>=0 && nc>=0 && nr<N && nc<N && !visited[nr][nc]) {
					visited[nr][nc] = true; // 연결 체크
					length++; // 선 길이 증가
					nr += dr[d];
					nc += dc[d];
				}
				
				// 반복문이 끝났을 때 가장가리에 도달한 것이면 갯수 증가시키고 다음 코어로 넘어가기
				if(nr<0 || nc<0 || nr>=N || nc>=N) {
					count++;
					link(idx+1, v);
					count--;
				}
				
				// 다음 코어를 고려하고 왔을 때랑
				// 가장자리에 도달하지 못하고 코어에 부딪혔을 때랑
				// 둘 다 다시 현재 코어에 올때까지 연결한 선 없애기
				while(!(nr-dr[d]==r && nc-dc[d]==c)) {
					nr -= dr[d];
					nc -= dc[d];
					visited[nr][nc] = false; // 연결 해제
					length--; // 선 길이 감소
				}
			} else { // 연결하지 않을때는 아무것도 하지 않고 다음 코어로 넘어가기
				link(idx+1, v);
			}
			
		}
		
	}
}