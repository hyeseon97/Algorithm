import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.StringTokenizer;

public class Main {
	
	static int N;
	static int[] people;
	static ArrayList<ArrayList<Integer>> list;
	static Set<Integer> A;
	static Set<Integer> B;
	static int min;
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.parseInt(br.readLine());
		people = new int[N+1];
		list = new ArrayList<>();
		StringTokenizer st = new StringTokenizer(br.readLine());
		for(int i = 1;i<=N;i++) {
			people[i] = Integer.parseInt(st.nextToken());
		}
		for(int i = 0;i<=N;i++) {
			list.add(new ArrayList<>());
			if(i==0) continue;
			st = new StringTokenizer(br.readLine());
			int P = Integer.parseInt(st.nextToken());
			for(int j = 1;j<=P;j++) {
				list.get(i).add(Integer.parseInt(st.nextToken()));
			}
		}// 입력 완료
		
		A = new HashSet<>();
		B = new HashSet<>();
		min = Integer.MAX_VALUE;
		
		grouping(1);
		
		if(min == Integer.MAX_VALUE) System.out.println(-1);
		else System.out.println(min);
		
	}
	
	// 구역을 두 선거구로 나누기
	static void grouping(int idx) {
		
		if(idx > N) {
			if(A.size()==0 || B.size()==0) return;
			
			cal();
			
			return;
		}
		
		
		A.add(idx);
		grouping(idx+1);
		A.remove(idx);

		B.add(idx);
		grouping(idx+1);
		B.remove(idx);
		
	}
	
	// 각 선거구의 인구수를 계산하고 차이값의 최소값 갱신하기
	static void cal() {

		List<Integer> listA = new ArrayList<>(A);
		Queue<Integer> q = new LinkedList<>();
		q.add(listA.get(0));
		boolean[] visited = new boolean[N+1];
		visited[listA.get(0)] = true;
		int count = 0;
		int sumA = 0;
		while(!q.isEmpty()) {
			int num = q.poll();
			count++;
			sumA += people[num];
			for(int next : list.get(num)) {
				if(visited[next] || !A.contains(next)) continue;
				q.add(next);
				visited[next] = true;
			}
		}
		if(count!=listA.size()) return;

		List<Integer> listB = new ArrayList<>(B);
		q = new LinkedList<>();
		q.add(listB.get(0));
		visited = new boolean[N+1];
		visited[listB.get(0)] = true;
		count = 0;
		int sumB = 0;
		while(!q.isEmpty()) {
			int num = q.poll();
			count++;
			sumB += people[num];
			for(int next : list.get(num)) {
				if(visited[next] || !B.contains(next)) continue;
				q.add(next);
				visited[next] = true;
			}
		}
		if(count!=listB.size()) return;

		min = Math.min(min, Math.abs(sumA-sumB));
		
	}
}