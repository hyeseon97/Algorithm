import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
	
	// 시 크기
	static int N;
	// 시 인구 수
	static int[][] people;
	// 총 인구수
	static int total;
	// 시작 기준점 정보
	// 좌표 x, y 경계 길이 d1, d2
	static int[] standard;
	// 최대인구, 최소인구 차이의 최소값
	static int res;
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.parseInt(br.readLine());
		people = new int[N][N];
		for(int i = 0;i<N;i++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			for(int j = 0;j<N;j++) {
				people[i][j] = Integer.parseInt(st.nextToken());
				total += people[i][j];
			}
		} // 입력 끝
		
		res = Integer.MAX_VALUE;
		
		// 모든 경우의 수에 대해서 계산해보기
		for(int r = 0;r<=N-3;r++) {
			for(int c = 1;c<=N-2;c++) {
				for(int d1 = 1;d1<=N-2;d1++) {
					for(int d2 = 1;d2<=N-2;d2++) {
						standard = new int[] {r, c, d1, d2};
						// 경계의 길이만큼 경계선을 그엇을 때 배열 밖으로 나가지 않는 선에서 계산하기
						if(r+d1<N && c-d1>=0 && r+d2<N && c+d2<N && r+d1+d2<N && c-d1+d2<N) {
							cal();
						}
						
					}
				}
			}
		}
		
		System.out.println(res);
		
	}
	
	static void cal() {
		
		// 구역별 인원수
		int[] area = new int[6];
		
		int x = standard[0];
		int y = standard[1];
		int d1 = standard[2];
		int d2 = standard[3];
		
		// 구역별 경계선에 따라 인원수 계산하기
		
		for(int i = 0;i<N;i++) {
			for(int j = 0;j<N;j++) {
				if(i<x+d1 && j<=y && (i+j<x+y)) {
					area[1] += people[i][j];
				}
			}
		}
		for(int i = 0;i<N;i++) {
			for(int j = 0;j<N;j++) {
				if(i<=x+d2 && j>y && (i-j<x-y)) {
					area[2] += people[i][j];
				}
			}
		}
		for(int i = 0;i<N;i++) {
			for(int j = 0;j<N;j++) {
				if(i>=x+d1 && j<y-d1+d2 && (i-j>x+d1-y+d1)) {
					area[3] += people[i][j];
				}
			}
		}
		for(int i = 0;i<N;i++) {
			for(int j = 0;j<N;j++) {
				if(i>x+d2 && j>=y-d1+d2 && (i+j>x+d2+y+d2)) {
					area[4] += people[i][j];
				}
			}
		}
		
		area[5] = total - (area[1]+area[2]+area[3]+area[4]);
		
		// 구역별 최대인원수와 최소인원수 구하고 차이를 결과값과 비교해 최소값 구하기
		int max = 0;
		int min = Integer.MAX_VALUE;
		
		for(int i = 1;i<=5;i++) {
			max = Math.max(max, area[i]);
			min = Math.min(min, area[i]);
		}
		
		res = Math.min(res, (max-min));
	}
}