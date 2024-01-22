import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int N = Integer.parseInt(br.readLine());
		
		int[] dp = new int[N+1];
		
		// 어떤 수 i에서 제곱수항의 최소 개수를 구하기 위해서는
		// 1의 제곱부터 제곱수가 i가 넘지 않는 선에서
		// 그 제곱수만큼 뺀 수의 제곱수항 최소개수에서 +1을 했을 때 가장 작은 값으로 최적해를 구한다
		// 즉, dp[i-j^2] 들의 최소값 구해서 갱신하기
		for(int i = 1;i<=N;i++) {
			int min = Integer.MAX_VALUE;
			for(int j = 1;Math.pow(j, 2)<=i;j++) {
				min = Math.min(min, dp[i-(int)Math.pow(j, 2)]);
			}
			dp[i] = min+1;
		}
		
		System.out.println(dp[N]);
	}
}