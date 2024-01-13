import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		int N = Integer.parseInt(st.nextToken());
		int M = Integer.parseInt(st.nextToken());
		
		ArrayList<ArrayList<Integer>> list = new ArrayList<>();
		for(int i = 0;i<=N;i++) {
			list.add(new ArrayList<>());
		}
		
		for(int i = 0;i<M;i++) {
			st = new StringTokenizer(br.readLine());
			int A = Integer.parseInt(st.nextToken());
			int B = Integer.parseInt(st.nextToken());
			
			list.get(B).add(A);
		}
		
		List<Integer> result = new ArrayList<>();
		int max = 0;
		
		for(int i = 1;i<=N;i++) {
			Queue<Integer> q = new LinkedList<>();
			q.add(i);
			boolean[] visited = new boolean[N+1];
			visited[i] = true;
			int count = 0;
			
			while(!q.isEmpty()) {
				int node = q.poll();
				count++;
				
				for(int next : list.get(node)) {
					if(visited[next]) continue;
					q.add(next);
					visited[next] = true;
				}
			}
			if(count>max) {
				max = count;
				result = new ArrayList<>();
				result.add(i);
			} else if(count == max) {
				result.add(i);
			}
			
		}
		
		result.sort(new Comparator<Integer>() {

			@Override
			public int compare(Integer o1, Integer o2) {
				return o1-o2;
			}
		});
		
		for(int i = 0;i<result.size();i++) {
			System.out.print(result.get(i) + " ");
		}
	}
}