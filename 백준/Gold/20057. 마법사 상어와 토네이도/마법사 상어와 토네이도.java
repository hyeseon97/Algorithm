import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
	
	// [i][j]
	// i는 진행방향 왼, 아래, 오, 위
	// j는 모래방향 5%&알파, 10%, 10%, 7%&2%, 7%&2%, 1%, 1%
	static int[][] dr = {{0, -1, 1, -1, 1, -1, 1},
				  		{1, 1, 1, 0, 0, -1, -1},
				  		{0, -1, 1, -1, 1, -1, 1},
				  		{-1, -1, -1, 0, 0, 1, 1}};
	static int[][] dc = {{-1, -1, -1, 0, 0, 1, 1},
			  	  		{0, -1, 1, -1, 1, -1, 1},
			  	  		{1, 1, 1, 0, 0, -1, -1},
			  	  		{0, -1, 1, -1, 1, -1, 1}};
	
	// 모래밭 크키
	static int N;
	
	// 현재 토네이도 위치와 방향
	static int R;
	static int C;
	static int D;
	
	// 모래밭
	static int[][] sand;
	
	// 토네이도 방문 체크
	static boolean[][] visited;
	
	// 모래밭 밖으로 나간 모래
	static int out;
	
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.parseInt(br.readLine());
		R = N/2;
		C = N/2;
		D = 0;
		sand = new int[N][N];
		visited = new boolean[N][N];
		visited[R][C] = true;
		for(int i = 0;i<N;i++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			for(int j = 0;j<N;j++) {
				sand[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		out = 0;
		
		// 토네이토를 계속 이동시키가다 0, 0에 도달하면 종료
		while(true) {
			
			// 토네이도 이동
			moveT();
			
			// 현재 위치에 있는 모래 이동
			moveS();
			
			// 이동방향 바꿔야하면 바꾸기
			changeD();

			// 모래밭 밖으로 나가면 종료
			if(R == 0 && C == 0) {
				break;
			}
			
		}
		
		System.out.println(out);
		
	}

	static void moveT() {
		// 현재 방향으로 한칸 이동
		R = R+dr[D][0];
		C = C+dc[D][0];
		visited[R][C] = true;
	}
	
	static void moveS() {
		// 현재 위치의 모래
		int total = sand[R][C];
		
		// 각 위치의 모래양 계산
		int sand5 = (int)(total * 0.05); 
		int sand10 = (int)(total * 0.1); 
		int sand7 = (int)(total * 0.07); 
		int sand2 = (int)(total * 0.02); 
		int sand1 = (int)(total * 0.01); 
		int alpha = total - (sand5 + (sand10 + sand7 + sand2 + sand1)*2);
		
		// 5% 위치
		int r = R+dr[D][0]*2;
		int c = C+dc[D][0]*2;
		isOut(r, c, sand5);
		
		// 10% 위치
		r = R+dr[D][1];
		c = C+dc[D][1];
		isOut(r, c, sand10);
		
		r = R+dr[D][2];
		c = C+dc[D][2];
		isOut(r, c, sand10);
		
		// 7% 위치
		r = R+dr[D][3];
		c = C+dc[D][3];
		isOut(r, c, sand7);
		
		r = R+dr[D][4];
		c = C+dc[D][4];
		isOut(r, c, sand7);

		// 2% 위치
		r = R+dr[D][3]*2;
		c = C+dc[D][3]*2;
		isOut(r, c, sand2);
		
		r = R+dr[D][4]*2;
		c = C+dc[D][4]*2;
		isOut(r, c, sand2);
		
		// 1% 위치
		r = R+dr[D][5];
		c = C+dc[D][5];
		isOut(r, c, sand1);
		
		r = R+dr[D][6];
		c = C+dc[D][6];
		isOut(r, c, sand1);
		
		
		// 알파위치
		r = R+dr[D][0];
		c = C+dc[D][0];
		isOut(r, c, alpha);
		
	}
	
	static void isOut(int r, int c, int val) {
		if(r<0 || c<0 || r>=N | c>=N) {
			out += val;
		} else {
			sand[r][c] += val;
		}
	}
	
	static void changeD() {
		// 현재 위치의 왼쪽이 가지 않은곳이면 회전
		int nextD = (D+1)%4;
		int nr = R+dr[nextD][0];
		int nc = C+dc[nextD][0];
		if(!visited[nr][nc]) {
			D = nextD;
		}
	}
}