import java.io.*;
import java.util.*;

public class Main {

	// 격자크기
	static int N;

	// 블리자드 명령 수
	static int M;

	// 격자
	static int[][] board;

	// 구슬 큐
	static Queue<int[]> stone;

	// 폭발한 구슬 카운트
	// 각 인덱스에 해당 숫자 구슬 카운트
	static int[] distroyStone;

	// 격자의 번호방향
	static int[] dr = { 0, 1, 0, -1 };
	static int[] dc = { -1, 0, 1, 0 };

	// 얼음파편 방향
	static int[] throwDr = { 0, -1, 1, 0, 0 };
	static int[] throwDc = { 0, 0, 0, -1, 1 };

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		board = new int[N + 1][N + 1];
		for (int i = 1; i <= N; i++) {
			st = new StringTokenizer(br.readLine());
			for (int j = 1; j <= N; j++) {
				board[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		distroyStone = new int[4];

		// 명령어 수만큼 반복
		for (int m = 0; m < M; m++) {
			st = new StringTokenizer(br.readLine());
			int d = Integer.parseInt(st.nextToken());
			int s = Integer.parseInt(st.nextToken());

			// 1. 얼음파편 던져서 구슬 파괴
			throwIce(d, s);
			// 배열에 있는 구슬 큐로 이동
			arrToQueue();

			// 남은 구슬 없으면 바로 종료
			if (stone.isEmpty())
				break;

			// 2. 구슬 폭발과 이동 반복
			while (true) {
				// 더이상 폭발이 없거나 남은 구슬이 없으면 종료
				if (!stoneBoom() || stone.isEmpty())
					break;
			}

			// 남은 구슬 없으면 바로 종료
			if (stone.isEmpty())
				break;

			// 3. 구슬 변화시키기
			change();

			// 큐에 있는 구슬 배열로 이동
			queueToArr();

		}

		int answer = 1 * distroyStone[1] + 2 * distroyStone[2] + 3 * distroyStone[3];
		System.out.println(answer);

	}
	
	static void print() {
		for(int i = 1;i<=N;i++) {
			for(int j = 1;j<=N;j++) {
				System.out.print(board[i][j] + " ");
			}
			System.out.println();
		}
		System.out.println();
	}

	static void throwIce(int d, int s) {
		int r = (N + 1) / 2;
		int c = (N + 1) / 2;

		// d방향으로부터 s거리까지 구슬파괴
		for (int len = 1; len <= s; len++) {
			board[r + throwDr[d] * len][c + throwDc[d] * len] = 0;
		}
	}

	static boolean stoneBoom() {

		// 폭발 전 구슬 개수
		int before = stone.size();

		// 큐에서 구슬 하나씩 꺼내면서 폭발할 수 있는지 구슬번호와 갯수 누적하고
		// 구슬 번호가 바뀌었을 때 누적한게 4개 이상이면 폭발시키고
		// 아니면 갯수만큼 다시 넣기
		// 번호도 1번부터 다시 붙이기

		// 1번부터 꺼내서 초기화
		int[] temp = stone.poll();
		int stoneNum = temp[1];
		int stoneCount = 1;
		int num = 1;

		while (!stone.isEmpty() && stone.peek()[0] != 1) {
			temp = stone.poll();
			// 구슬 번호 바뀌면 폭발하거나 다시 넣기
			if (temp[1] != stoneNum) {
				// 4개 이상으로 누적됐을 때 폭발
				if (stoneCount >= 4) {
					distroyStone[stoneNum] += stoneCount;
				} else {
					// 다시 넣기
					for (int i = 0; i < stoneCount; i++) {
						stone.add(new int[] { num++, stoneNum });
					}
				}
				// 구슬 번호 갱신
				stoneNum = temp[1];
				stoneCount = 1;
			} else {
				// 안바뀌면 개수 증가
				stoneCount++;
			}
		}

		// 마지막으로 갱신된 구슬도 폭발하거나 넣어주기
		// 4개 이상으로 누적됐을 때 폭발
		if (stoneCount >= 4) {
			distroyStone[stoneNum] += stoneCount;
		} else {
			// 다시 넣기
			for (int i = 0; i < stoneCount; i++) {
				stone.add(new int[] { num++, stoneNum });
			}
		}

		// 폭발 후 구슬 개수가 전이랑 변함이 없으면 더이상 폭발할 수 없으므로 false반환
		if (before == stone.size()) {
			return false;
		}

		return true;
	}

	static void change() {

		// 큐에서 하나씩 꺼내오면서 구슬번호같으면 누적시켰다가
		// 달라지면 나눠서 넣기
		// 번호도 1번부터 다시 붙이기

		// 1번부터 꺼내서 초기화
		int[] temp = stone.poll();
		int stoneNum = temp[1];
		int stoneCount = 1;
		int num = 1;

		while (!stone.isEmpty() && stone.peek()[0] != 1) {
			temp = stone.poll();
			// 구슬 번호 바뀌면 A는 누적개수, B는 구슬번호로 두개의 구슬 넣기
			if (temp[1] != stoneNum) {
				stone.add(new int[] { num++, stoneCount });
				stone.add(new int[] { num++, stoneNum });

				// 구슬 번호 갱신
				stoneNum = temp[1];
				stoneCount = 1;
			} else {
				// 안바뀌면 개수 증가
				stoneCount++;
			}
		}

		// 마지막으로 갱신된 구슬도 나눠서 넣기
		stone.add(new int[] { num++, stoneCount });
		stone.add(new int[] { num++, stoneNum });
		
	}

	static void arrToQueue() {
		stone = new LinkedList<>();

		int r = (N + 1) / 2;
		int c = (N + 1) / 2;
		int d = 0;
		int num = 1;
		boolean[][] visited = new boolean[N + 1][N + 1];
		visited[(N + 1) / 2][(N + 1) / 2] = true;

		while (true) {
			// 현재방향에 있는 구슬 저장
			r += dr[d];
			c += dc[d];
			if (board[r][c] != 0) {
				stone.add(new int[] { num++, board[r][c] });
			}
			visited[r][c] = true;

			// 왼쪽에 가지 않았으면 방향 전환
			int leftD = (d + 1) % 4;
			if (!visited[r + dr[leftD]][c + dc[leftD]]) {
				d = leftD;
			}

			// (1, 1)에 도달하면 종료
			if (r == 1 && c == 1)
				break;
		}
	}

	static void queueToArr() {
		board = new int[N + 1][N + 11];

		int r = (N + 1) / 2;
		int c = (N + 1) / 2;
		int d = 0;
		boolean[][] visited = new boolean[N + 1][N + 1];
		visited[(N + 1) / 2][(N + 1) / 2] = true;

		// 남은 구슬이 없을때까지 반복
		while (!stone.isEmpty()) {
			// 큐에서 하나 꺼내서 현재방향에 구슬 저장
			r += dr[d];
			c += dc[d];
			int[] temp = stone.poll();
			board[r][c] = temp[1];
			visited[r][c] = true;

			// 왼쪽에 가지 않았으면 방향 전환
			int leftD = (d + 1) % 4;
			if (!visited[r + dr[leftD]][c + dc[leftD]]) {
				d = leftD;
			}

			// (1, 1)에 도달하면 종료
			if (r == 1 && c == 1)
				break;
		}

	}
}