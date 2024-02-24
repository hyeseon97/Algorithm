import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;

public class Solution {

	// 재료 수
	static int N;
	// 재료별 시너지 조합
	static int[][] synergy;
	// 총 재료, 여기서 A에 쓸 재료는 빼가고 남은 재료를 B에 넣어줄것임
	static Set<Integer> food;
	// A에 쓰이는 재료
	static List<Integer> foodA;
	// B에 쓰이는 재료
	static List<Integer> foodB;
	// 요리에서 시너지 계산하기 위해 재료 2개씩 빼올 임시 배열
	static int[] sel;
	// A요리 시너지
	static int synergyA;
	// B요리 시너지
	static int synergyB;
	// 최소 시너지
	static int min;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		int T = Integer.parseInt(br.readLine());
		for (int t = 1; t <= T; t++) {
			N = Integer.parseInt(br.readLine());
			synergy = new int[N][N];
			food = new HashSet<>();
			for (int i = 0; i < N; i++) {
				StringTokenizer st = new StringTokenizer(br.readLine());
				food.add(i);
				for (int j = 0; j < N; j++) {
					synergy[i][j] = Integer.parseInt(st.nextToken());
				}
			} // 입력 완료

			min = Integer.MAX_VALUE;
			foodA = new ArrayList<>();
			foodB = new ArrayList<>();
			cook(0, 0);
			
			sb.append("#" + t + " " + min + "\n");

		}
		System.out.println(sb);
	}
	
	// A 재료에서 2개 뽑아서 시너지 계산
	static void combiA(int idx, int i) {
		
		if(idx == 2) {
			synergyA += synergy[foodA.get(sel[0])][foodA.get(sel[1])];
			synergyA += synergy[foodA.get(sel[1])][foodA.get(sel[0])];
			return;
		}
		
		if(i == N/2) {
			return;
		}
		
		sel[idx] = i;
		combiA(idx+1, i+1);
		combiA(idx, i+1);
		
	}

	// B 재료에서 2개 뽑아서 시너지 계산
	static void combiB(int idx, int i) {
		
		if(idx == 2) {
			synergyB += synergy[foodB.get(sel[0])][foodB.get(sel[1])];
			synergyB += synergy[foodB.get(sel[1])][foodB.get(sel[0])];
			return;
		}
		
		if(i == N/2) {
			return;
		}
		
		sel[idx] = i;
		combiB(idx+1, i+1);
		combiB(idx, i+1);
		
	}
	
	// 재료를 2개로 나누는 메서드
	static void cook(int idx, int i) {
		
		if(idx == N/2) {
			// 남은 재료를 B에 넣어주기
			foodB = new ArrayList<>(food);
			
			// 시너지 계산 초기화
			synergyA = 0;
			synergyB = 0;
			
			sel = new int[2];
			combiA(0, 0);
			sel = new int[2];
			combiB(0, 0);
			
			min = Math.min(min, Math.abs(synergyA-synergyB));
			return;
		}
		
		if(i == N) {
			return;
		}
		
		foodA.add(i);
		food.remove(i);
		cook(idx+1, i+1);
		
		foodA.remove((Integer)i);
		food.add(i);
		cook(idx, i+1);
		
	}
}