import java.util.*;
import java.io.*;;

public class Main {

	// 수열의 크기
	static int N;
	
	// 수열
	static int[] arr;
	
	// 쿼리의 개수
	static int M;
	
	// 쿼리
	static int[][] query;
	
	// 세그먼트 트리의 높이, 크기, 배열
	static int H;
	static int size;
	static int[] tree;
	
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.parseInt(br.readLine());
		arr = new int[N+1];
		StringTokenizer st = new StringTokenizer(br.readLine());
		for(int i = 1;i<=N;i++) {
			arr[i] = Integer.parseInt(st.nextToken());
		}
		H = (int)Math.ceil(Math.log(N)/Math.log(2));
		size = 1<<(H+1);
		tree = new int[size];
		
		M = Integer.parseInt(br.readLine());
		query = new int[M][3];
		for(int i = 0;i<M;i++) {
			st = new StringTokenizer(br.readLine());
			for(int j = 0;j<3;j++) {
				query[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		
		// 세그먼트 트리 초기화
		init(1, 1, N);
		
		StringBuilder sb = new StringBuilder();
		
		// 쿼리 반복
		for(int i = 0;i<M;i++) {
			int command = query[i][0];
			
			if(command == 1) {
				int node = query[i][1];
				int value = query[i][2];
				change(node, value, 1, 1, N);
			} else {
				int left = query[i][1];
				int right = query[i][2];
				int value = get(left, right, 1, 1, N);
				sb.append(value + "\n");
			}
		}
		
		System.out.println(sb.toString());
	}
	
	static void init(int node, int start, int end) {
		
		if(start == end) {
			tree[node] = arr[start];
			return;
		}
		
		init(node*2, start, (start+end)/2);
		init(node*2+1, (start+end)/2+1, end);
		tree[node] = Math.min(tree[node*2], tree[node*2+1]);
		
	}
	
	static void change(int idx, int value, int node, int start, int end) {
		
		if(start == idx && end == idx) {
			tree[node] = value;
			return;
		}
		
		if(idx < start || end < idx) return;

		change(idx, value, node*2, start, (start+end)/2);
		change(idx, value, node*2+1, (start+end)/2+1, end);
		tree[node] = Math.min(tree[node*2], tree[node*2+1]);
	}
	
	static int get(int left, int right, int node, int start, int end) {
		
		if(right < start || end < left) return Integer.MAX_VALUE;
		
		if(left <= start && end <= right) {
			return tree[node];
		}
		
		int leftMin = get(left, right, node*2, start, (start+end)/2);
		int rightMin = get(left, right, node*2+1, (start+end)/2+1, end);
		return Math.min(leftMin, rightMin);
		
		
	}
	
}