import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.StringTokenizer;

public class Solution {
	
	// 지도 크기
	static int N;
	// 지도
	static int[][] map;
	// 웜홀 위치 저장
	static Map<Integer, int[]> wormhole;
	// 최대 점수
	static int max;
	
	static int[] dr = {-1, 0, 1, 0};
	static int[] dc = {0, -1, 0, 1};
	
	// [i][j]
	// i는 블록번호
	// j는 현재 진행 방향
	// 에 대해 해당 블록을 만나면 바뀌는 방향
	static int[][] block = {{-1, -1, -1, -1}, {2, 0, 3, 1}, {3, 2, 0, 1}, {1, 3, 0, 2}, {2, 3, 1, 0}, {2, 3, 0, 1}};
	
	public static void main(String[] args) throws IOException {
		Scanner sc = new Scanner(System.in);
		StringBuilder sb = new StringBuilder();
		int T = sc.nextInt();
		for(int t = 1;t<=T;t++) {
			N = sc.nextInt();
			map = new int[N][N];
			wormhole = new HashMap<>();
			for(int i = 0;i<N;i++) {
				for(int j = 0;j<N;j++) {
					map[i][j] = sc.nextInt();
					
					// 웜홀일 때
					if(map[i][j]>=6 && map[i][j]<=10) {
						// 웜홀 숫자를 양수 또는 음수로 저장하기
						// wormhole에 내 위치를 저장한 key와 반대의 부호를 가진 숫자로 map에 저장
						// -> 그래야 map에 저장된 숫자로 바로 wormhole에서 반대쪽 웜홀의 위치를 꺼내올 수 있음
						int num = map[i][j];
						if(wormhole.containsKey(map[i][j])) num *= -1;
						wormhole.put(num, new int[] {i, j});
						map[i][j] = -num;
					}
				}
			} // 입력 끝
			
			// 최대 점수
			max = 0;
			
			// 모든 칸에 대해서 시작해보기
			for(int i = 0;i<N;i++) {
				for(int j = 0;j<N;j++) {
					// 블록, 웜홀, 블랙홀이면 건너뛰기 (시작할 수 없음)
					if(map[i][j] != 0) continue;
					
					game(i, j);
				}
			}
			
			sb.append("#" + t + " " + max + "\n");
		}
		System.out.println(sb);
	}
	
	static void game(int R, int C) {
		
		// 현재 위치 (R, C)에서 4방향으로 게임 시작해보기
		for(int d = 0;d<4;d++) {
			int r = R+dr[d];
			int c = C+dc[d];
			int dir = d;
			int score = 0;

			while(true) {
				
				// 벽, 블록, 웜홀, 블랙홀을 만날때까지 계속 직진
				while(r>=0 && c>=0 && r<N && c<N && map[r][c]==0 && !(r==R && c==C)) {
					r += dr[dir];
					c += dc[dir];
				}
				
				
				// 어떤것을 만났는지에 따라 판단해주기
				
				// 벽을 만났을 때
				// 점수 증가
				if(r<0 || c<0 || r>=N || c>=N) {
					dir = (dir+2)%4;
					r += dr[dir];
					c += dc[dir];
					score++;
					continue;
				}
				
				// 블랙홀 -1을 만났을 때
				if(map[r][c]==-1) {
					break;
				}
				
				// 시작위치에 도착했을 때
				if(r==R && c==C) {
					break;
				}
				
				
				// 블록 1~5를 만났을 때 block 배열에 저장된대로 방향 다시 바꿔주기
				// 점수 증가
				if(map[r][c]>=1 && map[r][c]<=5) {
					dir = block[map[r][c]][dir];
					r += dr[dir];
					c += dc[dir];
					score++;
					continue;
				}
				
				// 웜홀 6~10을 만났을 때 map에 저장된대로 위치 다시 바꿔주기
				if(Math.abs(map[r][c])>=6 && Math.abs(map[r][c])<=10) {
					int num = map[r][c];
					r = wormhole.get(num)[0];
					c = wormhole.get(num)[1];
					r += dr[dir];
					c += dc[dir];
					continue;
				}
				
			}
			
			max = Math.max(max, score);
			
		}
	}
}