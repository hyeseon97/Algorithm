import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.StringTokenizer;

public class Solution {
	
	// 구슬 개수
	static int N;
	// 맵 크기
	static int W;
	static int H;
	// 맵
	static int[][] board;
	// 벽돌 총 개수
	static int total;
	// 남은 벽돌 개수 최소값
	static int min;
	
	static int[] dr = {-1, 1, 0, 0};
	static int[] dc = {0, 0, -1, 1};
	
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		int T = Integer.parseInt(br.readLine());
		for(int t = 1;t<=T;t++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			N = Integer.parseInt(st.nextToken());
			W = Integer.parseInt(st.nextToken());
			H = Integer.parseInt(st.nextToken());
			board = new int[H][W];
			total = 0;
			min = Integer.MAX_VALUE;
			for(int i = 0;i<H;i++) {
				st = new StringTokenizer(br.readLine());
				for(int j = 0;j<W;j++) {
					board[i][j] = Integer.parseInt(st.nextToken());
					if(board[i][j]>0) {
						total++;
					}
				}
			} // 입력 완료
			
			// 초기 구슬 갯수, 벽돌 갯수, 벽돌현황으로 시작
			game(N, total, board);
			
			sb.append("#" + t + " " + min + "\n");
			
		}
		System.out.println(sb);
	}
	
	static void game(int n, int brick, int[][] arr) {
		
		// 구슬을 다 썼거나 남은 벽돌이 없을 때 최소값 갱신하고 종료하기
		if(n == 0 || brick == 0) {
			min = Math.min(min, brick);
			return;
		}
		
		// 모든 열을 탐색하면서 어디에 구슬을 떨어뜨릴 수 있나 판별하기
		for(int w = 0;w<W;w++) {
			int h = 0;
			
			// 벽돌위 빈 공간 뛰어넘기
			while(h<H && arr[h][w]==0) {
				h++;
			}
			
			// 벽돌이 없는 열일 경우 건너뛰기
			if(h == H) continue;
			
			// 구슬을 떨어뜨렸는지 판별할 변수
			boolean exist = false;
			// 구슬을 떨어뜨려서 벽돌을 깰 위치 저장
			int hit = h;
			// 남은 구슬을 다 떨어뜨렸을 때 2이상인 벽돌이 있는 경우 찾기
			// -> 이래야 다른 벽돌들도 연쇄적으로 부술수있어서 이득
			for(int ball = 0;ball<n;ball++) {
				if(h<H && arr[h][w]>1) {
					exist = true;
				}
				h++;
			}
			
			// 구슬떨어뜨리자
			if(exist) {
				// 배열 복사
				int[][] nextArr = new int[H][W];
				for(int i = 0;i<H;i++) {
					nextArr[i] = arr[i].clone();
				}
				// 남은 벽돌 개수 복사
				int nextBrick = brick;
				
				// 벽돌번호가 2 이상인 벽돌을 넣을 큐
				Queue<int[]> q = new LinkedList<>();
				q.add(new int[] {hit, w, nextArr[hit][w]});
				nextArr[hit][w] = 0;
				nextBrick--;
				
				while(!q.isEmpty()) {
					int[] temp = q.poll();
					int r = temp[0];
					int c = temp[1];
					int num = temp[2];
					
					// 4방향을 현재 벽돌 번호만큼 나아가면서 0으로 만들기
					for(int d = 0;d<4;d++) {
						for(int l = 1;l<num;l++) {
							int nr = r+dr[d]*l;
							int nc = c+dc[d]*l;
							if(nr<0 || nc<0 || nr>=H || nc>=W || nextArr[nr][nc]==0) continue;
							// 2이상이면 큐에 저장
							if(nextArr[nr][nc]>1) {
								q.add(new int[] {nr, nc, nextArr[nr][nc]});
							}
							// 0으로 하고 남은 벽돌 마이너스
							nextArr[nr][nc] = 0;
							nextBrick--;
						}
					}
				}
				
				// 없어진 벽돌 제외하고 나머지 벽돌 내리기
				int[][] resArr = new int[H][W];
				for(int i = 0;i<W;i++) {
					int k = H-1;
					for(int j = H-1;j>=0;j--) {
						if(nextArr[j][i]!=0) {
							resArr[k--][i] = nextArr[j][i];
						}
					}
				}
				
				// 다음 구슬 쓰러 가기
				game(n-1, nextBrick, resArr);
				
			} else {
				// 깰 벽돌이 없을 땐 남은 구슬만큼 남은 벽돌에서 마이너스 해주고
				// 구슬이 0인채로 넘어가기
				int nextBrick = brick-n<0?0:brick-n;
				game(0, nextBrick, arr);
			}
			
		}
	}
	
}