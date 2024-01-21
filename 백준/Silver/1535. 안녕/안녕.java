import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int N = Integer.parseInt(br.readLine());
		int[] hp = new int[N+1];
		int[] happy = new int[N+1];
		
		StringTokenizer st = new StringTokenizer(br.readLine());
		for(int i = 1;i<=N;i++) {
			hp[i] = Integer.parseInt(st.nextToken());
		}
		st = new StringTokenizer(br.readLine());
		for(int i = 1;i<=N;i++) {
			happy[i] = Integer.parseInt(st.nextToken());
		}
		
		int[][] dp = new int[N+1][100];
		for(int i = 1;i<=N;i++) {
			for(int j = 1;j<=99;j++) {
				if(j>=hp[i] && dp[i-1][j]<(dp[i-1][j-hp[i]]+happy[i])) {
					dp[i][j] = dp[i-1][j-hp[i]]+happy[i];
				} else {
					dp[i][j] = dp[i-1][j];
				}
			}
		}
		
		System.out.println(dp[N][99]);
		
	}
}