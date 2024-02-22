import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Solution {
	
	// 벌통 크기
	static int N;
	// 선택할 수 있는 벌통 개수
	static int M;
	// 채취할 수 있는 최대 꿀 양
	static int C;
	// 벌통
	static int[][] honey;
	// 해당 벌통을 시작으로 할 때 채취할 수 있는 양을 저장
	static int[][] temp;
	
	static int res;
	static int r;
	static int c;
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		int T = Integer.parseInt(br.readLine());
		for(int t = 1;t<=T;t++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			N = Integer.parseInt(st.nextToken());
			M = Integer.parseInt(st.nextToken());
			C = Integer.parseInt(st.nextToken());
			honey = new int[N][N];
			for(int i = 0;i<N;i++) {
				st = new StringTokenizer(br.readLine());
				for(int j = 0;j<N;j++) {
					honey[i][j] = Integer.parseInt(st.nextToken());
				}
			}
			temp = new int[N][N];
			for(int i = 0;i<N;i++) {
				for(int j = 0;j<=N-M;j++) {
					sum = 0;
					squ = 0;
					max = 0;
					area(i, j, 0);
					temp[i][j] = max;
				}
			}
			
			res = 0;
			// 두개의 벌통을 고르는 모든 경우의 수
			for(int i = 0;i<N;i++) {
				for(int j = 0;j<=N-M;j++) {
					r = i;
					c = j+M;

					while(true) {
						// 열이 배열 벗어나면 다음행으로
						if(c>N-M) {
							r++;
							c = 0;
						}
						
						// 행이 끝까지 가면 종료
						if(r==N) {
							break;
						}
						
						// 두개 벌통 합친 값 최대값 갱신
						res = Math.max(res, temp[i][j]+temp[r][c]);
						c++;
					}
					
					
				}
			}
			
			sb.append("#" + t + " " + res + "\n");
			
		}
		System.out.println(sb);
	}
	
	// 수익 최대값
	static int max;
	// 현재 벌통의 꿀 양 합
	static int sum;
	// 현재 벌통의 수익
	static int squ;
	// 모든 벌통 경우마다 수익 최대값 구하기
	static void area(int r, int c, int m) {
		
		// 모든 벌통을 다 고려한 후
		if(m == M) {
			// 꿀 양 합이 C 이하일 때 가능
			if(sum<=C) {
				// 수익 갱신
				max = Math.max(max, squ);
			}
			return;
		}
		
		sum += honey[r][c];
		squ += Math.pow(honey[r][c], 2);
		area(r, c+1, m+1);
		
		sum -= honey[r][c];
		squ -= Math.pow(honey[r][c], 2);
		area(r, c+1, m+1);
		
	}

}