import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Solution {
	
	// 자석을 회전시킬 횟수
	static int K;
	// 자석4개의 정보
	static String[] magnet;
	// 자석 회전시킬 정보
	// 회전시킬 자석 번호, 회전방향
	static int[][] move;
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder answer = new StringBuilder();
		int T = Integer.parseInt(br.readLine());
		for(int t = 1;t<=T;t++) {
			K = Integer.parseInt(br.readLine());
			magnet = new String[4];
			for(int i = 0;i<4;i++) {
				StringTokenizer st = new StringTokenizer(br.readLine());
				StringBuilder sb = new StringBuilder();
				for(int j = 0;j<8;j++) {
					sb.append(st.nextToken());
				}
				magnet[i] = sb.toString();
			}
			move = new int[K][2];
			for(int i = 0;i<K;i++) {
				StringTokenizer st = new StringTokenizer(br.readLine());
				for(int j = 0;j<2;j++) {
					move[i][j] = Integer.parseInt(st.nextToken());
				}
			} // 입력 완료
			
			for(int turn = 0;turn<K;turn++) {
				
				int num = move[turn][0]-1;
				int dir = move[turn][1];
				
				// 자석이 이번 회차에 회전할 방향 저장
				// 0이면 회전안함
				int[] rotation = new int[4];
				rotation[num] = dir;
				
				// 회전시킬 자석의 왼쪽자석들 조사
				int i = num-1;
				int d = dir*-1;
				while(i>=0) {
					// 왼쪽의 2번과 오른쪽의 6번이 다를때 왼쪽자석 회전
					if(magnet[i].charAt(2)!=magnet[i+1].charAt(6)) {
						rotation[i] = d;
					} else {
						break; // 같으면 여기서 끝
					}
					i--; d*=-1; // 다음왼쪽으로 넘어가고 방향도 바꾸고
				}
				
				// 회전시킬 자석의 오른쪽자석들 조사
				i = num+1;
				d = dir*-1;
				while(i<4) {
					// 왼쪽의 2번과 오른쪽의 6번이 다를때 오른쪽자석 회전
					if(magnet[i-1].charAt(2)!=magnet[i].charAt(6)) {
						rotation[i] = d;
					} else {
						break; // 같으면 여기서 끝
					}
					i++; d*=-1; // 다음오른쪽으로 넘어가고 방향도 바꾸고
				}
				
				// 회전방향에 따라 회전시키기
				for(int r = 0;r<4;r++) {
					// 시계방향 회전 -> 뒤에있는거 떼와서 앞에 붙이기
					if(rotation[r]==1) {
						char s = magnet[r].charAt(7);
						magnet[r] = s + magnet[r].substring(0, 7);
					} else if(rotation[r]==-1) { // 반시계방향 회전 -> 앞에있는거 떼서 뒤에 붙이기
						char s = magnet[r].charAt(0);
						magnet[r] = magnet[r].substring(1, 8) + s;
					}
				}
			}
			
			// 자석의 맨위에 극에 따라 결과 계산하기
			int result = 0;
			for(int i = 0;i<4;i++) {
				if(magnet[i].charAt(0)=='1') {
					result = result | (1<<i);
				}
			}
			
			answer.append("#" + t + " " + result + "\n");
		}
		System.out.println(answer);
	}
}