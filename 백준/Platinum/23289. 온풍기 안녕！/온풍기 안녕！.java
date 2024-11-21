import java.io.*;
import java.util.*;

public class Main {

	// 집크기
	static int R;
	static int C;

	// 집의 온도
	static int[][] temperature;

	// 온도 조절을 한번에 하기 위한 임시 배열
	static int[][] tempArr;

	// 온풍기
	static List<int[]> heaterList;

	// 검사해야하는 곳
	static List<int[]> testList;

	// 검사 기준
	static int K;

	// 벽 개수
	static int W;

	// 벽 : 각 칸을 key, 벽을 사이에 두고 있는 인접 칸 set을 value
	static Map<String, Set<String>> wall;

	// 먹은 초콜릿
	static int chocolate;

	// 방향배열 : i인덱스는 X → ← ↑ ↓, j인덱스는 직진 왼쪽 오른쪽
	static int[][] dr = { { 0, 0, 0 }, { 0, -1, 1 }, { 0, 1, -1 }, { -1, 0, 0 }, { 1, 0, 0 } };
	static int[][] dc = { { 0, 0, 0 }, { 1, 0, 0 }, { -1, 0, 0 }, { 0, -1, 1 }, { 0, 1, -1 } };

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		R = Integer.parseInt(st.nextToken());
		C = Integer.parseInt(st.nextToken());
		K = Integer.parseInt(st.nextToken());
		temperature = new int[R + 1][C + 1];
		heaterList = new ArrayList<>();
		testList = new ArrayList<>();
		for (int i = 1; i <= R; i++) {
			st = new StringTokenizer(br.readLine());
			for (int j = 1; j <= C; j++) {
				int x = Integer.parseInt(st.nextToken());
				// 1~4는 온풍기 방향
				if (x >= 1 && x <= 4) {
					heaterList.add(new int[] { i, j, x });
				}
				// 5는 검사해야할 곳
				else if (x == 5) {
					testList.add(new int[] { i, j });
				}
			}
		}
		W = Integer.parseInt(br.readLine());
		wall = new HashMap<>();
		for (int w = 0; w < W; w++) {
			st = new StringTokenizer(br.readLine());
			int x = Integer.parseInt(st.nextToken());
			int y = Integer.parseInt(st.nextToken());
			int t = Integer.parseInt(st.nextToken());
			String area1 = x + " " + y;
			String area2 = "";
			if (t == 0) {
				area2 = (x - 1) + " " + y;
			} else {
				area2 = x + " " + (y + 1);
			}

			if (!wall.containsKey(area1)) {
				wall.put(area1, new HashSet<>());
			}
			if (!wall.containsKey(area2)) {
				wall.put(area2, new HashSet<>());
			}
			wall.get(area1).add(area2);
			wall.get(area2).add(area1);
		}

		// 초콜릿을 100개먹을때까지 반복하고 그 전에 검사를 통과하면 끝
		// 통과하지 못하면 101개 먹은채로 끝
		chocolate = 0;
		while (chocolate <= 100) {

			// 1. 온풍기에서 바람 불기
			for (int i = 0; i < heaterList.size(); i++) {
				wind(heaterList.get(i)[0], heaterList.get(i)[1], heaterList.get(i)[2]);
			}

			// 2. 온도 조절하기
			copyTempArr();
			// 각 칸에서 오른쪽과 아래 인접한 칸과 조절
			for (int i = 1; i <= R; i++) {
				for (int j = 1; j <= C; j++) {
					conditioning(i, j);
				}
			}

			// 3. 가장 바깥쪽 온도 -1
			decrease();

			// 4. 초콜릿 먹기
			chocolate++;
			
			// 5. 검사하기
			boolean result = test();
			if (result) {
				break;
			}

		}

