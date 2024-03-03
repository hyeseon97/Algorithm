import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
	
	// 편의점 개수
	static int N;
	// 상근이의 현재 위치 (처음은 집의 위치)
	static int R;
	static int C;
	// 편의점 좌표
	static List<int[]> store;
	// 페스티벌 좌표
	static int festivalR;
	static int festivalC;
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		int T = Integer.parseInt(br.readLine());
		for(int t = 1;t<=T;t++) {
			N = Integer.parseInt(br.readLine());
			StringTokenizer st = new StringTokenizer(br.readLine());
			R = Integer.parseInt(st.nextToken());
			C = Integer.parseInt(st.nextToken());
			store = new ArrayList<>();
			for(int i = 0;i<N;i++) {
				st = new StringTokenizer(br.readLine());
				int r = Integer.parseInt(st.nextToken());
				int c = Integer.parseInt(st.nextToken());
				store.add(new int[] {r, c});
			}
			st = new StringTokenizer(br.readLine());
			festivalR = Integer.parseInt(st.nextToken());
			festivalC = Integer.parseInt(st.nextToken());
			// 입력 끝
			

			// 갈 수 있는 편의점은 한번 가면 다시 갈 필요 없으므로 다익스트라로 방문체크
			Queue<int[]> q = new LinkedList<>();
			q.add(new int[] {R, C, 0});
			int[] visited =  new int[N];
			Arrays.fill(visited, Integer.MAX_VALUE);
			
			boolean happy = false;
			
			
			while(!q.isEmpty()) {
				
				int[] temp = q.poll();
				int r = temp[0];
				int c = temp[1];
				int len = temp[2];
				
				int dist = distance(r, festivalR, c, festivalC);
				int drink = (int)Math.ceil(dist/50.0);
				
				// 페스티벌에 갈 수 있을 때
				if(drink<=20) {
					happy = true;
					break;
				}
				
				for(int n = 0;n<N;n++) {
					dist = distance(r, store.get(n)[0], c, store.get(n)[1]);
					drink = (int)Math.ceil(dist/50.0);
					
					if(drink<=20 && len+1<visited[n]) {
						visited[n] = len+1;
						q.add(new int[] {store.get(n)[0], store.get(n)[1], len+1});
					}
				}
				
			}
			
			if(happy) {
				sb.append("happy\n");
			} else {
				sb.append("sad\n");
			}
			
		}
		
		System.out.println(sb);
	}

	// 두 좌표의 거리구하는 메서드
	static int distance(int startR, int endR, int startC, int endC) {
		return Math.abs(startR-endR) + Math.abs(startC-endC);
	}
	
}