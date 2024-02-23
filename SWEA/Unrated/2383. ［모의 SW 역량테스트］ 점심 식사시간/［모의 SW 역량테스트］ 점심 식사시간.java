import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.StringTokenizer;

public class Solution {

	// 방의 크기
	static int N;
	// 방 정보
	static int[][] room;
	// 사람 위치
	static List<int[]> people;
	// 사람 수
	static int P;
	// 입구 위치와 계단 수
	static List<int[]> stair;
	// 최소시간
	static int min;
	// 사람들을 어느 입구로 나가게 할 것인지
	static int[] go;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		int T = Integer.parseInt(br.readLine());
		for (int t = 1; t <= T; t++) {
			N = Integer.parseInt(br.readLine());
			room = new int[N][N];
			people = new ArrayList<>();
			stair = new ArrayList<>();
			for (int i = 0; i < N; i++) {
				StringTokenizer st = new StringTokenizer(br.readLine());
				for (int j = 0; j < N; j++) {
					room[i][j] = Integer.parseInt(st.nextToken());
					if (room[i][j] >= 2) {
						stair.add(new int[] { i, j, room[i][j] });
					} else if (room[i][j] == 1) {
						people.add(new int[] { i, j });
					}
				}
			} // 입력 완료

			P = people.size();
			go = new int[P];
			min = Integer.MAX_VALUE;

			arrangement(0);
			sb.append("#" + t + " " + min + "\n");
		}
		System.out.println(sb);
	}

	// 계단입구 번호에 따른 사람들의 이동시간 계산하기
	static int simulation(int stairNum) {

		// 계단에 도착한 사람들의 시간을 오름차순으로 정렬
		PriorityQueue<Integer> time = new PriorityQueue<>();
		for (int i = 0; i < P; i++) {
			if (go[i] == stairNum) {
				// 이동시간 = |PR-SR| + |PC-SC| |사람세로위치-계단입구세로위치|+|사람가로위치-계단입구가로위치|
				int PR = people.get(i)[0];
				int SR = stair.get(stairNum)[0];
				int PC = people.get(i)[1];
				int SC = stair.get(stairNum)[1];
				time.add(Math.abs(PR - SR) + Math.abs(PC - SC));
			}
		}

		// 걸린 시간
		int moveTime = 0;
		// 계단에 있는 사람의 계단을 내려가기 시간한 시간 저장 (이 큐에는 최대 3명만 있을 수 있음)
		Queue<Integer> down = new LinkedList<>();
		while (true) {
			// 시간 증가
			moveTime++;

			// 계단큐가 비어있지 않으면서 계단을 다 내려온 사람은 빼기
			// 게단을 내려가기 시작한 시간 + 계단 수 가 현재 시간과 맞아야 다 내려온 것!
			while (!down.isEmpty() && down.peek() + stair.get(stairNum)[2] == moveTime) {
				down.poll();
			}

			// 계단에 있는 사람이 3명 미만이면서 계단에 도착한 사람이 있으면 계단에 넣어주기
			// 계단에 도착한 시간 + 1 이 현재시간보다 작으면 이미 도착했으므로 계단에 넣어줄 수 있음!
			while (down.size() < 3 && !time.isEmpty() && time.peek() + 1 <= moveTime) {
				time.poll();
				down.add(moveTime); // 현재 시간을 넣어주기 왜냐, 이미 도착했는데 기다리고 있을 수 있기 때문에 계단입구에 도착한 시간을 넣어주면 안됨
			}

			// 계단에 있는 사람도 없고 기다리는 사람도 없을 때 모두 이동 완료
			if (time.isEmpty() && down.isEmpty()) {
				break;
			}

		}

		return moveTime;

	}

	// 사람들을 어느 계단입구로 나가게 할 것인지 정해주기
	static void arrangement(int p) {

		// 모든 사람들을 다 정해줬으면 나가는 시간 계산하기
		if (p == P) {
			// 0번 입구 사람들이 걸린 시간과 1번 입구 사람들이 걸린 시간 중 최대 시간과 min값을 비교해서 min 값 갱신
			min = Math.min(min, Math.max(simulation(0), simulation(1)));
			return;
		}

		// p번 사람을 0번 입구로 보내기
		go[p] = 0;
		arrangement(p + 1);
		// p번 사람을 1번 입구로 보내기
		go[p] = 1;
		arrangement(p + 1);
	}
}