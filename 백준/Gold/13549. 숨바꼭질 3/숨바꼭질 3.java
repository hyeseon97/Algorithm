import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;
import java.util.StringTokenizer;

public class Main {
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		int N = Integer.parseInt(st.nextToken());
		int K = Integer.parseInt(st.nextToken());
		
		
		Queue<int[]> q = new LinkedList<>();
		
		Set<Integer> set = new HashSet<>();
		q.add(new int[] {N, 0});
		while(!q.isEmpty()) {
			int[] temp = q.poll();
			int x = temp[0];
			int time = temp[1];
			set.add(x);
			
			// K에 도달했을 때 현재 시간 출력
			if(x==K) {
				System.out.println(time);
				return;
			}
			
			// -1, +1, *2 에 가지 않았을 때만 큐에 넣어주기
			if(!set.contains(x*2) && x<=K) {
				q.add(new int[] {x*2, time}); // 순간이동 (*2)은 0초가 걸리므로 time을 그대로 넣어주기
			}
			if(!set.contains(x-1) && x-1>=0) {
				q.add(new int[] {x-1, time+1});
			}
			if(!set.contains(x+1)) {
				q.add(new int[] {x+1, time+1});
			}
			
		}
		
	}
}