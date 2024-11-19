import java.io.*;
import java.util.*;

public class Main {

	// 격자의 크기
	static int N;

	// 색상의 개수
	static int M;

	// 격자
	static int[][] board;

	// 삭제될 블록그룹 정보
	static int total;
	static int rainbow;
	static int maxR;
	static int maxC;

	// 점수
	static int score;

	static int[] dr = { -1, 0, 1, 0 };
	static int[] dc = { 0, -1, 0, 1 };

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		board = new int[N][N];
		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			for (int j = 0; j < N; j++) {
				board[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		score = 0;
		
		while (true) {

			// 1. 가장 큰 블록그룹 찾기
			find();

			// 조건에 부합한 블록그룹이 없으면 종료
			if (total == 0)
				break;

			// 2. 블록그룹의 블록 제거하고 점수 계산
			remove();
			score += Math.pow(total, 2);

			// 3. 중력
			gravity();
			
			// 4. 90도 반시계 방향 회전
			rotation();
			
			// 5. 중력
			gravity();
		}

		System.out.println(score);

	}
	
	static void print() {
		for(int i = 0;i<N;i++) {
			for(int j = 0;j<N;j++) {
				System.out.print(board[i][j] + " ");
			}
			System.out.println();
		}
		System.out.println();
	}

	static void find() {

		// 모든 칸에서 시작해 BFS로 그룹이 될 수 있는 블록들 찾기
		// 일반 블록은 방문체크해서 시작 블록이 되지 않게 하기
		// 전체개수, 무지개개수, 기준블록 행 열 위치 갱신하면서 BFS 나아가기
		total = 0;
		rainbow = 0;
		maxR = 0;
		maxC = 0;

		boolean[][] colorVisited = new boolean[N][N];
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				// 체크되지 않은 일반블록에서만 시작
				if (!(board[i][j] >= 1 && !colorVisited[i][j]))
					continue;
				// 현재 블록 그룹의 블록들 저장
				PriorityQueue<int[]> tempBlock = new PriorityQueue<int[]>(new Comparator<int[]>() {
					@Override
					public int compare(int[] o1, int[] o2) {
						// 일반블록이 먼저
						if (o1[2] == o2[2]) {
							// 행이 작은게 먼저
							if (o1[0] == o2[0]) {
								return o1[1] - o2[1];
							}
							return o1[0] - o2[0];
						}
						return o1[2] - o2[2];
					}
				});

				// 현재 블록 그룹에 대한 정보와 BFS큐
				Queue<int[]> q = new LinkedList<>();
				q.add(new int[] { i, j });
				boolean[][] visited = new boolean[N][N];

				int color = board[i][j];
				int rainbowCount = 0;

                while (!q.isEmpty()) {
					int[] temp = q.poll();
					int r = temp[0];
					int c = temp[1];

					for (int d = 0; d < 4; d++) {
						int nr = r + dr[d];
						int nc = c + dc[d];
						// 격자판을 벗어났거나 이미 방문했으면 건너뛰기
						if (nr < 0 || nc < 0 || nr >= N || nc >= N || visited[nr][nc] || colorVisited[nr][nc])
							continue;
						// 검정색이거나 무지개가 아닌데 색이 맞지 않거나 빈곳이면 건너뛰기
						if (board[nr][nc] == -1 || (board[nr][nc] > 0 && board[nr][nc] != color) || board[nr][nc] == -9)
							continue;

						// 다음블록 추가
						q.add(new int[] { nr, nc });
						visited[nr][nc] = true;

						// 다음블록이 일반블록이면 colorVisited에 체크하고 nowBlock에는 2인덱스에 0으로 저장
						// 다음블록이 무지개블록이면 rainbowCount를 +1하고 nowBlock에는 2인덱스에 1로 저장
						if (board[nr][nc] == color) {
							colorVisited[nr][nc] = true;
							tempBlock.add(new int[] { nr, nc, 0 });
						} else {
							rainbowCount++;
							tempBlock.add(new int[] { nr, nc, 1 });
						}

					}

				}

				// 현재 블록 그룹에 일반색이 하나도 없거나 총 2개이상이 안되면 건너뛰기
				if (tempBlock.isEmpty() || tempBlock.peek()[2] == 1 || tempBlock.size() < 2)
					continue;

				// 조건에 적합한 블록인지 판단해 갱신
				// 1) 크기가 크면 갱신
				if (total < tempBlock.size()) {
					total = tempBlock.size();
					rainbow = rainbowCount;
					maxR = tempBlock.peek()[0];
					maxC = tempBlock.peek()[1];
				} else if (total == tempBlock.size()) {

					// 2) 무지개가 많으면 갱신
					if (rainbow < rainbowCount) {
						total = tempBlock.size();
						rainbow = rainbowCount;
						maxR = tempBlock.peek()[0];
						maxC = tempBlock.peek()[1];
					} else if (rainbow == rainbowCount) {

						// 3) 기준블록행이 크면 갱신
						if (maxR < tempBlock.peek()[0]) {
							total = tempBlock.size();
							rainbow = rainbowCount;
							maxR = tempBlock.peek()[0];
							maxC = tempBlock.peek()[1];
						} else if (maxR == tempBlock.peek()[0]) {

							// 4) 기준블록열이 크면 갱신
							if (maxC < tempBlock.peek()[1]) {
								total = tempBlock.size();
								rainbow = rainbowCount;
								maxR = tempBlock.peek()[0];
								maxC = tempBlock.peek()[1];
							}
						}
					}
				}
			}
		}
	}

	static void remove() {

		// 찾은 블록 그룹 기준행을 시작으로 BFS를 한번 더 하면서 블록 삭제 -> -9로 저장
		Queue<int[]> q = new LinkedList<>();
		q.add(new int[] { maxR, maxC });
		boolean[][] visited = new boolean[N][N];

		int color = board[maxR][maxC];

		while (!q.isEmpty()) {
			int[] temp = q.poll();
			int r = temp[0];
			int c = temp[1];
			board[r][c] = -9;

			for (int d = 0; d < 4; d++) {
				int nr = r + dr[d];
				int nc = c + dc[d];
				// 격자판을 벗어났거나 이미 방문했으면 건너뛰기
				if (nr < 0 || nc < 0 || nr >= N || nc >= N || visited[nr][nc])
					continue;
				// 검정색이거나 무지개가 아닌데 색이 맞지 않거나 빈곳이면 건너뛰기
				if (board[nr][nc] == -1 || (board[nr][nc] != 0 && board[nr][nc] != color) || board[nr][nc] == -9)
					continue;

				// 다음블록으로
				q.add(new int[] { nr, nc });
				visited[nr][nc] = true;
			}

		}
	}

	static void gravity() {

		for (int j = 0; j < N; j++) {
			// 빈칸 찾는 행
			int under = N - 1;
			// 무지개나 일반블록 찾는 행
			int top = under - 1;

			while (under >= 0 && top >= 0) {
				// 빈칸 찾기
				while (under >= 0 && board[under][j] != -9)
					under--;
				// top을 under 바로 위에서부터 시작
				top = under - 1;
				// 우선 검정색까지 포함해서 블록 찾기
				while (top >= 0 && board[top][j] == -9)
					top--;
				// 배열 밖으로 벗어나면 종료
				if (under < 0 || top < 0)
					break;
				// 검정블록이면 검정블록위에서부터 다시 찾기
				if (board[top][j] == -1) {
					under = top - 1;
					continue;
				}
				// 위의 예외를 다 거치고 빈칸과 검정블록 아닌 블록을 찾았을 때
				// 블록 내리기
				board[under][j] = board[top][j];
				board[top][j] = -9;
			}
		}
	}

	static void rotation() {

		// 가장 바깥부터 회전
		for (int start = 0; start < N / 2; start++) {
			// 현재 회전할 테두리 길이
			int len = N - start * 2 - 1;
			// 현재 테두리의 변마다 한칸씩 회전 (4개씩)
			for (int i = 0; i < len; i++) {
				int idx = i+start;
				int temp = board[start][idx];
				board[start][idx] = board[idx][start + len];
				board[idx][start + len] = board[start + len][start + len - i];
				board[start + len][start + len - i] = board[start + len - i][start];
				board[start + len - i][start] = temp;
			}
		}
	}
}