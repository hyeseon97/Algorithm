import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.StringTokenizer;

public class Solution {
	
	static int M;
	static int A;
	static List<int[]>[][] map;
	static int[] moveA;
	static int[] moveB;
	static int[] power;
	
	static int total;
	
	static int[] dr = {0, -1, 0, 1, 0};
	static int[] dc = {0, 0, 1, 0, -1};
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		int T = Integer.parseInt(br.readLine());
		for(int t = 1;t<=T;t++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			M = Integer.parseInt(st.nextToken());
			A = Integer.parseInt(st.nextToken());
			map = new ArrayList[10][10];
			for(int i = 0;i<10;i++) {
				for(int j = 0;j<10;j++) {
					map[i][j] = new ArrayList<>();
				}
			}
			
			moveA = new int[M];
			moveB = new int[M];
			
			st = new StringTokenizer(br.readLine());
			for(int i = 0;i<M;i++) {
				moveA[i] = Integer.parseInt(st.nextToken());
			}
			st = new StringTokenizer(br.readLine());
			for(int i = 0;i<M;i++) {
				moveB[i] = Integer.parseInt(st.nextToken());
			}
			
			// 충전기 범위에 파워 체크하기
			power = new int[A];
			for(int i = 0;i<A;i++) {
				st = new StringTokenizer(br.readLine());
				int X = Integer.parseInt(st.nextToken());
				int Y = Integer.parseInt(st.nextToken());
				int C = Integer.parseInt(st.nextToken());
				int P = Integer.parseInt(st.nextToken());
				power[i] = P;
				
				check(X, Y, C, P, i);
			}
			
			
			for(int i = 0;i<10;i++) {
				for(int j = 0;j<10;j++) {
					Collections.sort(map[i][j], new Comparator<int[]>() {
						@Override
						public int compare(int[] o1, int[] o2) {
							return o2[1]-o1[1];
						}
					});
				}
			}
			
			
			
			int AR = 0;
			int AC = 0;
			int BR = 9;
			int BC = 9;
			total = 0;
			int time = 0 ;
			// 이동하기
			while(true) {
				
				if(map[AR][AC].size()>0 && map[BR][BC].size()>0) {
					// A와 B의 제일 큰 충전기가 같을 때
					if(map[AR][AC].get(0)[0]==map[BR][BC].get(0)[0]) {
						// A에 충전기가 두개이상이거나 B에 충전기가 두개이상일때 파워 최대값 갱신할 변수
						// 둘 다 없으면 0이겠지 그럼 A랑 B랑 같은 충전기만 충전할 수 있는것
						int max = 0;
						if(map[AR][AC].size()>1) {
							max = Math.max(max, map[AR][AC].get(1)[1]);
						}
						if(map[BR][BC].size()>1) {
							max = Math.max(max, map[BR][BC].get(1)[1]);
						}
						total += map[AR][AC].get(0)[1];
						total += max;
					} else {
						// 다를때니까 둘 다 제일 큰 걸로 더하기
						total += map[AR][AC].get(0)[1];
						total += map[BR][BC].get(0)[1];
					}
				} else if(map[AR][AC].size()>0) {
					total += map[AR][AC].get(0)[1];
				} else if(map[BR][BC].size()>0) {
					total += map[BR][BC].get(0)[1];
				}
				
				// 이동시간 되었으면 종료
				if(time == M) {
					break;
				}
				
				// 다음 칸으로 이동
				AR += dr[moveA[time]];
				AC += dc[moveA[time]];
				BR += dr[moveB[time]];
				BC += dc[moveB[time]];
				time++;
			}
			
			sb.append("#" + t + " " + total + "\n");
			
		}
		System.out.println(sb);
	}
	
	// BC의 충전기마다 범위에 따라 지도에 체크해놓기
	static void check(int X, int Y, int C, int P, int num) {
		
		Queue<int[]> q = new LinkedList<>();
		q.add(new int[] {Y-1, X-1, 0});
		boolean[][] visited = new boolean[10][10];
		visited[Y-1][X-1] = true;
		
		while(!q.isEmpty()) {
			int[] temp = q.poll();
			int r = temp[0];
			int c = temp[1];
			int area = temp[2];
			
			if(area > C) {
				break;
			}
			
			map[r][c].add(new int[] {num, P});
			
			for(int d = 1;d<=4;d++) {
				int nr = r+dr[d];
				int nc = c+dc[d];
				if(nr<0 || nc<0 || nr>=10 || nc>=10 || visited[nr][nc]) continue;
				
				q.add(new int[] {nr, nc, area+1});
				visited[nr][nc] = true;
			}
			
		}
		
	}
}