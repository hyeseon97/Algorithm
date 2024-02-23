import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Solution {
	
	// 맵의 크기
	static int N;
	// 경사로 길이
	static int X;
	// 맵
	static int[][] map;
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		int T = Integer.parseInt(br.readLine());
		for(int t = 1;t<=T;t++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			N = Integer.parseInt(st.nextToken());
			X = Integer.parseInt(st.nextToken());
			map = new int[N][N];
			for(int i = 0;i<N;i++) {
				st = new StringTokenizer(br.readLine());
				for(int j = 0;j<N;j++) {
					map[i][j] = Integer.parseInt(st.nextToken());
				}
			}
			
			// 활주로를 놓을 수 있는 경우의 수
			int result = 0;
			
			// 가로행 조사
			L:for(int i = 0;i<N;i++) {
				// (지형의 높이, 지형의 길이) 를 저장
				// 3 3 2 2 1 3 이면
				// (3,2) (2, 2) (1, 1) (3, 1)
				List<int[]> list = new ArrayList<>();
				int num = map[i][0];
				int count = 1;
				for(int j = 1;j<N;j++) {
					if(map[i][j]!=num) {
						list.add(new int[] {num, count});
						num = map[i][j];
						count = 1;
					} else {
						count++;
					}
				}
				list.add(new int[] {num, count});
				// 저장 끝
				
				// 활주로를 놓을 수 있는지 판단
				for(int idx = 1;idx<list.size();idx++) {
					// 높이 차이가 2이상이면 경사로를 놓을 수 없으므로 종료
					if(Math.abs(list.get(idx-1)[0]-list.get(idx)[0])>=2) continue L;
					
					// 이젠 모두 높이가 1차이
					
					// 이전 높이가 더 낮을 때, 이전 높이를 가진 지형의 길이가 경사로 길이보다 같거나 길면 놓을 수 있음
					if(list.get(idx-1)[0]<list.get(idx)[0] && list.get(idx-1)[1]>=X) {
						// 경사로를 놓은 만큼 지형 길이를 경사로 길이만큼 빼주기
						list.get(idx-1)[1] -= X;
					} else if(list.get(idx-1)[0]>list.get(idx)[0] && list.get(idx)[1]>=X) {
						// 다음 높이가 더 낮을 때, 다음 높이를 가진 지형의 길이가 경사로 길이보다 같거나 길면 놓을 수 있음
						// 경사로를 놓은 만큼 지형 길이를 경사로 길이만큼 빼주기
						list.get(idx)[1] -= X;
					} else {
						// 경사로를 놓을 수 없을 때 종료
						continue L;
					}
				}
				// 마지막 높이의 지형까지 왔으면 활주로를 놓을 수 있으므로 경우의 수 증가
				result++;
			}
			
			// 세로열 조사
			L:for(int i = 0;i<N;i++) {
				List<int[]> list = new ArrayList<>();
				int num = map[0][i];
				int count = 1;
				for(int j = 1;j<N;j++) {
					if(map[j][i]!=num) {
						list.add(new int[] {num, count});
						num = map[j][i];
						count = 1;
					} else {
						count++;
					}
				}
				list.add(new int[] {num, count});
				
				for(int idx = 1;idx<list.size();idx++) {
					if(Math.abs(list.get(idx-1)[0]-list.get(idx)[0])>=2) continue L;
					
					if(list.get(idx-1)[0]<list.get(idx)[0] && list.get(idx-1)[1]>=X) {
						list.get(idx-1)[1] -= X;
					} else if(list.get(idx-1)[0]>list.get(idx)[0] && list.get(idx)[1]>=X) {
						list.get(idx)[1] -= X;
					} else {
						continue L;
					}
				}
				// 마지막 높이의 지형까지 왔으면 활주로를 놓을 수 있으므로 경우의 수 증가
				result++;
			}
			
			sb.append("#" + t + " " + result + "\n");
		}
		System.out.println(sb);
	}
}