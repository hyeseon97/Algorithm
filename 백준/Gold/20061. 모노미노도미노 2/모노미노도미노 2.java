import java.util.*;
import java.io.*;

public class Main {

	// 블록을 놓은 횟수
	static int N;

	// 블록 놓을 정보
	static int[][] info;

	// 보드판
	static boolean[][] board;

	// 점수
	static int score;

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.parseInt(br.readLine());
		info = new int[N][3];
		board = new boolean[10][10];
		score = 0;

		for (int n = 0; n < N; n++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			for (int m = 0; m < 3; m++) {
				info[n][m] = Integer.parseInt(st.nextToken());
			}
		}

		// 횟수만큼 반복
		for (int n = 0; n < N; n++) {
			int t = info[n][0];
			int x = info[n][1];
			int y = info[n][2];

			int[][] block = null;
			if (t == 1) {
				block = new int[][] { { t, x, y } };
			} else if (t == 2) {
				block = new int[2][2];
				block = new int[][] { { t, x, y }, { t, x, y + 1 } };
			} else if (t == 3) {
				block = new int[2][2];
				block = new int[][] { { t, x, y }, { t, x + 1, y } };
			}

			putGreen(block);
			putBlue(block);
		}
		
		int total = 0;
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				if(board[i][j]) total++;
			}
		}
		
		System.out.println(score);
		System.out.println(total);
	}

	static void putGreen(int[][] block) {

		// 현재 블록 타입
		int type = block[0][0];

		// 각 블록이 위치할 수 있는 가장 낮은 곳 찾기
		int[] index = new int[block.length];

		for (int b = 0; b < block.length; b++) {
			int x = block[b][1];
			int y = block[b][2];

			for (int idx = 4; idx <= 9; idx++) {
				if (board[idx][y])
					break;
				else {
					index[b] = idx;
				}
			}
		}

		// 각 블록이 위치할 수 있는 곳 중 최소값이 최종 위치
		int result = 10;
		for (int b = 0; b < block.length; b++) {
			result = Math.min(result, index[b]);
		}

		// 최종위치에 저장
		for (int b = 0; b < block.length; b++) {
			int x = block[b][1];
			int y = block[b][2];

			// 세로블록의 아래블록일때만 이 result의 위에 놔야함
			if (type == 3 && b == 1) {
				board[result - 1][y] = true;
			} else {
				board[result][y] = true;
			}
		}

		// 최종 위치가 연한색이 아닌 곳에 있으면
		if (result >= 6) {

			// 현재 위치의 행이 다 찼을 때 삭제
			// 단, type이 3인 세로블록일때만 result의 -1위치에서도 행이 다 찼을 때 삭제해야하므로 한번 더 반복
			for (int seq = 0; seq < 2; seq++) {

				int count = 0;
				for (int j = 0; j < 4; j++) {
					if (board[result][j])
						count++;
				}

				if (count == 4) {
					score++; // 점수 더해주고
					for (int i = result; i >= 4; i--) {
						for (int j = 0; j < 4; j++) {
							if (i == result) {
								board[i][j] = false;
							} else if (board[i][j]) {
								board[i][j] = false;
								board[i + 1][j] = true;
							}
						}
					}
				} else {
					result--;
				}

				if (type != 3)
					seq++;
			}
		}

		// 연한 위치에 아직 남아있는 만큼 아래로 내리기
		int down = 0;
		for (int i = 4; i <= 5; i++) {
			for (int j = 0; j < 4; j++) {
				if (board[i][j]) {
					down++;
					break;
				}
			}
		}

		for (int i = 9; i >= 4; i--) {
			for (int j = 0; j < 4; j++) {
				if (i > 9 - down) {
					board[i][j] = false;
				} else if (board[i][j]) {
					board[i][j] = false;
					board[i + down][j] = true;
				}
			}
		}
	}

	static void putBlue(int[][] block) {

		// 현재 블록 타입
		int type = block[0][0];

		// 각 블록이 위치할 수 있는 가장 낮은 곳 찾기
		int[] index = new int[block.length];

		for (int b = 0; b < block.length; b++) {
			int x = block[b][1];
			int y = block[b][2];

			for (int idx = 4; idx <= 9; idx++) {
				if (board[x][idx])
					break;
				else {
					index[b] = idx;
				}
			}
		}

		// 각 블록이 위치할 수 있는 곳 중 최소값이 최종 위치
		int result = 10;
		for (int b = 0; b < block.length; b++) {
			result = Math.min(result, index[b]);
		}

		// 최종위치에 저장
		for (int b = 0; b < block.length; b++) {
			int x = block[b][1];
			int y = block[b][2];

			// 가로블록의 오른쪽블록일때만 이 result의 왼쪽에 놔야함
			if (type == 2 && b == 1) {
				board[x][result-1] = true;
			} else {
				board[x][result] = true;
			}
		}

		// 최종 위치가 연한색이 아닌 곳에 있으면
		if (result >= 6) {

			// 현재 위치의 행이 다 찼을 때 삭제
			// 단, type이 2인 가로블록일때만 result의 -1위치에서도 열이 다 찼을 때 삭제해야하므로 한번 더 반복
			for (int seq = 0; seq < 2; seq++) {

				int count = 0;
				for (int i = 0; i < 4; i++) {
					if (board[i][result])
						count++;
				}

				if (count == 4) {
					score++; // 점수 더해주고
					for (int j = result; j >= 4; j--) {
						for (int i = 0; i < 4; i++) {
							if (j == result) {
								board[i][j] = false;
							} else if (board[i][j]) {
								board[i][j] = false;
								board[i][j + 1] = true;
							}
						}
					}
				} else {
					result--;
				}

				if (type != 2)
					seq++;
			}
		}

		// 연한 위치에 아직 남아있는 만큼 아래로 내리기
		int down = 0;
		for (int j = 4; j <= 5; j++) {
			for (int i = 0; i < 4; i++) {
				if (board[i][j]) {
					down++;
					break;
				}
			}
		}

		for (int j = 9; j >= 4; j--) {
			for (int i = 0; i < 4; i++) {
				if (j > 9 - down) {
					board[i][j] = false;
				} else if (board[i][j]) {
					board[i][j] = false;
					board[i][j + down] = true;
				}
			}
		}
	}

}