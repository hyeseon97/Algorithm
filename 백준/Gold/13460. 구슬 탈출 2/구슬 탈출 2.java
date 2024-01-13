import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {

	static int[] dr = { -1, 1, 0, 0 };
	static int[] dc = { 0, 0, -1, 1 };

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		int N = Integer.parseInt(st.nextToken());
		int M = Integer.parseInt(st.nextToken());

		char[][] board = new char[N][M];
		
		// 빨간구슬과 파란구슬 위치 저장
		int redR = 0;
		int redC = 0;
		int blueR = 0;
		int blueC = 0;
		
		for (int i = 0; i < N; i++) {
			String str = br.readLine();
			for (int j = 0; j < M; j++) {
				board[i][j] = str.charAt(j);
				if (board[i][j] == 'R') {
					redR = i;
					redC = j;
					board[i][j] = '.';
				} else if (board[i][j] == 'B') {
					blueR = i;
					blueC = j;
					board[i][j] = '.';
				}
			}
		}

		Queue<int[]> q = new LinkedList<>();
		// 빨간구슬 위치와 파란구슬 위치, 이동횟수 저장
		q.add(new int[] { redR, redC, blueR, blueC, 1 });

		while(!q.isEmpty()) {
			int[] temp = q.poll();
			redR = temp[0];
			redC = temp[1];
			blueR = temp[2];
			blueC = temp[3];
			int count = temp[4];
			
			// 이동횟수가 10이 넘었으면 건너뛰기
			if(count == 11) continue;
			
			// 4방향으로 기울이기
			Next : for(int d = 0;d<4;d++) {
				
				// 파란구슬이 구멍에 빠지면 실패이므로 파란구슬 먼저 이동시켜보기
				
				int blueNR = blueR;
				int blueNC = blueC;
				// 현재 방향으로 장애물이나 벽이 나오기 전까지 이동
				while(board[blueNR+dr[d]][blueNC+dc[d]]!='#') {
					// 파란구슬이 구멍을 만나면 건너뛰기
					if(board[blueNR+dr[d]][blueNC+dc[d]]=='O') {
						continue Next;
					} else { // 계속이동
						blueNR += dr[d];
						blueNC += dc[d];
					}
				}
				int redNR = redR;
				int redNC = redC;
				// 현재 방향으로 장애물이나 벽이 나오기 전까지 이동
				while(board[redNR+dr[d]][redNC+dc[d]]!='#') {
					// 빨간구슬이 구멍을 만나면 성공이므로 이동횟수 출력하고 종료하기
					if(board[redNR+dr[d]][redNC+dc[d]]=='O') {
						System.out.println(count);
						return;
					} else { // 계속이동
						redNR += dr[d];
						redNC += dc[d];
					}
				}
				// 구슬 이동 끝
				
				
				// 이동 후 겹쳐있으면 안되므로 누가 한칸 물러서야하는지 따져보기
				// 우선 빨간구슬로 초기화
				String who = "red";
				
				// 겹쳐있을 때
				if(redNR==blueNR && redNC==blueNC) {
					// 방향에 따라서 따져보기
					// 위로 기울였을 때
					if(d==0) {
						// 파란구슬이 빨간구슬보다 아래에 있었으면 파란구슬을 한칸 후진
						if(blueR>redR) {
							who = "blue";
						}
					} else if(d==1) { // 아래로 기울였을 때
						// 파란구슬이 빨간구슬보다 위에 있었으면 파란구슬을 한칸 후진
						if(blueR<redR) {
							who = "blue";
						}
					} else if(d==2) { // 왼쪽으로 기울였을 때
						// 파란구슬이 빨간구슬보다 오른쪽에 있었으면 파란구슬을 한칸 후진
						if(blueC>redC) {
							who = "blue";
						}
					} else { // 오른쪽으로 기울였을 때
						// 파란구슬이 빨간구슬보다 왼쪽에 있었으면 파란구슬을 한칸 후진
						if(blueC<redC) {
							who = "blue";
						}
					}
					
					// 후진해야하는게 파란구슬일때
					if(who.equals("blue")) {
						// 방향에 맞춰 한칸 뒤로
						blueNR -= dr[d];
						blueNC -= dc[d];
					} else { // 후진해야하는게 빨간구슬일때
						// 방향에 맞춰 한칸 뒤로
						redNC -= dc[d];
						redNR -= dr[d];
					}
				}
				
				// 이동 후 위치 저장하고 이동횟수(count)도 하나 증가시켜 저장
				q.add(new int[] {redNR, redNC, blueNR, blueNC, count+1});
			}
			
			
		}

		// 이동횟수가 10이 넘었는데도 성공하지 못했으면 실패로 -1 출력
		System.out.println(-1);
	}
}