import java.util.*;
import java.io.*;

public class Main {
	
	// 큐브 돌릴 횟수
	static int N;
	
	// 큐브
	static char[][][] cube;
	
	
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		
		int T = Integer.parseInt(br.readLine());
		
		// 테스트케이브만큼 반복
		for(int t = 1;t<=T;t++) {
			N = Integer.parseInt(br.readLine());
			initCube();
			
			// 돌릴 횟수만큼 반복
			StringTokenizer st = new StringTokenizer(br.readLine());
			for(int n = 0;n<N;n++) {
				String command = st.nextToken();
				char c = command.charAt(0);
				char dir = command.charAt(1);
				
				// 돌릴면과 방향에 따라
				if(c == 'U') {
					if(dir == '+') {
						rotationPlus(0);
						char[] temp = cube[1][0].clone();
						cube[1][0] = cube[4][0].clone();
						cube[4][0] = cube[3][0].clone();
						cube[3][0] = cube[2][0].clone();
						cube[2][0] = temp.clone();
					} else {
						rotationMinus(0);
						char[] temp = cube[1][0].clone();
						cube[1][0] = cube[2][0].clone();
						cube[2][0] = cube[3][0].clone();
						cube[3][0] = cube[4][0].clone();
						cube[4][0] = temp.clone();
					}
				} else if(c == 'D') {
					if(dir == '+') {
						rotationPlus(5);
						char[] temp = cube[1][2].clone();
						cube[1][2] = cube[2][2].clone();
						cube[2][2] = cube[3][2].clone();
						cube[3][2] = cube[4][2].clone();
						cube[4][2] = temp.clone();
					} else {
						rotationMinus(5);
						char[] temp = cube[1][2].clone();
						cube[1][2] = cube[4][2].clone();
						cube[4][2] = cube[3][2].clone();
						cube[3][2] = cube[2][2].clone();
						cube[2][2] = temp.clone();
					}
				} else if(c == 'B') {
					if(dir == '+') {
						rotationPlus(1);
						
						char[] temp = cube[0][0].clone();
						move(0, 0, 0, 0, 1, 2, 0, 1, 2, 0);
						move(2, 0, 1, 2, 0, 5, 2, 0, 2, -1);
						move(5, 2, 0, 2, -1, 4, 2, -1, 0, 0);
						cube[4][2][0] = temp[0];
						cube[4][1][0] = temp[1];
						cube[4][0][0] = temp[2];
						
					} else {
						rotationMinus(1);
						
						char[] temp = cube[0][0].clone();
						move(0, 0, 0, 0, 1, 4, 2, -1, 0, 0);
						move(4, 2, -1, 0, 0, 5, 2, 0, 2, -1);
						move(5, 2, 0, 2, -1, 2, 0, 1, 2, 0);
						cube[2][0][2] = temp[0];
						cube[2][1][2] = temp[1];
						cube[2][2][2] = temp[2];
						
					}
				} else if(c == 'R') {
					if(dir == '+') {
						rotationPlus(2);
						
						char[] temp = {cube[0][0][2], cube[0][1][2], cube[0][2][2]};
						move(0, 0, 1, 2, 0, 3, 0, 1, 2, 0);
						move(3, 0, 1, 2, 0, 5, 0, 1, 2, 0);
						move(5, 0, 1, 2, 0, 1, 2, -1, 0, 0);
						cube[1][2][0] = temp[0];
						cube[1][1][0] = temp[1];
						cube[1][0][0] = temp[2];
						
					} else {
						rotationMinus(2);
						
						char[] temp = {cube[0][0][2], cube[0][1][2], cube[0][2][2]};
						move(0, 0, 1, 2, 0, 1, 2, -1, 0, 0);
						move(1, 2, -1, 0, 0, 5, 0, 1, 2, 0);
						move(5, 0, 1, 2, 0, 3, 0, 1, 2, 0);
						cube[3][0][2] = temp[0];
						cube[3][1][2] = temp[1];
						cube[3][2][2] = temp[2];
						
					}
				} else if(c == 'F') {
					if(dir == '+') {
						rotationPlus(3);
						
						char[] temp = cube[0][2].clone();
						move(0, 2, 0, 0, 1, 4, 2, -1, 2, 0);
						move(4, 2, -1, 2, 0, 5, 0, 0, 2, -1);
						move(5, 0, 0, 2, -1, 2, 0, 1, 0, 0);
						cube[2][0][0] = temp[0];
						cube[2][1][0] = temp[1];
						cube[2][2][0] = temp[2];
						
					} else {
						rotationMinus(3);
						
						char[] temp = cube[0][2].clone();
						move(0, 2, 0, 0, 1, 2, 0, 1, 0, 0);
						move(2, 0, 1, 0, 0, 5, 0, 0, 2, -1);
						move(5, 0, 0, 2, -1, 4, 2, -1, 2, 0);
						cube[4][2][2] = temp[0];
						cube[4][1][2] = temp[1];
						cube[4][0][2] = temp[2];
						
					}
				} else if(c == 'L') {
					if(dir == '+') {
						rotationPlus(4);
						
						char[] temp = {cube[0][0][0], cube[0][1][0], cube[0][2][0]};
						move(0, 0, 1, 0, 0, 1, 2, -1, 2, 0);
						move(1, 2, -1, 2, 0, 5, 0, 1, 0, 0);
						move(5, 0, 1, 0, 0, 3, 0, 1, 0, 0);
						cube[3][0][0] = temp[0];
						cube[3][1][0] = temp[1];
						cube[3][2][0] = temp[2];
						
					} else {
						rotationMinus(4);
						
						char[] temp = {cube[0][0][0], cube[0][1][0], cube[0][2][0]};
						move(0, 0, 1, 0, 0, 3, 0, 1, 0, 0);
						move(3, 0, 1, 0, 0, 5, 0, 1, 0, 0);
						move(5, 0, 1, 0, 0, 1, 2, -1, 2, 0);
						cube[1][2][2] = temp[0];
						cube[1][1][2] = temp[1];
						cube[1][0][2] = temp[2];
						
					}
				}
				
			} // 큐브 돌리기 끝
			
			for(int i = 0;i<3;i++) {
				for(int j = 0;j<3;j++) {
					sb.append(cube[0][i][j]);
				}
				sb.append("\n");
			}
			
		} // 테스트케이스 끝
		
