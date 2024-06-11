import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
	static int[] dr = {-1, 1, 0, 0};
	static int[] dc = {0, 0, -1, 1};
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		int N = Integer.parseInt(st.nextToken());
		int L = Integer.parseInt(st.nextToken());
		int R = Integer.parseInt(st.nextToken());
		
		int[][] arr = new int[N][N];
		for(int i = 0;i<N;i++) {
			st = new StringTokenizer(br.readLine());
			for(int j = 0;j<N;j++) {
				arr[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		
		int day = 0;
		while(true) {
			// 인구 이동 여부 변수
			boolean moved = false;
			
			// 국경 열은 나라 체크
			boolean[][] visited = new boolean[N][N];
			for(int i = 0;i<N;i++) {
				for(int j = 0;j<N;j++) {
					if(!visited[i][j]) {
						// 인접한 나라와의 인구수 차이가 L이상 R이하 인지 확인하기 위해 BFS 사용
						Queue<int[]> q = new LinkedList<>();
						q.add(new int[] {i, j});
						// 국경선을 열은 나라들 저장
						List<int[]> list = new ArrayList<>();
						list.add(new int[] {i, j});
						visited[i][j] = true;
						
						while(!q.isEmpty()) {
							int[] temp = q.poll();
							int r = temp[0];
							int c = temp[1];
							
							for(int d = 0;d<4;d++) {
								int nr = r+dr[d];
								int nc = c+dc[d];
								if(nr<0 || nc<0 || nr>=N || nc>=N || visited[nr][nc]) continue;
								// 인접한 나라와 인구수 차이가 L이상 R이하일 때 연합
								if(Math.abs(arr[r][c]-arr[nr][nc])>=L && Math.abs(arr[r][c]-arr[nr][nc])<=R) {
									moved = true;
									// 인접한 나라가 또 다른 연합할 수 있는 나라가 있는지 확인하기 위해 탐색 이어나가기
									q.add(new int[] {nr, nc});
									list.add(new int[] {nr, nc});
									visited[nr][nc] = true;
								}
							}
							
						}
						
						// 국경선 열어서 연합한 나라들 list에 담겨있음
						// 인구이동 시작
						int people = 0;
						int country = list.size();
						for(int idx = 0;idx<list.size();idx++) {
							int[] temp = list.get(idx);
							int r = temp[0];
							int c = temp[1];
							people += arr[r][c];
						}
						
						// 평균으로 인구수 조정
						int avg = people/country;
						for(int idx = 0;idx<list.size();idx++) {
							int[] temp = list.get(idx);
							int r = temp[0];
							int c = temp[1];
							arr[r][c] = avg;
						}
					}
				}
			}
			
			// 인구이동 없으면 종료
			if(!moved) {
				break;
			} else {
				day++;
			}
		}
		System.out.println(day);
	}
}