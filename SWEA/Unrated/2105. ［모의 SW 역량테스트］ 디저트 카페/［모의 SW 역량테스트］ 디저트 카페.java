import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;

public class Solution {
	
	// 지역크기
	static int N;
	// 카페지도
	static int[][] cafe;
	// 시작좌표
	static int R;
	static int C;
	// 디저트 종류 중복 확인
	static Set<Integer> dessert;
	// 먹을 수 있는 디저트 최대 개수
	static int max;
	
	static int[] dr = {1, 1, -1, -1};
	static int[] dc = {1, -1, -1, 1};
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		int T = Integer.parseInt(br.readLine());
		for(int t = 1;t<=T;t++) {
			N = Integer.parseInt(br.readLine());
			cafe = new int[N][N];
			for(int i = 0;i<N;i++) {
				StringTokenizer st = new StringTokenizer(br.readLine());
				for(int j = 0;j<N;j++) {
					cafe[i][j] = Integer.parseInt(st.nextToken());
				}
			} // 입력 완료
			
			// 디저트를 먹지 못할 땐 -1 출력이기 때문에 최소를 -1로 초기화
			max = -1;
			
			// 가장 왼쪽 열, 가장 오른쪽 열, 가장 밑 행에서 시작할 수는 X
			// 이 외 나머지의 모든 카페에서 시작
			for(int i = 0;i<N-1;i++) {
				for(int j = 1;j<N-1;j++) {
					// 시작위치 저장
					R = i;
					C = j;
					// 디저트 종류 초기화
					dessert = new HashSet<>();
					// 카페 투어 출발~
					start(i, j, 0);
				}
			}
			
			sb.append("#" + t + " " + max + "\n");
			
		}
		System.out.println(sb);
	}
	
	// 카페 투어 메서드
	static void start(int i, int j, int dir) {
		
		// 방향이 오른쪽위로 올라가는 대각선 방향이면서 시작 위치에 도달했을 때 멈추고 종류 개수 최댓값 갱신
		if(dir==3 && i==R && j==C) {
			max = Math.max(max, dessert.size());
			return;
		}
		
		// 현재 방향으로 한칸 전진
		i += dr[dir];
		j += dc[dir];
		
		// 배열을 벗어낫을 경우 리턴
		if(i<0 || i>=N || j<0 || j>=N) return;
		
		// 현재 위치의 디저트를 이미 먹었으면 리턴
		if(dessert.contains(cafe[i][j])) return;
		
		// 이제 이 카페는 갈 수 있는 것!
		
		// 디저트 종류 저장
		dessert.add(cafe[i][j]);
		
		// 현재 방향을 유지한 채 다음 카페로 or 방향을 꺾고 다음 카페로
		start(i, j, dir);
		if(dir<3) { // 오른쪽대각선 위로 올라가는 방향일 땐 더이상 방향을 꺾을 수 없음
			start(i, j, dir+1);
		}

		// 디저트 종류 다시 빼기
		dessert.remove(cafe[i][j]);
		
	}
}