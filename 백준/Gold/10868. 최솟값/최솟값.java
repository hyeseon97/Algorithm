import java.io.*;
import java.util.*;

public class Main {
	
	static int N;
	static int M;
	static int H;
	static int size;
	static long[] arr;
	static long[] tree;
	
	static int left;
	static int right;
	
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		arr = new long[N+1];
		for(int i = 1;i<=N;i++) {
			arr[i] = Integer.parseInt(br.readLine());
		}
		
		H = (int)Math.ceil(Math.log(N)/Math.log(2));
		size = 1<<(H+1);
		
		tree = new long[size];
		init(1, 1, N);
		
		StringBuilder sb = new StringBuilder();
		for(int i = 0;i<M;i++) {
			st = new StringTokenizer(br.readLine());
			left = Integer.parseInt(st.nextToken());
			right = Integer.parseInt(st.nextToken());
			long min=  get(1, 1, N);
			sb.append(min + "\n");
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
	
	static long get(int node, int start, int end) {
		
		// 겹치지 않을 때
		if(right < start || end < left) return Long.MAX_VALUE;
		
		
		// 찾는 구간이 start~end를 완전히 포함할 때
		if(left <= start && end <= right) {
			return tree[node];
		}
		
		long leftMin = get(node*2, start, (start+end)/2);
		long rightMin = get(node*2+1, (start+end)/2+1, end);
		return Math.min(leftMin, rightMin);
		
	}
}