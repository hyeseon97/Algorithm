import java.io.*;
import java.util.*;

public class Main {

	// 코스의 길이
	static int N;
	
	// 시야 범위
	static int M;
	
	// 광고판
	static int[] board;
	
	// 세그먼트트리(구간 최대값)
	static int[] tree;
	
	static int H;
	static int size;
	
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		board = new int[N+1];
		st = new StringTokenizer(br.readLine());
		for(int i = 1;i<=N;i++) {
			board[i] = Integer.parseInt(st.nextToken());
		}
		
		H = (int)Math.ceil(Math.log(N)/Math.log(2));
		size = 1<<(H+1);
		tree = new int[size];
		
		init(1, 1, N);
		
		// M부터 시작하여 앞뒤 M-1칸으로 범위를 지정해 최대값 출력
		StringBuilder sb = new StringBuilder();
		for(int i = M;i<=N-M+1;i++) {
			int left = i-(M-1);
			int right = i+(M-1);
			sb.append(get(left, right, 1, 1, N) + " ");
		}
		
		System.out.println(sb.toString());
	}
	
	static void init(int node, int start, int end) {
		
		if(start == end) {
			tree[node] = board[start];
			return;
		}
		
		init(node*2, start, (start+end)/2);
		init(node*2+1, (start+end)/2+1, end);
		tree[node] = Math.max(tree[node*2], tree[node*2+1]);
		
	}
	
	static int get(int left, int right, int node, int start, int end) {
		
		if(right < start || end < left) return 0;
		
		if(left <= start && end <= right) {
			return tree[node];
		}
		
		int leftMax = get(left, right, node*2, start, (start+end)/2);
		int rightMax = get(left, right, node*2+1, (start+end)/2+1, end);
		return Math.max(leftMax, rightMax);
		
	}
}