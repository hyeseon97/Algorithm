import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.StringTokenizer;

public class Solution {

	// 지도 크기
	static int N;
	// 공사할 수 있는 최대 깊이
	static int K;
	// 지도
	static int[][] map;
	// 제일 높은 봉우리 위치 리스트
	static List<int[]> top;
	// 방문체크
	static boolean[][] visited;
	// 등산로 최대길이
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
			K = Integer.parseInt(st.nextToken());
			map = new int[N][N];
			visited = new boolean[N][N];
			top = new ArrayList<>();
			int topMax = 0;
			for (int i = 0; i < N; i++) {
				st = new StringTokenizer(br.readLine());
				for (int j = 0; j < N; j++) {
					map[i][j] = Integer.parseInt(st.nextToken());
					if (topMax <= map[i][j]) {
						if (topMax < map[i][j]) {
							topMax = map[i][j];
							top = new ArrayList<>();
						}
						top.add(new int[] { i, j });
					}
				}
			} // 입력 끝

			max = 0;

			// 가장 높은 모든 봉우리에서 시작
			for (int i = 0; i < top.size(); i++) {
				int r = top.get(i)[0];
				int c = top.get(i)[1];
				visited[r][c] = true;
				down(r, c, map[r][c], 1, 0);
				visited[r][c] = false;
			}

			sb.append("#" + t + " " + max + "\n");
		}
		System.out.println(sb);
	}

	static void down(int R, int C, int high, int len, int fix) {

		// (R, C)에서 출발해 낮은곳으로만 가는 DFS
		// 다음칸이 같거나 높은데 K만큼 깎아서 갈 수 있다면 최소한으로 깎아서 가보기, 이때 공사했다는 표시 0/1 로 체크해서 가기

		boolean move = false;

		for (int d = 0; d < 4; d++) {
			int nr = R + dr[d];
			int nc = C + dc[d];
			if (nr < 0 || nc < 0 || nr >= N || nc >= N ||  visited[nr][nc]) continue;
			// 현재 높이보다 낮을 때 갈 수 있음
			if (high > map[nr][nc]) {
				visited[nr][nc] = true;
				down(nr, nc, map[nr][nc], len+1, fix);
				visited[nr][nc] = false;
				move = true;
			} else {
				// 공사를 한번도 안했으면서 같거나 높지만 공사하면 갈 수 있을 때
				if (fix == 0 && high > map[nr][nc] - K) {
					// 현재 높이보다 1만 작게 깎아서 공사하기
					visited[nr][nc] = true;
					down(nr, nc, high-1, len+1, 1);
					visited[nr][nc] = false;
					move = true;
				}
			}
		}

		// 현재 위치에서 더이상 다른 곳으로 내려갈 수 없을 때 등산로 조성 종료
		// 이동거리로 최대값 비교해서 갱신
		if (!move) {
			max = Math.max(max, len);
		}

	}

}