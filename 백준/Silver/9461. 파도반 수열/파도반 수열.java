import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
	static long[] arr;
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		int T = Integer.parseInt(br.readLine());
		arr = new long[101];
		
		// 1부터 4일때는 값을 미리 저장하고
		// 5부터 공식을 적용할 수 있음
		arr[1] = 1;
		arr[2] = 1;
		arr[3] = 1;
		arr[4] = 2;
		for(int t = 1;t<=T;t++) {
			int N = Integer.parseInt(br.readLine());
			sb.append(method(N));
			sb.append("\n");
		}
		System.out.println(sb);
	}
	
	static long method(int n) {

		// n일때 값이 계산되지 않았으면 계산하기
		// 이 수열은 n에 해당하는 값을 n-1의 값과 와 n-5의 값의 합으로 수할 수 있음
		if(n>0 && arr[n]==0) {
			arr[n] = method(n-1)+method(n-5); // 재귀로 값 구하기
		}
		
		// 계산 후 n의 값을 반환
		return arr[n];
		
	}
}