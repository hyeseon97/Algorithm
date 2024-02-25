import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Solution {

	// 접수창구 개수
	static int N;
	// 정비창구 개수
	static int M;
	// 고객 수
	static int K;
	// 지갑 두고 간 고객 접수창구 번호
	static int A;
	// 지갑 두고 간 고객 정비창구 번호
	static int B;
	// 접수창구 별 걸리는 시간
	static int[] receptionTime;
	// 정비창구 별 걸리는 시간
	static int[] repairTime;
	// 고객들이 정비소 방문하는 시간
	static int[] arriveTime;

	// 접수창구 대기 인원 큐
	// 0:고객번호 1:접수창구에 온 시간
	static PriorityQueue<int[]> receptionQ;
	// 정비창구 대기 인원 큐
	// 0:고객번호 1:정비창구에 온 시간 2:접수창구 번호
	static PriorityQueue<int[]> repairQ;
	
	// 접수창구 현황
	static int[][] reception;
	static int receptionCount;
	// 정비창구 현황
	static int[][] repair;
	static int repairCount;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		int T = Integer.parseInt(br.readLine());
		for (int t = 1; t <= T; t++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			N = Integer.parseInt(st.nextToken());
			M = Integer.parseInt(st.nextToken());
			K = Integer.parseInt(st.nextToken());
			A = Integer.parseInt(st.nextToken());
			B = Integer.parseInt(st.nextToken());

			receptionTime = new int[N+1];
			repairTime = new int[M+1];
			arriveTime = new int[K+1];

			reception = new int[N+1][2];
			receptionCount = 0;
			repair = new int[M+1][3];
			repairCount = 0;
			
			// 우선순위큐를 선언하고 정렬을 재정의
			sorting();
			
			st = new StringTokenizer(br.readLine());
			for (int i = 1; i <= N; i++) {
				receptionTime[i] = Integer.parseInt(st.nextToken());
			}
			st = new StringTokenizer(br.readLine());
			for (int i = 1; i <= M; i++) {
				repairTime[i] = Integer.parseInt(st.nextToken());
			}
			st = new StringTokenizer(br.readLine());
			for (int i = 1; i <= K; i++) {
				arriveTime[i] = Integer.parseInt(st.nextToken());
				receptionQ.add(new int[] {i, arriveTime[i]});
			}
			// 입력 완료 ---------------------------------------------------
			
			int result = 0;
			
			int time = 0;
			while(!receptionQ.isEmpty() || !repairQ.isEmpty() || receptionCount!=0 || repairCount!=0) {
				
				// 1. 접수창구에 있는 사람들 중 작업이 끝났으면 정비창구 큐로 보내기
				for(int i = 1;i<=N;i++) {
					// 사람이 있는 창구에서 사람이 창구에 들어온 시간 + 접수시간 = 현재시간 (=작업완료)
					if(reception[i][0]!=0 && reception[i][1]+receptionTime[i]==time) {
						repairQ.add(new int[] {reception[i][0], time, i});
						reception[i] = new int[2];
						receptionCount--;
					}
				}
				
				// 2. 접수창구 큐에 있는 사람들 중 도착한 사람들 접수창구로 넣기
				for(int i = 1;i<=N;i++) {
					// 사람이 있으면 건너뛰고 다음 창구 알아보기
					if(reception[i][0]!=0) continue;
					// 대기하고 있는 사람이 있으면서 도착했으면 넣기
					if(!receptionQ.isEmpty() && receptionQ.peek()[1]<=time) {
						int[] temp = receptionQ.poll();
						reception[i] = new int[] {temp[0], time};
						receptionCount++;
					}
				}
				
				// 3. 정비창구에 있는 사람들 중 작업이 끝났으면 내보내기
				for(int i = 1;i<=M;i++) {
					// 사람이 있는 창구에서 사람이 창구에 들어온 시간 + 정비시간 = 현재시간 (=작업완료)
					if(repair[i][1]!=0 && repair[i][1]+repairTime[i]==time) {
						// 접수창구번호와 정비창구번호가 A, B와 같으면 사람번호 더하기
						if(repair[i][2]==A && i==B) {
							result += repair[i][0];
						}
						repair[i] = new int[3];
						repairCount--;
					}
				}
				
				// 4. 정비창구 큐에 있는 사람들을 순서대로 정비창구로 넣기
				for(int i = 1;i<=M;i++) {
					// 사람이 있거나 대기자가 없을 때 건너뛰기
					if(repair[i][0]!=0 || repairQ.isEmpty()) continue;
					int[] temp = repairQ.poll();
					repair[i] = new int[] {temp[0], time, temp[2]};
					repairCount++;
				}
				
				time++;
			}
			
			if(result == 0) result = -1;
			sb.append("#" + t + " " + result + "\n");

		}
		
		System.out.println(sb);
	}

	// 우선순위 큐 사용자 정의 재정렬
	static void sorting() {
		// 접수창구에 온 시간 -> 고객번호 순으로 재정렬
		receptionQ = new PriorityQueue<>(new Comparator<int[]>() {
			@Override
			public int compare(int[] o1, int[] o2) {

				if (o1[1] < o2[1]) {
					return -1;
				} else if (o1[1] == o2[1]) {
					if (o1[0] < o2[0]) {
						return -1;
					}
					return 1;
				} else {
					return 1;
				}

			}
		});

		// 정비창구에 온 시간 -> 접수창구 번호 순으로 재정렬
		repairQ = new PriorityQueue<>(new Comparator<int[]>() {
			@Override
			public int compare(int[] o1, int[] o2) {

				if (o1[1] < o2[1]) {
					return -1;
				} else if (o1[1] == o2[1]) {
					if (o1[2] < o2[2]) {
						return -1;
					}
					return 1;
				} else {
					return 1;
				}

			}
		});
	}
}