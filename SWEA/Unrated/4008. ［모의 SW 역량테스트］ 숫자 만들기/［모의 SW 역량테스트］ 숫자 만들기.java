import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Solution {
	
	// 숫자 개수
	static int N;
	// 연산자 개수
	// 0:+ 1:- 2:* 3:/
	static int[] count;
	// 숫자
	static int[] nums;
	// 연산자 순서
	static int[] seq;
	
	// 계산 후 최대값
	static int max;
	// 계산 후 최소값
	static int min;
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		int T = Integer.parseInt(br.readLine());
		for(int t = 1;t<=T;t++) {
			N = Integer.parseInt(br.readLine());
			count = new int[4];
			nums = new int[N];
			StringTokenizer st = new StringTokenizer(br.readLine());
			for(int i = 0;i<4;i++) {
				count[i] = Integer.parseInt(st.nextToken());
			}
			st = new StringTokenizer(br.readLine());
			for(int i = 0;i<N;i++) {
				nums[i] = Integer.parseInt(st.nextToken());
			} // 입력 끝
			
			// 연산자 순서 배열과 최대값 최소값 초기화
			seq = new int[N-1];
			max = Integer.MIN_VALUE;
			min = Integer.MAX_VALUE;
			
			make(0);
			
			sb.append("#" + t + " " + (max-min) + "\n");
		}
		System.out.println(sb);
	}
	
	// 정해진 연산자 순서대로 계산하기
	static void cal() {
		
		int result = nums[0];
		
		// seq에 저장되어 있는 연산자 종류에 따라 계산
		for(int i = 1;i<N;i++) {
			if(seq[i-1] == 0) {
				result += nums[i];
			} else if(seq[i-1] == 1) {
				result -= nums[i];
			} else if(seq[i-1] == 2) {
				result *= nums[i];
			} else {
				result /= nums[i];
			}
		}
		
		// 최대값 최소값 갱신
		max = Math.max(max, result);
		min = Math.min(min, result);
		
	}
	
	// 연산자 순서 정하기
	static void make(int idx) {
		
		// 연산자 순서 N-1번째 되었을 때 다 정한 것이므로 계산하기
		if(idx == N-1) {
			cal();
			return;
		}
		
		// 0(덧셈) 부터 3(나눗셈)까지 남은 연산자가 있을 때 현재(idx) 순서에 넣기
		for(int i = 0;i<4;i++) {
			if(count[i]==0) continue;
			count[i]--; // 연산자 사용했으므로 -1
			seq[idx] = i; // 현재 연산자를 현재 순서에 넣기
			make(idx+1); // 다음 연산자 순서 정하러 재귀
			count[i]++; // 연산자 갯수 다시 원상복구
		}
		
		
	}
}