		System.out.println(chocolate);

	}

	private static void wind(int heaterR, int heaterC, int heaterD) {

		Queue<int[]> q = new LinkedList<>();
		q.add(new int[] { heaterR + dr[heaterD][0], heaterC + dc[heaterD][0], 5 });
		temperature[heaterR + dr[heaterD][0]][heaterC + dc[heaterD][0]] += 5;
		boolean[][] visited = new boolean[R + 1][C + 1];
		visited[heaterR + dr[heaterD][0]][heaterC + dc[heaterD][0]] = true;

		while (!q.isEmpty()) {
			int[] temp = q.poll();
			int r = temp[0];
			int c = temp[1];
			int val = temp[2];
			if (val == 1)
				continue;

			String area1 = r + " " + c;

			// 갈 수 있는지는
			// 1) 배열을 벗어나지 않았는지
			// 2) 현재칸에 벽이 없으면 갈 수 있고
			// 3) 있어도 다음칸 사이에 없으면 갈 수 있고
			// 4) 최종 nr, nc칸에는 아직 안갔을때만 가고

			// 직진방향으로 바람이 갈 수 있는지
			int nr = r + dr[heaterD][0];
			int nc = c + dc[heaterD][0];
			String area2 = nr + " " + nc;
			if (nr >= 1 && nc >= 1 && nr <= R && nc <= C
					&& (!wall.containsKey(area1) || !wall.get(area1).contains(area2)) && !visited[nr][nc]) {
				q.add(new int[] { nr, nc, val - 1 });
				temperature[nr][nc] += (val - 1);
				visited[nr][nc] = true;
			}

			// 왼쪽직진방향으로 바람이 갈 수 있는지
			// 먼저 왼쪽으로 갈 수 있는지
			int mr = r + dr[heaterD][1];
			int mc = c + dc[heaterD][1];
			area2 = mr + " " + mc;
			if (mr >= 1 && mc >= 1 && mr <= R && mc <= C
					&& (!wall.containsKey(area1) || !wall.get(area1).contains(area2))) {

				// 왼쪽으로 가고 직진방향으로 갈 수 있는지
				nr = mr + dr[heaterD][0];
				nc = mc + dc[heaterD][0];
				String area3 = nr + " " + nc;
				if (nr >= 1 && nc >= 1 && nr <= R && nc <= C
						&& (!wall.containsKey(area2) || !wall.get(area2).contains(area3)) && !visited[nr][nc]) {
					q.add(new int[] { nr, nc, val - 1 });
					temperature[nr][nc] += (val - 1);
					visited[nr][nc] = true;
				}
			}

			// 오른쪽직진방향으로 바람이 갈 수 있는지
			mr = r + dr[heaterD][2];
			mc = c + dc[heaterD][2];
			area2 = mr + " " + mc;
			if (mr >= 1 && mc >= 1 && mr <= R && mc <= C
					&& (!wall.containsKey(area1) || !wall.get(area1).contains(area2))) {

				// 오른쪽으로 가고 직진방향으로 갈 수 있는지
				nr = mr + dr[heaterD][0];
				nc = mc + dc[heaterD][0];
				String area3 = nr + " " + nc;
				if (nr >= 1 && nc >= 1 && nr <= R && nc <= C
						&& (!wall.containsKey(area2) || !wall.get(area2).contains(area3)) && !visited[nr][nc]) {
					q.add(new int[] { nr, nc, val - 1 });
					temperature[nr][nc] += (val - 1);
					visited[nr][nc] = true;
				}
			}
		}

	}

	private static void copyTempArr() {

		tempArr = new int[R + 1][C + 1];
		for (int i = 1; i <= R; i++) {
			for (int j = 1; j <= C; j++) {
				tempArr[i][j] = temperature[i][j];
			}
		}

	}

	private static void conditioning(int i, int j) {

		// 오른쪽 사이에 벽이 없으면 조절
		String area1 = i + " " + j;
		String area2 = i + " " + (j + 1);
		if ((j + 1) <= C && (!wall.containsKey(area1) || !wall.get(area1).contains(area2))) {
			// 조절될 값
			int val = Math.abs(tempArr[i][j] - tempArr[i][j + 1]) / 4;
			// 큰쪽은 빼고 작은쪽은 더하기
			if (tempArr[i][j] > tempArr[i][j + 1]) {
				temperature[i][j] -= val;
				temperature[i][j + 1] += val;
			} else {
				temperature[i][j] += val;
				temperature[i][j + 1] -= val;
			}
		}

		// 아래쪽 사이에 벽이 없으면 조절
		area2 = (i + 1) + " " + j;
		if ((i + 1) <= R && (!wall.containsKey(area1) || !wall.get(area1).contains(area2))) {
			// 조절될 값
			int val = Math.abs(tempArr[i][j] - tempArr[i + 1][j]) / 4;
			// 큰쪽은 빼고 작은쪽은 더하기
			if (tempArr[i][j] > tempArr[i + 1][j]) {
				temperature[i][j] -= val;
				temperature[i + 1][j] += val;
			} else {
				temperature[i][j] += val;
				temperature[i + 1][j] -= val;
			}
		}
	}

	private static void decrease() {

		// 1행
		for (int j = 1; j < C; j++) {
			if (temperature[1][j] == 0)
				continue;
			temperature[1][j]--;
		}

		// 1열
		for (int i = 2; i <= R; i++) {
			if (temperature[i][1] == 0)
				continue;
			temperature[i][1]--;
		}

		// R행
		for (int j = 2; j <= C; j++) {
			if (temperature[R][j] == 0)
				continue;
			temperature[R][j]--;
		}

		// C열
		for (int i = 1; i < R; i++) {
			if (temperature[i][C] == 0)
				continue;
			temperature[i][C]--;
		}
	}

	private static boolean test() {

		for (int t = 0; t < testList.size(); t++) {
			int i = testList.get(t)[0];
			int j = testList.get(t)[1];
			// 하나라도 맞지 않으면 바로 false반환
			if (temperature[i][j] < K) {
				return false;
			}
		}

		return true;

	}

}