		System.out.println(sb.toString());
		
	}
	
	static void initCube() {
		
		cube = new char[][][]{{{'w', 'w', 'w'}, {'w', 'w', 'w'}, {'w', 'w', 'w'}},
			{{'o', 'o', 'o'}, {'o', 'o', 'o'}, {'o', 'o', 'o'}},
			{{'b', 'b', 'b'}, {'b', 'b', 'b'}, {'b', 'b', 'b'}},
			{{'r', 'r', 'r'}, {'r', 'r', 'r'}, {'r', 'r', 'r'}},
			{{'g', 'g', 'g'}, {'g', 'g', 'g'}, {'g', 'g', 'g'}},
			{{'y', 'y', 'y'}, {'y', 'y', 'y'}, {'y', 'y', 'y'}}
		};
		
	}
	
	// 면을 시계방향 회전
	static void rotationPlus(int c) {
		
		for(int i = 0;i<2;i++) {
			char temp = cube[c][0][i];
			cube[c][0][i] = cube[c][2-i][0];
			cube[c][2-i][0] = cube[c][2][2-i];
			cube[c][2][2-i] = cube[c][i][2];
			cube[c][i][2] = temp;
		}
		
	}
	
	// 면을 반시계방향 회전
	static void rotationMinus(int c) {
		
		for(int i = 0;i<2;i++) {
			char temp = cube[c][0][i];
			cube[c][0][i] = cube[c][i][2];
			cube[c][i][2] = cube[c][2][2-i];
			cube[c][2][2-i] = cube[c][2-i][0];
			cube[c][2-i][0] = temp;
		}
		
	}
	
	static void move(int a, int b, int plusB, int c, int plusC, int x, int y, int plusY, int z, int plusZ) {
		
		for(int s = 0;s<3;s++) {
			cube[a][b][c] = cube[x][y][z];
			b += plusB;
			c += plusC;
			y += plusY;
			z += plusZ;
		}
		
	}
}