import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Solution {
	
	static int N;
	static int M;
	static int K;
	static List<Integer>[][] cell;
	static List<int[]> groups;
	
	static int[] dr = {-1, 0, 1, 0};
	static int[] dc = {0, -1, 0, 1};
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		int T = Integer.parseInt(br.readLine());
		for(int t = 1;t<=T;t++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			N = Integer.parseInt(st.nextToken());
			M = Integer.parseInt(st.nextToken());
			K = Integer.parseInt(st.nextToken());
			groups = new ArrayList<>();
			groups.add(new int[] {0, 0, 0, 0}); // 0번은 건너뛰기 위해 아무거나 넣기
			for(int i = 1;i<=K;i++) { // 군집 번호는 1번부터
				st = new StringTokenizer(br.readLine());
				int r = Integer.parseInt(st.nextToken());
				int c = Integer.parseInt(st.nextToken());
				int count = Integer.parseInt(st.nextToken());
				int dir = Integer.parseInt(st.nextToken());
				switch(dir) {
					case 1: dir = 0; break;
					case 2: dir = 2; break;
					case 3: dir = 1; break;
					case 4: dir = 3; break;
				}
				groups.add(new int[] {r, c, count, dir});
			}
			
			for(int time = 1;time<=M;time++) {
				cell = new ArrayList[N][N];
				
				// 1. 미생물들 방향대로 이동시키기
				// 약품이 있는 곳으로 갔다면 절반으로 감소시키고 방향바꾸기
				// 약품이 없는 셀에 도착했다면 cell에 군집 번호를 저장
				for(int num = 1;num<=K;num++) {
					int r = groups.get(num)[0];
					int c = groups.get(num)[1];
					int count = groups.get(num)[2];
					int dir = groups.get(num)[3];
					
					// 개수가 0인 군집은 건너뛰기
					if(count == 0) continue;
					
					r += dr[dir];
					c += dc[dir];
					
					if(r==0 || c==0 || r==N-1 || c==N-1) {
						count /= 2;
						dir = (dir+2)%4;
					} else {
						if(cell[r][c]==null) {
							cell[r][c] = new ArrayList<>();
						}
						cell[r][c].add(num);
					}
					groups.set(num, new int[] {r, c, count, dir});
				}
				
				// 2. 약품이 없는 셀들을 돌면서 군집번호가 2개 이상이면 갯수가 가장 큰걸로 방향 정하고 합치기
				for(int r = 1;r<N-1;r++) {
					for(int c = 1;c<N-1;c++) {
						if(cell[r][c]!=null && cell[r][c].size()>=2) {
							int num = 0; // 개수가 가장 큰 군집의 번호
							int max = 0; // 개수가 가장 큰 군집의 개수
							int count = 0; // 군집들의 미생물 개수 합
							for(int i = 0;i<cell[r][c].size();i++) {
								int n = cell[r][c].get(i);
								count += groups.get(n)[2]; // 미생물 합치기
								if(max<groups.get(n)[2]) {
									max = groups.get(n)[2];
									num = n;
								}
							}
							
							// 다시 포문을 돌면서 num번인 군집에 미생물수 합계count를 다시 저장하고
							// 나머지 군집은 개수를 0으로 해서 없애기
							for(int i = 0;i<cell[r][c].size();i++) {
								int n = cell[r][c].get(i);
								if(n==num) {
									groups.get(n)[2] = count;
								} else {
									groups.get(n)[2] = 0;
								}
							}
						}
					}
				}
			} // 시간 종료
			
			int count = 0;
			for(int i = 1;i<=K;i++) {
//				System.out.println(groups.get(i)[2]);
				count += groups.get(i)[2];
			}
			
			sb.append("#" + t + " " + count + "\n");
		}
		System.out.println(sb);
	}
}