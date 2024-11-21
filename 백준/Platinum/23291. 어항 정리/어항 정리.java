import java.io.*;
import java.util.*;

public class Main {

	// 어항 개수
	static int N;

	// 최대 최소 차이가 되야하는 수
	static int K;

	// 어항 배열 세로 크기 (N에 따라 대략 여유있게 설정), 가로 크기는 N
	static int H;

	// 어항 배열
	static int[][] fish;
	
	// 어항 임시 복사 배열
	static int[][] tempFish;

	// 1번과정에서 사용할 가장 적은 어항들 위치
	static List<int[]> smallList;

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		K = Integer.parseInt(st.nextToken());
		H = Math.max((int) Math.sqrt(N) + 1, 4); // 180도 회전해서 쌓는 과정이 2번이어서 최소 4칸이어야 함
		fish = new int[H][N];
		st = new StringTokenizer(br.readLine());
		for (int n = 0; n < N; n++) {
			// 가장 밑 바닥에 일렬로 저장
			fish[H - 1][n] = Integer.parseInt(st.nextToken());
		}

		// 정리 반복
		int clean = 0;
		while (true) {

			// 최대 최소 차이 구하기
			if (cal() <= K) {
				break;
			}

			// 정리 시작
			clean++;

			// 1. 가장 적은 어항들에 +1
			plusOne();

			// 2. 공중부양시켜서 90도 회전시켜 쌓기
			build90();

			// 3. 인접한 어항 조절
			balance();

			// 4. 일렬로 놓기
			put();

			// 5. 공중부양시켜서 180도 회전시켜 쌓기
			build180();

			// 6. 인접한 어항 조절
			balance();

			// 7. 일렬로 놓기
			put();
			

		}
		
		System.out.println(clean);

	}

	// 1. 가장 적은 어항들에 +1 ---------------------------------------------
	static void plusOne() {
		
		for(int i = 0;i<smallList.size();i++) {
			int r = smallList.get(i)[0];
			int c = smallList.get(i)[1];
			fish[r][c] += 1;
		}
	}

	// 2. 공중부양시켜서 90도 회전시켜 쌓기 ------------------------------------
	static void build90() {
		
		// 공중부양시킬 어항중 왼쪽아래 위치
		int R = H-1;
		int C = 0;
		// 공중부양시킬 격자 크기
		int a = 1;
		int b = 1;
		
		while(true) {
			
			// r, c가 공중부양하고 회전한 후 놓일 위치
			int newR = R-a;
			int newC = C+a;
			
			// 어항들 90도 회전해서 놓일 새 위치로 이동
			for(int j = C;j<C+a;j++) {
				for(int i = R;i>R-b;i--) {
					int temp = fish[i][j];
					fish[i][j] = 0;
					fish[newR][newC++] = temp;
				}
				newR++;
				newC = C+a;
			}
			
			// C 기준위치 수정
			C = C+a;
			
			// 격자크기 수정
			if(a == b) b++;
			else a++;
			
			// 다음 공중부양 할 수 있는가
			// 다음 어항들 90회전하고 놓을때 배열밖을 벗어나면 끝
			if(C+a+b-1 >= N) break;
			
		}
	}

	// 3. 인접한 어항 조절 --------------------------------------------------
	static void balance() {
		
		copyArr();
		
		// 0이 아니면 오른쪽과 위와의 인접어항에 대해 비교해서 조절
		for(int r = H-1;r>=0;r--) {
			for(int c = 0;c<N;c++) {
				if(tempFish[r][c] == 0) continue;
				// 오른쪽 어항있을때
				if((c+1) < N && tempFish[r][c+1] != 0) {
					int d = Math.abs(tempFish[r][c] - tempFish[r][c+1]) / 5;
					if(tempFish[r][c] > tempFish[r][c+1]) {
						fish[r][c] -= d;
						fish[r][c+1] += d;
					}else {
						fish[r][c] += d;
						fish[r][c+1] -= d;
					}
				}
				
				// 위쪽 어항있을때
				if((r-1) >= 0 && tempFish[r-1][c] != 0) {
					int d = Math.abs(tempFish[r][c] - tempFish[r-1][c]) / 5;
					if(tempFish[r][c] > tempFish[r-1][c]) {
						fish[r][c] -= d;
						fish[r-1][c] += d;
					}else {
						fish[r][c] += d;
						fish[r-1][c] -= d;
					}
				}
				
			}
		}
	}
	
	static void copyArr() {
		
		tempFish = new int[H][N];
		for(int i = 0;i<H;i++) {
			for(int j = 0;j<N;j++) {
				tempFish[i][j] = fish[i][j];
			}
		}
	}

	// 4. 일렬로 놓기 ------------------------------------------------------
	static void put() {
		
		// 어항들중 가장 왼쪽아래 위치
		int r = H-1;
		int c = 0;
		while(true) {
			if(fish[r][c] != 0) break;
			c++;
		}
		
		// 바닥의 왼쪽부터 순서대로 놓기
		for(int idx = 0;idx<N;idx++) {
			int temp = fish[r][c];
			fish[r][c] = 0;
			fish[H-1][idx] = temp;
			r--;
			if(r == -1 || fish[r][c] == 0) {
				r = H-1;
				c++;
			}
		}
		
	}

	// 5. 공중부양시켜서 180도 회전시켜 쌓기 ------------------------------------
	static void build180() {
		
		// 한번
		for(int idx = 0;idx<N/2;idx++) {
			int temp = fish[H-1][idx];
			fish[H-1][idx] = 0;
			fish[H-2][N-idx-1] = temp; // 왼쪽 1층 왼쪽 어항부터 오른쪽 2층 오른쪽부터로 이동
		}
		
		// 두번
		for(int idx = N/2;idx<N*3/4;idx++) {
			int temp = fish[H-1][idx];
			fish[H-1][idx] = 0;
			fish[H-4][N-idx-1+N/2] = temp; // 왼쪽 1층 왼쪽 어항부터 오른쪽 4층 오른쪽부터로 이동
			temp = fish[H-2][idx];
			fish[H-2][idx] = 0;
			fish[H-3][N-idx-1+N/2] = temp; // 왼쪽 2층 왼쪽 어항부터 오른쪽 3층 오른쪽부터로 이동
		}
	}


	// 최대 최소 구하는 메서드 ------------------------------------------------
	static int cal() {

		int min = 10001;
		int max = 0;
		smallList = new ArrayList<>();

		for (int idx = 0; idx < N; idx++) {
			max = Math.max(max, fish[H-1][idx]);
			if (min > fish[H-1][idx]) {
				min = fish[H-1][idx];
				smallList = new ArrayList<>();
				smallList.add(new int[] { H-1, idx });
			} else if (min == fish[H-1][idx]) {
				smallList.add(new int[] { H-1, idx });
			}
		}
		
		return max - min;
	}
}