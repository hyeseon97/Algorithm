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
		int redR = 0;
		int redC = 0;
		int blueR = 0;
		int blueC = 0;
		int holeR = 0;
		int holeC = 0;
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
				} else if (board[i][j] == 'O') {
					holeR = i;
					holeC = j;
				}
			}
		}

		Queue<int[]> q = new LinkedList<>();
		q.add(new int[] { redR, redC, blueR, blueC, 1 });

		while(!q.isEmpty()) {
			int[] temp = q.poll();
			redR = temp[0];
			redC = temp[1];
			blueR = temp[2];
			blueC = temp[3];
			int count = temp[4];
			if(count == 11) continue;
			
			Next : for(int d = 0;d<4;d++) {
				int blueNR = blueR;
				int blueNC = blueC;
				while(board[blueNR+dr[d]][blueNC+dc[d]]!='#') {
					if(board[blueNR+dr[d]][blueNC+dc[d]]=='O') {
						continue Next;
					} else {
						blueNR += dr[d];
						blueNC += dc[d];
					}
				}
				int redNR = redR;
				int redNC = redC;
				while(board[redNR+dr[d]][redNC+dc[d]]!='#') {
					if(board[redNR+dr[d]][redNC+dc[d]]=='O') {
						System.out.println(count);
						return;
					} else {
						redNR += dr[d];
						redNC += dc[d];
					}
				}
				
				String who = "red";
				if(redNR==blueNR && redNC==blueNC) {
					if(d==0) {
						if(blueR>redR) {
							who = "blue";
						}
					} else if(d==1) {
						if(blueR<redR) {
							who = "blue";
						}
					} else if(d==2) {
						if(blueC>redC) {
							who = "blue";
						}
					} else {
						if(blueC<redC) {
							who = "blue";
						}
					}
					
					if(who.equals("blue")) {
						blueNR -= dr[d];
						blueNC -= dc[d];
					} else {
						redNC -= dc[d];
						redNR -= dr[d];
					}
				}
				
				q.add(new int[] {redNR, redNC, blueNR, blueNC, count+1});
//				System.out.println("num = " + count);
//				System.out.println("d = " + d);
//				System.out.println(redNR +  " " + redNC + "    " + blueNR + " " + blueNC);
			}
			
			
		}

		System.out.println(-1);
	}
}