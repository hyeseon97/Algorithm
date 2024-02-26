import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
	
	// 케이스크기
	static int N;
	// 바이러스 개수
	static int K;
	// 케이스 상태
	static int[][] arr;
	// 시간
	static int S;
	// 출력할 칸 좌표
	static int R;
	static int C;
	// 바이러스 번호 순으로 정렬해서 저장할 우선순위 큐
	static PriorityQueue<int[]> pq;
	
	static int[] dr = {-1, 1, 0, 0};
	static int[] dc = {0, 0, -1, 1};
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		K = Integer.parseInt(st.nextToken());
		arr = new int[N][N];
		pq = new PriorityQueue<int[]>(new Comparator<int[]>() {

			@Override
			public int compare(int[] o1, int[] o2) {
				return o1[2]-o2[2];
			}
		});
		for(int i = 0;i<N;i++) {
			st = new StringTokenizer(br.readLine());
			for(int j = 0;j<N;j++) {
				arr[i][j] = Integer.parseInt(st.nextToken());
				if(arr[i][j]!=0) {
					pq.add(new int[] {i, j, arr[i][j]});
				}
			}
		}
		st = new StringTokenizer(br.readLine());
		S = Integer.parseInt(st.nextToken());
		R = Integer.parseInt(st.nextToken())-1;
		C = Integer.parseInt(st.nextToken())-1;
		// 입력 완료
		
		// S시간동안 바이러스 전파
		for(int time = 0;time<S;time++) {
			
			// 현재 시간동안 퍼지는 바이러스 임시 저장
			Queue<int[]> q = new LinkedList<>();
			while(!pq.isEmpty()) {
				int[] temp = pq.poll();
				int r = temp[0];
				int c = temp[1];
				int num = temp[2];
				
				for(int d = 0;d<4;d++) {
					int nr = r+dr[d];
					int nc = c+dc[d];
					if(nr<0 || nc<0 || nr>=N || nc>=N || arr[nr][nc]!=0) continue;
					q.add(new int[] {nr, nc, num});
					arr[nr][nc] = num;
				}
			}
			
			// 퍼진 바이러스 케이스배열에 저장
			while(!q.isEmpty()) {
				int[] temp = q.poll();
				int r = temp[0];
				int c = temp[1];
				int num = temp[2];
				pq.add(new int[] {r, c, num});
			}
		}
		
		System.out.println(arr[R][C]);
		
	}
}