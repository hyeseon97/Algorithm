import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
	
	// 컨베이어 벨트 길이
	static int N;
	
	// 벨트 내구도
	static int[] belt;
	
	// 벨트위 로봇 여부
	static boolean[] robot;
	
	// 올리는 위치
	static int S;
	// 내리는 위치
	static int E;
	
	// 종료되어야하는 조건
	static int K;
	
	// 내구도 0인 칸 갯수 
	static int count;

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		K = Integer.parseInt(st.nextToken());
		belt = new int[N*2];
		robot = new boolean[N*2];
		st = new StringTokenizer(br.readLine());
		for(int i = 0;i<N*2;i++) {
			belt[i] = Integer.parseInt(st.nextToken());
		}
		S = 0;
		E = N-1;
		count = 0;
		
		int round = 0;
		while(count < K) {

			round++;
			
			// 1. 벨트 회전
			rotation();
			
			// 2. 로봇 이동
			move();
			
			// 3. 로봇 올리기
			put();
			
		}
		
		System.out.println(round);
		
		
	}
	
	static void rotation() {
		
		// 벨트 회전은 올리는위치와 내리는위치를 하나 이전으로 옮기는 것
		S = S-1==-1?N*2-1:S-1;
		E = E-1==-1?N*2-1:E-1;
		
		// 벨트가 회전해서 내리는 위치에 어떤 로봇이 도착했으면 내리기
		if(robot[E]) {
			robot[E] = false;
		}
		
	}
	
	static void move() {
		
		// 내리는 위치 이전에서부터 올리는 위치까지 로봇들이 있으면 한칸 앞으로 이동
		int idx = E-1==-1 ? N*2-1 : E-1;
		for(int i = 0;i<N;i++) {
			
			// 현재 위치에 로봇이 있을 때
			if(robot[idx]) {
				
				// 다음 위치에 로봇이 없고 내구도가 1이상일 때
				int nidx = (idx+1)%(N*2);
				if(!robot[nidx] && belt[nidx]>=1) {
					robot[idx] = false;
					robot[nidx] = true;
					belt[nidx]--;
					
					// 내구도가 0이 되었으면
					if(belt[nidx] == 0) {
						count++;
					}
					
					// 다음위치가 내리는 위치일 때
					if(nidx == E) {
						robot[nidx] = false;
					}
				}
			}
			
			idx = idx-1==-1 ? N*2-1 : idx-1;
		}
	}
	
	static void put() {
		
		// 올리는 위치의 내구도가 1이상일 때
		if(belt[S]>=1) {
			robot[S] = true;
			belt[S]--;
			
			// 내구도가 0이 되었으면
			if(belt[S] == 0) {
				count++;
			}
		}
	}
}