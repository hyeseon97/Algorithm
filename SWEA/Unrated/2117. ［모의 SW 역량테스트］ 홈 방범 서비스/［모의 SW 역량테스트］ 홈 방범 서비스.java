import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Solution {

	// 도시의 크기
	static int N;
	// 한 집이 지불하는 서비스 비용
	static int M;
	// 도시
	static int[][] city;
	// 도시를 다 덮을 수 있는 서비스 범위
	static int K;
	// 서비스 운영 비용
	static int pay;
	// 최대 집 수
	static int max;

	static int[] dr = { -1, 1, 0, 0 };
	static int[] dc = { 0, 0, -1, 1 };

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		int T = Integer.parseInt(br.readLine());
		for (int t = 1; t <= T; t++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			N = Integer.parseInt(st.nextToken());
			M = Integer.parseInt(st.nextToken());
			city = new int[N][N];
			for (int i = 0; i < N; i++) {
				st = new StringTokenizer(br.readLine());
				for (int j = 0; j < N; j++) {
					city[i][j] = Integer.parseInt(st.nextToken());
				}
			}
			K = N * 2 - 1;
			max = 0;

			//서비스 중심 위치를 도시의 모든 위치에서 반복
			for (int i = 0; i < N; i++) {
				for (int j = 0; j < N; j++) {
					// 서비스 중심 위치 (i, j)일 때 계산하기
					pay = 1;
					service(i, j);
				}

			}
			sb.append("#" + t + " " + max + "\n");
		}
		System.out.println(sb);
	}

	// 서비스 중심 위치는 (R, C)이고 범위는 A일 때 서비스 가능한 집 수 계산
	static void service(int R, int C) {

		// BFS로 범위 안의 모든 칸 탐색
		Queue<int[]> q = new LinkedList<>();
		q.add(new int[] { R, C, 1 });
		boolean[][] visited = new boolean[N][N];
		visited[R][C] = true;

		// 서비스 범위 바뀐거 판단하기 위한 변수
		// 큐에 들어가는 위치가 서비스 범위가 커지는 순서로 들어가니까 바뀌었을때마다 이득 새로 판단하기
		int area = 1;
		
		// 서비스 가능한 집 수
		int house = 0;

		while (!q.isEmpty()) {
			int[] temp = q.poll();
			int r = temp[0];
			int c = temp[1];
			int k = temp[2];

			// 범위가 바꼈을 때
			// 현재범위에 해당하는 집들로 이익 계산
			if(k!=area) {
				// 보안회사가 지불할 돈 갱신
				pay += 4*(area-1);
				// 보안회사가 얻는 이득
				int profit = house * M - pay;
				// 손해보지 않았으면 최대값 갱신
				if (profit >= 0) {
					max = Math.max(max, house);
				}
				area = k;
			}

			
			// 집 카운트
			if (city[r][c] == 1)
				house++;
			
			if(k>K) {
				break;
			}

			for (int d = 0; d < 4; d++) {
				int nr = r + dr[d];
				int nc = c + dc[d];
				// 배열을 벗어낫거나, 방문했거나, 최대 서비스 범위 벗어나면 건너뛰기
				if (nr < 0 || nc < 0 || nr >= N || nc >= N || visited[nr][nc])
					continue;

				q.add(new int[] { nr, nc, k + 1 });
				visited[nr][nc] = true;
				
			}
		}

		// 모든 배열을 덮었을 땐 while문 안에 있는 범위가 바꼈을때 조건문 안에 들어가지 못하기 때문에 따로 계산해준다
		// 보안회사가 지불할 돈 갱신
		pay += 4*(K-1);
		// 보안회사가 얻는 이득
		int profit = house * M - pay;
		// 손해보지 않았으면 최대값 갱신
		if (profit >= 0) {
			max = Math.max(max, house);
		}
		

	}
}